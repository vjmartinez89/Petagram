package com.vjmartinez.petagram;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignInStep1Activity extends AppCompatActivity {

    TextInputEditText txiCompleteName = null;
    TextInputEditText txiBirthDate = null;
    TextInputEditText txiPhone = null;
    TextInputEditText txiEmail = null;
    TextInputEditText txiContactDescription = null;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    MaterialButton btnSingInNext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_step1);
        initComponents();

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {
            txiCompleteName.setText(extras.getString("CONTACT_NAME"));
            txiBirthDate.setText(extras.getString("CONTACT_BIRTHDAY"));
            txiPhone.setText(extras.getString("CONTACT_PHONE"));
            txiEmail.setText(extras.getString("CONTACT_EMAIL"));
            txiContactDescription.setText(StringUtils.nvl(extras.getString("CONTACT_DESCRIPTION"), ""));
        }

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
        txiContactDescription = (TextInputEditText)findViewById(R.id.txi_si_address);
        btnSingInNext = (MaterialButton)findViewById(R.id.btn_sing_in_next);

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
                    i.putExtra("CONTACT_NAME", txiCompleteName.getText().toString());
                    i.putExtra("CONTACT_BIRTHDAY", txiBirthDate.getText().toString());
                    i.putExtra("CONTACT_PHONE", txiPhone.getText().toString());
                    i.putExtra("CONTACT_EMAIL", txiEmail.getText().toString());
                    i.putExtra("CONTACT_DESCRIPTION", txiContactDescription.getText().toString());
                    startActivity(i);
                    finish(); //Finish this Activity
                }
            }
        });
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
