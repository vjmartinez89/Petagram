package com.vjmartinez.petagram;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.vjmartinez.petagram.utils.MessageUtil;

/**
 * The base Activity class
 */
public class PetagramActivity extends AppCompatActivity implements IPetragramActivity{

    protected static final int PERMISSIONS_REQUEST_CALL = 10000;
    protected static final int PICK_IMAGE = 10001;
    protected static final int REQUEST_IMAGE_CAPTURE = 10002;
    protected static final int PERMISSIONS_REQUEST_INT = 10003;
    protected static final int PERMISSIONS_REQUEST_BLT = 10004;

    protected static final int ENABLE_BTL_REQUEST_CODE = 40000;


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
     * @param message The message text to show in toast
     */
    public void showToast(String message){
        MessageUtil.showToast(getBaseContext(),message, Toast.LENGTH_LONG);
    }

    /**
     * Check an application permission status
     * @return the permission status
     */
    protected boolean checkPermissionStatus(Context context, String permission){
        int res = ContextCompat.checkSelfPermission(context, permission);
        return PackageManager.PERMISSION_GRANTED == res;
    }

    /**
     * Show explanation message to user
     * @param title The title of the explanation
     * @param message The text message of the explanation
     * @param permission The permission
     * @param permissionRequestCode The request code
     */
    protected void showExplanation(final Activity activity, String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {

        if(!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            requestPermission(activity, new String[]{permission},
                                    permissionRequestCode);
                        }
                    });
            builder.create().show();
        }
    }

    /**
     * Request user permission
     * @param permissionName The permission name
     * @param permissionRequestCode The request code
     */
    protected void requestPermission(Activity activity, String[] permissionName,
                                     int permissionRequestCode) {
        ActivityCompat.requestPermissions(activity,
                permissionName, permissionRequestCode);
    }


    /**
     * Process user response for request permission
     * @param requestCode The request code
     * @param permissions The permissions array
     * @param grantResults The gran results array
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showToast("Permission Granted!");
        } else {
            showToast("Permission Denied!");
        }
    }

    /**
     * Navigate to other activity
     * @param destination, The class of destination activity
     * @param extras, The extras to set in the intent
     * @param finishBeforeGo, Finish this activity before start the new (?)
     */
    public void go(Class destination, Bundle extras, boolean finishBeforeGo){
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
