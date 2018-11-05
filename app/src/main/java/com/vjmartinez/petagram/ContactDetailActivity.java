package com.vjmartinez.petagram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class ContactDetailActivity extends PetagramActivity {

    TextView textViewName;
    TextView textViewPhone;
    TextView textViewEmail;
    TextView textViewBirthDate;
    TextView textViewSex;
    TextView textViewAddress;
    ImageView imgContactProfile;
    private Toolbar actionBar = null;

    LinearLayout rowPhone;
    LinearLayout rowEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        textViewName = (TextView) findViewById(R.id.tv_cd_name);
        textViewPhone = (TextView) findViewById(R.id.tv_cd_phone);
        textViewEmail = (TextView) findViewById(R.id.tv_cd_email);
        textViewBirthDate = (TextView) findViewById(R.id.tv_cd_birth_date);
        textViewSex = (TextView) findViewById(R.id.tv_cd_sex);
        textViewAddress = (TextView) findViewById(R.id.tv_cd_address);
        imgContactProfile = (ImageView)findViewById(R.id.img_cd_profile);
        actionBar = (Toolbar) findViewById(R.id.mainAcionBar);

        rowPhone = (LinearLayout) findViewById(R.id.rowPhone);
        rowEmail = (LinearLayout) findViewById(R.id.rowEmail);

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

        rowPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(v);
            }
        });
        rowEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(v);
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
        actionBar.setTitleTextColor(getResources().getColor(R.color.titleColor));
    }

    /**
     * Init phone application for call to contact cellphone
     * @param v
     */
    public void call(View v) {
        String callNumber = textViewPhone.getText().toString();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE )) {

                showExplanation("Permission Needed", "Rationale",
                        Manifest.permission.CALL_PHONE, PERMISSIONS_REQUEST_CALL);

            } else {
                requestPermission(Manifest.permission.CALL_PHONE,
                        PERMISSIONS_REQUEST_CALL);
            }
        }
        //Init call application
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + callNumber)));
    }

    /**
     * Init email
     * @param v the view
     */
    public void sendMail(View v){
        String email = textViewEmail.getText().toString();
        String[] addressList =  {email}; //Create address list
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, addressList);
        emailIntent.setType("message/rfc822"); //Set the application type
        startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    /**
     * Go to Pet List Activity when user touch back button
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
     * Go back
     */
    private void goBack() {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * Set object data to form views
     * @param contact
     */
    private void setFormData(Contact contact) {

        if(contact.getPhoto() > 0){
            imgContactProfile.setImageResource(contact.getPhoto());
        }

        textViewName.setText(contact.getName());
        textViewBirthDate.setText(new SimpleDateFormat("dd/MM/yyyy")
                .format(contact.getBirthDate()));
        textViewSex.setText( "M".equalsIgnoreCase(contact.getSex()) ?
                getResources().getString(R.string.man) :
                getResources().getString(R.string.woman));
        textViewPhone.setText(contact.getPhone());
        textViewEmail.setText(contact.getEmail());
        textViewAddress.setText(StringUtils.nvl(contact.getAddress(), ""));
    }
}
