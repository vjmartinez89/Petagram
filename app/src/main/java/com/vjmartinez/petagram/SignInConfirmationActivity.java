package com.vjmartinez.petagram;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class SignInConfirmationActivity extends AppCompatActivity {

    private TextView tviContactName;
    private TextView tviContactBirthday;
    private TextView tviContactPhone;
    private TextView tviContactEmail;
    private TextView tviContactAddress;
    private TextView tviContactSex;
    private MaterialButton btnSingInBack;
    private Toolbar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_confirmation);

        initComponents();

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            if(!StringUtils.isEmpty(extras.getString("CONTACT_OBJECT"))){
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Contact contact = objectMapper.readValue(extras.getString("CONTACT_OBJECT"),
                            Contact.class);
                    setFormData(contact);
                }catch(Exception e){
                    Log.e("Error", e.getMessage(), e);
                }
            }
        }

    }

    /**
     * Set object data to form views
     * @param contact
     */
    private void setFormData(Contact contact) {

        tviContactName.setText(contact.getName());
        tviContactBirthday.setText(new SimpleDateFormat("dd/MM/yyyy")
                .format(contact.getBirthDate()));
        tviContactSex.setText( "M".equalsIgnoreCase(contact.getSex()) ?
                getResources().getString(R.string.man) :
                getResources().getString(R.string.woman));
        tviContactPhone.setText(contact.getPhone());
        tviContactEmail.setText(contact.getEmail());
        tviContactAddress.setText(StringUtils.nvl(contact.getAddress(), ""));
    }

    /***
     * Init the basic components of Activity
     */
    private void initComponents() {
        //TextView initialization
        tviContactName = (TextView) findViewById(R.id.tvi_contact_name);
        tviContactBirthday = (TextView) findViewById(R.id.tvi_contact_birthday);
        tviContactSex = (TextView)findViewById(R.id.tvi_contact_sex);
        tviContactPhone = (TextView) findViewById(R.id.tvi_contact_phone);
        tviContactEmail = (TextView) findViewById(R.id.tvi_contact_email);
        tviContactAddress = (TextView) findViewById(R.id.tvi_contact_address);
        btnSingInBack = (MaterialButton) findViewById(R.id.btn_sing_in_back);

        btnSingInBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        actionBar = (Toolbar) findViewById(R.id.mainAcionBar);
        setSupportActionBar(actionBar);
        //Set support for previous action bar button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Intercepts the click event on arrow back button in action bar
        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
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

    /**
     * Go to previous activity
     */
    private void goBack(){
        Intent i = new Intent(this, SignInStep1Activity.class);
        Contact contact = getFormData();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            i.putExtra("CONTACT_OBJECT", objectMapper.writeValueAsString(contact));
        }catch(Exception jse){
            i.putExtra("CONTACT_OBJECT", "" );
            Log.e("Error", jse.getMessage(), jse);
        }
        startActivity(i);
        finish();
    }


    /**
     * Create a Contact object from Form data
     * @return
     */
    private Contact getFormData() {
        try {
            return new Contact(
                    tviContactName.getText().toString(),
                    tviContactPhone.getText().toString(),
                    tviContactEmail.getText().toString(),
                    R.drawable.ic_user_male,
                    (new SimpleDateFormat("dd/MM/yyyy")).parse(tviContactBirthday.getText()
                            .toString()),
                    getResources().getString(R.string.man).equalsIgnoreCase(tviContactSex.getText()
                            .toString()) ? "M" : "F",
                    tviContactAddress.getText().toString()
            );
        }catch(Exception e){
            Log.e("Error", e.getMessage(), e);
        }
        return null;
    }
}
