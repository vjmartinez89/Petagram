package com.vjmartinez.petagram;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignInStep1Activity extends PetagramActivity {

    TextInputEditText txiCompleteName = null;
    TextInputEditText txiBirthDate = null;
    TextInputEditText txiPhone = null;
    TextInputEditText txiEmail = null;
    TextInputEditText txiContactAddress = null;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    MaterialButton btnSingInNext = null;
    RadioButton rbMan = null;
    RadioButton rbWoman = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_step1);
        initComponents();

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {

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
    }

    /**
     * Set object data to form components
     * @param contact
     */
    private void setFormData(Contact contact) {

        txiCompleteName.setText(contact.getName());
        txiBirthDate.setText(new SimpleDateFormat("dd/MM/yyyy")
                .format(contact.getBirthDate()));
        if("M".equalsIgnoreCase(contact.getSex())){
            rbMan.setChecked(true);
        }else{
            rbWoman.setChecked(true);
        }
        txiPhone.setText(contact.getPhone());
        txiEmail.setText(contact.getEmail());
        txiContactAddress.setText(StringUtils.nvl(contact.getAddress(),
                ""));

    }

    /***
     * Init the basic components of Activity
     */
    private void initComponents() {
        //Input initialization
        txiCompleteName = (TextInputEditText)findViewById(R.id.txiCompleteName);
        txiBirthDate = (TextInputEditText)findViewById(R.id.txiBirthDate);
        txiPhone = (TextInputEditText)findViewById(R.id.txi_si_phone);
        txiEmail = (TextInputEditText)findViewById(R.id.txi_si_email);
        txiContactAddress = (TextInputEditText)findViewById(R.id.txi_si_address);
        btnSingInNext = (MaterialButton)findViewById(R.id.btn_sing_in_next);
        rbMan = (RadioButton)findViewById( R.id.radio_man);
        rbWoman = (RadioButton)findViewById( R.id.radio_woman);

        //Date picker dialog click
        txiBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(SignInStep1Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, year, month, day );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //Date picker dialog date selection
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txiBirthDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
                txiPhone.requestFocusFromTouch();
            }
        };

        //Next button click
        btnSingInNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()) {
                    Intent i = new Intent(getBaseContext(), SignInConfirmationActivity.class);
                    Contact contact = getFormData();
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        i.putExtra("CONTACT_OBJECT", objectMapper.writeValueAsString(contact));
                    }catch(Exception jse){
                        i.putExtra("CONTACT_OBJECT", "" );
                        Log.e("Error", jse.getMessage(), jse);
                    }
                    startActivity(i);
                    finish(); //Finish this Activity
                }
            }
        });
    }


    /**
     * Create a Contact object from Form data
     * @return
     */
    private Contact getFormData() {
        try {
            return new Contact(
                    txiCompleteName.getText().toString(),
                    txiPhone.getText().toString(),
                    txiEmail.getText().toString(),
                    R.drawable.ic_user_male,
                    (new SimpleDateFormat("dd/MM/yyyy")).parse(txiBirthDate.getText().toString()),
                    rbMan.isChecked() ? "M" : "F",
                    txiContactAddress.getText().toString()
                    );
        }catch(Exception e){
            Log.e("Error", e.getMessage(), e);
        }
        return null;
    }

    /**
     * Validate mandatory data
     * @return true: all mandatory fields was filled
     */
    private boolean validateForm(){
        if(StringUtils.isEmpty(txiCompleteName.getText().toString())){
            MessageUtil.showAlertDialog(this,getResources().getString(R.string.validate),
                    getResources().getString(R.string.name_mandatory));
            txiCompleteName.requestFocusFromTouch();
            return false;
        }

        if(StringUtils.isEmpty(txiBirthDate.getText().toString())){
            MessageUtil.showAlertDialog(this, getResources().getString(R.string.validate),
                    getResources().getString(R.string.birthday_mandatory));
            txiBirthDate.requestFocusFromTouch();
            return false;
        }

        if(StringUtils.isEmpty(txiPhone.getText().toString())){
            MessageUtil.showAlertDialog(this, getResources().getString(R.string.validate),
                    getResources().getString(R.string.phone_mandatory));
            txiPhone.requestFocusFromTouch();
            return false;
        }

        if(StringUtils.isEmpty(txiEmail.getText().toString())){
            MessageUtil.showAlertDialog(this, getResources().getString(R.string.validate),
                    getResources().getString(R.string.email_mandatory));
            txiEmail.requestFocusFromTouch();
            return false;
        }

        return true;
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
