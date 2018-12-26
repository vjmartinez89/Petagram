package com.vjmartinez.petagram;

import android.content.Context;
import android.support.design.button.MaterialButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.vjmartinez.petagram.utils.FileUtils;
import com.vjmartinez.petagram.utils.MessageUtil;
import com.vjmartinez.petagram.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class FormFileActivity extends PetagramActivity {

    Toolbar actionBar = null;
    private EditText edtFfNombre;
    private EditText edtFfComment;
    private MaterialButton btnFfSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_file);
        this.setTitle(R.string.ffa_tittle);
        init();
    }

    @Override
    public void initComponents() {
        super.initComponents();
        actionBar = findViewById(R.id.mainAcionBar);
        setSupportActionBar(actionBar);
        //Set support for previous action bar button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btnFfSave = findViewById(R.id.btn_ff_save);
        edtFfNombre = (findViewById(R.id.edt_ff_nombre));
        edtFfComment = (findViewById(R.id.edt_ff_comment));
    }

    @Override
    public void initEvents() {
        super.initEvents();
        btnFfSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(validateForm()){

                    PopupMenu popupMenu =  new PopupMenu(FormFileActivity.this, v);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_save_file_menu,
                            popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.mi_io_file:
                                    //Validate extension
                                    if(!edtFfNombre.getText().toString().toLowerCase()
                                            .endsWith(".txt")){
                                        showToast(getString(R.string.file_name_invalid));
                                        break;
                                    }
                                    if(FileUtils.createSimpleTextFile(FormFileActivity.this,
                                            edtFfNombre.getText().toString(),edtFfComment.getText()
                                                    .toString(),
                                            Context.MODE_PRIVATE)){
                                        showToast(getString(R.string.file_created));
                                    }else{
                                        showToast(getString(R.string.file_creation_error));
                                    }
                                    break;
                                case R.id.mi_shared_pref:
                                    Map<String, String> preferences = new HashMap<>();
                                    preferences.put("file_name", edtFfNombre.getText().toString());
                                    preferences.put("file_content", edtFfComment.getText()
                                            .toString());
                                    if(FileUtils.createSharedPreferences(
                                            FormFileActivity.this,
                                            preferences)){
                                        showToast(getString(R.string.preference_add));
                                    }else{
                                        showToast(getString(R.string.preference_add_error));
                                    }
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
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
     * Validate mandatory fields
     * @return true: All mandatory fields are full, false: otherwise
     */
    private boolean validateForm(){
        if(StringUtils.isEmpty(edtFfNombre.getText())){
            showToast(getString(R.string.file_name_empty));
            return false;
        }

        if(StringUtils.isEmpty(edtFfComment.getText())){
            showToast(getString(R.string.file_content_empty));
            return false;
        }
        return true;
    }

    /**
     * Go to Main Activity when user touch back button
     * @param keyCode The key code
     * @param event The event object
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
        go(MainActivity.class, null, true);
    }

    //Initialize options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_options_menu, menu); //Inflate our menu
        return true;
    }

    //When user select a menu option
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_see_preferences:
               seePreferences();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show a dialog alert with the shared preferences
     */
    private void seePreferences() {
        Map<String, String> preferences = FileUtils
                .getSharedPreferences(FormFileActivity.this);
        StringBuilder preferencesString = new StringBuilder();
        if(!preferences.isEmpty()){
            for(Map.Entry<String, String> entry: preferences.entrySet()){
                preferencesString.append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append("\n");
            }
            MessageUtil.showAlertDialog(this, getString(R.string.preferences),
                    preferencesString.toString());
        }else{
            showToast(getString(R.string.no_preferences_found));
        }
    }
}
