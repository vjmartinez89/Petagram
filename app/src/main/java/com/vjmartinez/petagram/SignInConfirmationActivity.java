package com.vjmartinez.petagram;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class SignInConfirmationActivity extends AppCompatActivity {

    private TextView tviContactName;
    private TextView tviContactBirthday;
    private TextView tviContactPhone;
    private TextView tviContactEmail;
    private TextView tviContactDescription;
    private MaterialButton btnSingInBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_confirmation);

        initComponents();

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            tviContactName.setText(extras.getString("CONTACT_NAME"));
            tviContactBirthday.setText(extras.getString("CONTACT_BIRTHDAY"));
            tviContactPhone.setText(extras.getString("CONTACT_PHONE"));
            tviContactEmail.setText(extras.getString("CONTACT_EMAIL"));
            tviContactDescription.setText(StringUtils.nvl(extras.getString("CONTACT_DESCRIPTION"), ""));
        }

    }

    /***
     * Init the basic components of Activity
     */
    private void initComponents() {
        //TextView initialization
        tviContactName = (TextView) findViewById(R.id.tvi_contact_name);
        tviContactBirthday = (TextView) findViewById(R.id.tvi_contact_birthday);
        tviContactPhone = (TextView) findViewById(R.id.tvi_contact_phone);
        tviContactEmail = (TextView) findViewById(R.id.tvi_contact_email);
        tviContactDescription = (TextView) findViewById(R.id.tvi_contact_description);
        btnSingInBack = (MaterialButton) findViewById(R.id.btn_sing_in_back);

        btnSingInBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    /**
     * Go to Main Activity when user touch back button
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
           goBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack(){
        Intent intent = new Intent(this, SignInStep1Activity.class);
        intent.putExtra("CONTACT_NAME", tviContactName.getText().toString());
        intent.putExtra("CONTACT_BIRTHDAY", tviContactBirthday.getText().toString());
        intent.putExtra("CONTACT_PHONE", tviContactPhone.getText().toString());
        intent.putExtra("CONTACT_EMAIL", tviContactEmail.getText().toString());
        intent.putExtra("CONTACT_DESCRIPTION", tviContactDescription.getText().toString());
        startActivity(intent);
        finish();
    }
}
