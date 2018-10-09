package com.vjmartinez.petagram;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends PetagramActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
       initFloatingActionButton();
    }

    /**
     *
     */
    private void initFloatingActionButton(){
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.flaButtonMainPage);
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
        initShowUsersButon();
    }


    private void initShowUsersButon(){
        ImageView showUserImageView = (ImageView)findViewById(R.id.iconUsersList);
        showUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ContactListActivity.class);
                startActivity(i);
                finish();
            }
        });
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
}
