package com.vjmartinez.petagram;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends PetagramActivity {

    Toolbar actionBar = null;
    RecyclerView menuItemList;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * Initialize the activity components
     */
    @Override
    public void initComponents() {
        actionBar = (Toolbar) findViewById(R.id.mainAcionBar);
        setSupportActionBar(actionBar);

        menuItemList = (RecyclerView) findViewById(R.id.rv_menu_items);
        menuItemList.setHasFixedSize(true);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.flaButtonMainPage);
    }

    @Override
    public void initAdapters() {
        super.initAdapters();
        menuItemList.setAdapter(new MenuItemAdapter(getMenuItems(), this));
    }

    @Override
    public void initEvents() {
        super.initEvents();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Agregar una notificacion TOAST
                // Toast.makeText(getBaseContext(),getResources().getString(R.string.favorite_message), Toast.LENGTH_SHORT).show();

                //Notificacion SnackBar
                Snackbar.make(v,getResources().getString(R.string.favorite_message),Snackbar.LENGTH_LONG)
                        //Agrega una accion a la barra de notificacion
                        .setAction(getResources().getString(R.string.favorite_action_text),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.i("SNACKBAR","Click on snackBar");
                                    }
                                })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }
        });
    }

    /**
     * Create an return the menu item list
     * @return
     */
    private List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        try {
            menuItems.add(new MenuItem(getResources().getString(R.string.login), R.drawable.ic_login_48, null));
            menuItems.add(new MenuItem(getResources().getString(R.string.singin), R.drawable.ic_sign_in_48, SignInStep1Activity.class));
            menuItems.add(new MenuItem(getResources().getString(R.string.see_users), R.drawable.ic_users_list, ContactListActivity.class));
        }catch (Exception ex)
        {
            Log.e("ERROR", "Error creating menu "+ex.getMessage(), ex);
        }
        return menuItems;
    }



    /**
     * On back button confirm and close app
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            closeApplication();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Confirm and close app
     */
    private void closeApplication(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.exit_confirm_tittle));
        builder.setMessage(getResources().getString(R.string.exit_confirm))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    //Initialize options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu); //Inflate our menu
        return true;
    }

    //When user select a menu option
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_about:
                go(AboutActivity.class, null, true);
                break;
            case R.id.mi_settings:
                go(SettingsActivity.class, null, true);
                break;
            case R.id.mi_exit:
                closeApplication();
                break;
            case R.id.mi_refresh:
                showToast("Contenido actualizado");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
