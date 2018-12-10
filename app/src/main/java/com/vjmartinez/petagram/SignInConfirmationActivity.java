package com.vjmartinez.petagram;

import android.support.design.button.MaterialButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vjmartinez.petagram.dto.Contact;
import com.vjmartinez.petagram.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SignInConfirmationActivity extends PetagramActivity {

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

        init();

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

    /***
     * Init the basic components of Activity
     */
    @Override
    public void initComponents() {
        //TextView initialization
        tviContactName = findViewById(R.id.tvi_contact_name);
        tviContactBirthday = findViewById(R.id.tvi_contact_birthday);
        tviContactSex = findViewById(R.id.tvi_contact_sex);
        tviContactPhone = findViewById(R.id.tvi_contact_phone);
        tviContactEmail = findViewById(R.id.tvi_contact_email);
        tviContactAddress = findViewById(R.id.tvi_contact_address);
        btnSingInBack = findViewById(R.id.btn_sing_in_back);

        btnSingInBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        actionBar = findViewById(R.id.mainAcionBar);
        setSupportActionBar(actionBar);
        //Set support for previous action bar button
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void initAdapters() {
        super.initAdapters();
    }

    @Override
    public void initEvents() {
        super.initEvents();
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
     * @param keyCode the key code
     * @param event the event object
     * @return boolean flag
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
        Contact contact = getFormData();
        ObjectMapper objectMapper = new ObjectMapper();
        Bundle extras = new Bundle();
        try {
            extras.putString("CONTACT_OBJECT", objectMapper.writeValueAsString(contact));
        }catch(Exception jse){
            extras.putString("CONTACT_OBJECT", "" );
            Log.e("Error", jse.getMessage(), jse);
        }
        go(SignInStep1Activity.class, extras,true);
    }

    /**
     * Create a Contact object from Form data
     * @return A contact object with form information
     */
    private Contact getFormData() {
        try {
            return new Contact(
                    tviContactName.getText().toString(),
                    tviContactPhone.getText().toString(),
                    tviContactEmail.getText().toString(),
                    R.drawable.ic_user_male,
                    (new SimpleDateFormat("dd/MM/yyyy", new Locale("es_CO")))
                            .parse(tviContactBirthday.getText()
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


    /**
     * Set object data to form views
     * @param contact The contact object
     */
    private void setFormData(Contact contact) {

        tviContactName.setText(contact.getName());
        tviContactBirthday.setText(new SimpleDateFormat("dd/MM/yyyy",
                new Locale("es_CO"))
                .format(contact.getBirthDate()));
        tviContactSex.setText( "M".equalsIgnoreCase(contact.getSex()) ?
                getResources().getString(R.string.man) :
                getResources().getString(R.string.woman));
        tviContactPhone.setText(contact.getPhone());
        tviContactEmail.setText(contact.getEmail());
        tviContactAddress.setText(StringUtils.nvl(contact.getAddress(), ""));
    }

}
