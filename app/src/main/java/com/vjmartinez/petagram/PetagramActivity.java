package com.vjmartinez.petagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * The base Activity class
 */
public class PetagramActivity extends AppCompatActivity {

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
}
