package com.vjmartinez.petagram;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;

import com.vjmartinez.petagram.exception.NetworkException;
import com.vjmartinez.petagram.utils.NetworkUtils;
import com.vjmartinez.petagram.utils.SendEmailAsyncTask;
import com.vjmartinez.petagram.utils.StringUtils;

public class ContactActivity extends PetagramActivity {

    private EditText edtContactName;
    private EditText edtContactMail;
    private EditText edtContactComment;
    private MaterialButton btnContactSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        init();
    }

    @Override
    public void initComponents() {
        super.initComponents();
        edtContactName = findViewById(R.id.edt_contact_name);
        edtContactMail = findViewById(R.id.edt_contact_mail);
        edtContactComment = findViewById(R.id.edt_contact_comment);
        btnContactSend = findViewById(R.id.btn_contact_send);
    }

    @Override
    public void initEvents() {
        super.initEvents();
        btnContactSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getBaseContext(),
                        Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ContactActivity.this,
                            Manifest.permission.INTERNET )) {

                        showExplanation(ContactActivity.this,"Permission Needed",
                                "Rationale",
                                Manifest.permission.INTERNET, PERMISSIONS_REQUEST_INT);

                    } else {
                        requestPermission(ContactActivity.this,
                                new String[] {Manifest.permission.INTERNET},
                                PERMISSIONS_REQUEST_INT);
                    }
                }

                try {
                    if (validateForm()) {

                        if (!NetworkUtils.deviceIsConnected(getBaseContext())) {
                            throw new NetworkException("", "El dispositivo no está conectado a internet.");
                        }
                        String from = edtContactMail.getText().toString();
                        String[] to = {"vjmartinez@softcaribbean.com"};
                        String subject = "Comentario de "+edtContactName.getText().toString();
                        String body = edtContactComment.getText().toString();
                        new SendEmailAsyncTask(getBaseContext(), from, to, subject, body).execute();
                    }
                }catch(Exception nex){
                    showToast(nex.getMessage());
                }
            }
        });
    }

    /**
     * Validate the form content
     * @return true if data is valid, false otherwise
     */
    private boolean validateForm(){
        if(StringUtils.isEmpty(edtContactName.getText())){
            showToast("Ingrese su nombre.");
            return false;
        }

        if(StringUtils.isEmpty(edtContactMail.getText())){
            showToast("Ingrese su email.");
            return false;
        }

        if(StringUtils.isEmpty(edtContactComment.getText())){
            showToast("Ingrese su comentario.");
            return false;
        }
        return true;
    }
}
