package com.vjmartinez.petagram;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends PetagramActivity {

    //Global variables
    SwipeRefreshLayout swipeRefreshLayout;
    ListView planetList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFloatingActionButton();
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshMain);
        planetList = (ListView)findViewById(R.id.listViewMain);
        String[] planets = getResources().getStringArray(R.array.planets);
        planetList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planets));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent(){
        String[] planets = getResources().getStringArray(R.array.planets);
        planetList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planets));
        swipeRefreshLayout.setRefreshing(false);
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
    }


    private void initShowPetsButton(){
        Button showPetsButton = (Button)findViewById(R.id.btnMainPage);
        showPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), PetListActivity.class);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                i.putExtra("KEY_DATE",simpleDateFormat.format(Calendar.getInstance().getTime()));//Envia un parametro a la activity destino
                startActivity(i);
            }
        });
    }


}
