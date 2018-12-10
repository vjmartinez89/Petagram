package com.vjmartinez.petagram;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.view.View;
import android.widget.EditText;

import com.vjmartinez.petagram.utils.EmailUtils;
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
                if(validateForm()) {
                    if (EmailUtils.sendMail(edtContactMail.getText().toString(),
                            edtContactComment.getText().toString(), "Prueba Petagram")) {
                        showToast("El mensaje se ha enviado. Gracias.");
                    } else {
                        showToast("Se presentó un error enviando el mensaje.");
                    }
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
