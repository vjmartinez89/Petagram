package com.vjmartinez.petagram;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.vjmartinez.petagram.dto.Contact;
import com.vjmartinez.petagram.utils.MessageUtil;
import com.vjmartinez.petagram.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    Toolbar actionBar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_step1);
        init();
        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            if(extras.getSerializable("CONTACT_OBJECT") != null){
                Contact contact = (Contact) extras.getSerializable("CONTACT_OBJECT");
                setFormData(contact);
            }
        }
    }

    /***
     * Init the basic components of Activity
     */
    @Override
    public void initComponents() {
        //Input initialization
        txiCompleteName = findViewById(R.id.txiCompleteName);
        txiBirthDate = findViewById(R.id.txiBirthDate);
        txiPhone = findViewById(R.id.txi_si_phone);
        txiEmail = findViewById(R.id.txi_si_email);
        txiContactAddress = findViewById(R.id.txi_si_address);
        btnSingInNext = findViewById(R.id.btn_sing_in_next);
        rbMan = findViewById( R.id.radio_man);
        rbWoman = findViewById( R.id.radio_woman);

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
                if( dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
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
                txiBirthDate.setText(new SimpleDateFormat("dd/MM/yyyy",
                        new Locale("es_CO")).format(calendar.getTime()));
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
                    i.putExtra("CONTACT_OBJECT", contact);
                    startActivity(i);
                    finish(); //Finish this Activity
                }
            }
        });

        //Intercepts the click event on arrow back button in action bar
        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    /**
     * Set object data to form components
     * @param contact The contact object
     */
    private void setFormData(Contact contact) {
        if(contact != null) {
            txiCompleteName.setText(contact.getName());
            txiBirthDate.setText(new SimpleDateFormat("dd/MM/yyyy",
                    new Locale("es_CO"))
                    .format(contact.getBirthDate()));
            if ("M".equalsIgnoreCase(contact.getSex())) {
                rbMan.setChecked(true);
            } else {
                rbWoman.setChecked(true);
            }
            txiPhone.setText(contact.getPhone());
            txiEmail.setText(contact.getEmail());
            txiContactAddress.setText(StringUtils.nvl(contact.getAddress(),
                    ""));
        }
    }

    /**
     * Create a Contact object from Form data
     * @return The contact object
     */
    private Contact getFormData() {
        try {
            return new Contact(
                    txiCompleteName.getText().toString(),
                    txiPhone.getText().toString(),
                    txiEmail.getText().toString(),
                    R.drawable.ic_user_male,
                    (new SimpleDateFormat("dd/MM/yyyy", new Locale("es_CO")))
                            .parse(txiBirthDate.getText().toString()),
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
        if(StringUtils.isEmpty(txiCompleteName.getText())){
            MessageUtil.showAlertDialog(this,getResources().getString(R.string.validate),
                    getResources().getString(R.string.name_mandatory));
            txiCompleteName.requestFocusFromTouch();
            return false;
        }

        if(StringUtils.isEmpty(txiBirthDate.getText())){
            MessageUtil.showAlertDialog(this, getResources().getString(R.string.validate),
                    getResources().getString(R.string.birthday_mandatory));
            txiBirthDate.requestFocusFromTouch();
            return false;
        }

        if(StringUtils.isEmpty(txiPhone.getText())){
            MessageUtil.showAlertDialog(this, getResources().getString(R.string.validate),
                    getResources().getString(R.string.phone_mandatory));
            txiPhone.requestFocusFromTouch();
            return false;
        }

        if(StringUtils.isEmpty(txiEmail.getText())){
            MessageUtil.showAlertDialog(this, getResources().getString(R.string.validate),
                    getResources().getString(R.string.email_mandatory));
            txiEmail.requestFocusFromTouch();
            return false;
        }

        return true;
    }

    /**
     * Go to Main Activity when user touch back button
     * @param keyCode The key code
     * @param event The event
     * @return Boolean flag
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
           goBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Go to the previous activity
     */
    private void goBack() {
        go(MainActivity.class, null, true);
    }
}
