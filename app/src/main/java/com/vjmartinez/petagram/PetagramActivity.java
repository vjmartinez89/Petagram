package com.vjmartinez.petagram;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The base Activity class
 */
public class PetagramActivity extends AppCompatActivity implements IPetragramActivity{

    protected static final int PERMISSIONS_REQUEST_CALL = 10000;
    protected static final int PICK_IMAGE = 10001;
    protected static final int REQUEST_IMAGE_CAPTURE = 10002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("START","onCreate method was executed.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("START","onStart method was executed.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("RESUME","onResume method was executed.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("RESTART","onRestart method was executed.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PAUSE","onPause method was executed.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("STOP","onStop method was executed.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("DESTROY","onDestroy method was executed.");
    }

    /**
     * Show a Toast message
     * @param message
     */
    protected void showToast(String message){
        MessageUtil.showToast(getBaseContext(),message, Toast.LENGTH_LONG);
    }

    /**
     * Process user response for request permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CALL:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showToast("Permission Granted!");
                } else {
                    showToast("Permission Denied!");
                }
        }
    }

    /**
     * Show explanation message to user
     * @param title
     * @param message
     * @param permission
     * @param permissionRequestCode
     */
    protected void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    /**
     * Request user permission
     * @param permissionName
     * @param permissionRequestCode
     */
    protected void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    /**
     * Navigate to other activity
     * @param destination, The class of destination activity
     * @param extras, The extras to set in the intent
     * @param finishBeforeGo, Finish this activity before start the new (?)
     */
    protected void go(Class destination, Bundle extras, boolean finishBeforeGo){
        Intent i = new Intent(this, destination);
        if(extras != null ){
            i.putExtras(extras);
        }
        startActivity(i);
        if(finishBeforeGo) {
            finish();
        }
    }

    @Override
    public void initComponents() {
        //Implement this method in child classes
    }

    @Override
    public void initAdapters() {
        //Implement this method in child classes
    }

    @Override
    public void initEvents() {
        //Implement this method in child classes
    }

    /**
     * Execute initComponents, initAdapters and initEvents methods
     */
    public void init(){
        initComponents();
        initAdapters();
        initEvents();
    }
}
