package com.vjmartinez.petagram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vjmartinez.petagram.dto.Contact;
import com.vjmartinez.petagram.utils.MessageUtil;
import com.vjmartinez.petagram.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ContactDetailActivity extends PetagramActivity {

    private TextView textViewName;
    private TextView textViewPhone;
    private TextView textViewEmail;
    private TextView textViewBirthDate;
    private TextView textViewSex;
    private TextView textViewAddress;
    private ImageView imgContactProfile;
    private Toolbar actionBar = null;

    private LinearLayout rowPhone;
    private LinearLayout rowEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        init();

        Bundle extras = getIntent().getExtras();
        if (extras != null && !extras.isEmpty()) {

            if(extras.getSerializable("CONTACT_OBJECT") != null){
                Contact contact = (Contact) extras.getSerializable("CONTACT_OBJECT");
                setFormData(contact);
            }
        }
    }

    /**
     * Initialize view components of the activity
     */
    @Override
    public void initComponents() {
        super.initComponents();

        textViewName = findViewById(R.id.tv_cd_name);
        textViewPhone = findViewById(R.id.tv_cd_phone);
        textViewEmail = findViewById(R.id.tv_cd_email);
        textViewBirthDate = findViewById(R.id.tv_cd_birth_date);
        textViewSex = findViewById(R.id.tv_cd_sex);
        textViewAddress = findViewById(R.id.tv_cd_address);
        imgContactProfile = findViewById(R.id.img_cd_profile);
        registerForContextMenu(imgContactProfile); //Enable context menu in image view
        actionBar = findViewById(R.id.mainAcionBar);

        rowPhone = findViewById(R.id.rowPhone);
        rowEmail = findViewById(R.id.rowEmail);

        actionBar = findViewById(R.id.mainAcionBar);
        setSupportActionBar(actionBar);
        //Set support for previous action bar button
        if(getSupportActionBar() !=null) {
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
        //Intercepts the click event on arrow back button in action bar
        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    /**
     * Init phone application for call to contact cellphone
     * @param v The view
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
        emailIntent.putExtra(Intent.EXTRA_EMAIL, addressList);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    /**
     * Go to Pet List Activity when user touch back button
     * @param keyCode, The key code
     * @param event, The event information
     * @return boolean
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
        go(ContactListActivity.class, null, true);
    }


    /**
     * Set object data to form views
     * @param contact, The contact object
     */
    private void setFormData(Contact contact) {
        if(contact != null) {
            if (contact.getPhoto() > 0) {
                imgContactProfile.setImageResource(contact.getPhoto());
            }

            textViewName.setText(contact.getName());
            textViewBirthDate.setText(new SimpleDateFormat("dd/MM/yyyy",
                    new Locale("es_CO"))
                    .format(contact.getBirthDate()));
            textViewSex.setText("M".equalsIgnoreCase(contact.getSex()) ?
                    getResources().getString(R.string.man) :
                    getResources().getString(R.string.woman));
            textViewPhone.setText(contact.getPhone());
            textViewEmail.setText(contact.getEmail());
            textViewAddress.setText(StringUtils.nvl(contact.getAddress(), ""));
        }
    }

    //Using context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.img_cd_profile){
            getMenuInflater().inflate(R.menu.context_menu_profile_picture, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_choose_picture:
                openGallery();
                break;
            case R.id.mi_take_picture:
                //TODO: Verify camera hardware existence
                dispatchTakePictureIntent();
                break;
            case R.id.mi_delete:
                showToast("Se eliminará la foto de perfil del usuario");
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Open Gallery for select a picture
     */
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(gallery, getResources()
                .getString(R.string.select_image)), PICK_IMAGE);
    }

    /**
     * Open camera to take a photo
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Capture activity results
     * @param requestCode, The code of request
     * @param resultCode, Yhe code of result
     * @param data, the intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case PICK_IMAGE: //Pick an image from gallery
                    Uri imageUri = data.getData();
                    imgContactProfile.setImageURI(imageUri);
                    break;
                case REQUEST_IMAGE_CAPTURE: //Take a photo with camera
                    Bundle extras = data.getExtras();
                    if(extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imgContactProfile.setImageBitmap(imageBitmap);
                    }else{
                       showToast(getResources().getString(R.string.no_photo_taken));
                    }
                    break;
            }
        }else{
            switch(requestCode) {
                case PICK_IMAGE:
                    MessageUtil.showAlertDialog(this, getResources().getString(R.string.error),
                            getResources().getString(R.string.no_image_selected));
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    MessageUtil.showAlertDialog(this, getResources().getString(R.string.error),
                            getResources().getString(R.string.no_photo_taken));
                    break;
            }
        }
    }
}
