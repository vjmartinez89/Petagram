package com.vjmartinez.petagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

public class AboutActivity extends PetagramActivity {

    private Toolbar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }


    /***
     * Init the basic components of Activity
     */
    @Override
    public void initComponents() {
        actionBar = (Toolbar) findViewById(R.id.mainAcionBar);
        setSupportActionBar(actionBar);
        //Set support for previous action bar button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Initialize the activity adapters
     */
    @Override
    public void initAdapters() {
        super.initAdapters();
    }

    /**
     * Initialize the activity adapters
     */
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
        go(MainActivity.class, null, true);
    }
}
