package com.vjmartinez.petagram;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends PetagramActivity {

    //Global variables
    SwipeRefreshLayout swipeRefreshLayout;
   // ListView contactList;
    Adapter adapter;
    RecyclerView contacList;
    private Toolbar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

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

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshMain);

        contacList = (RecyclerView) findViewById(R.id.rvContactList);

        /*We can use GridLayoutManager to see list in grid view
             GridLayoutManager gridLayoutManager =  new GridLayoutManager( this, 2);
        */

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contacList.setLayoutManager(linearLayoutManager);
        contacList.setAdapter(new ContactDetailAdapter(getContactList(), this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    /**
     * Refresh contact list
     */
    private void refreshContent(){
        contacList.setAdapter(new ContactDetailAdapter( getContactList(), this));
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Get a contact list
     * @return
     */
    private List<Contact> getContactList(){
        List<Contact> contacts = new ArrayList<>();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            contacts.add(new Contact("Victor Julio Martinez Barrios",
                    "314 531 6197", "vijumaba89@gmail.com", R.drawable.ic_user_male,
                    dateFormat.parse("11/08/1989"),
                    "M", "Car. 59 # 70 - 349"));

            contacts.add(new Contact("Ana Milena Mejia Diaz",
                    "314 571 3323", "amdiaz220285@gmail.com", R.drawable.ic_user_female,
                    dateFormat.parse("22/02/1985"),
                    "F", "Car. 59 # 70 - 349"));

            contacts.add(new Contact("Nicole Martínez Mejía",
                    "314 531 2131", "nicole102014@gmail.com", R.drawable.ic_user_female,
                    dateFormat.parse("01/10/2014"),
                    "F", "Car. 59 # 70 - 349"));


        }catch(Exception ex){
            ex.printStackTrace();
        }
        return contacts;
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

    private void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
