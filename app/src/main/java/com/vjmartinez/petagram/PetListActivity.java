package com.vjmartinez.petagram;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PetListActivity extends PetagramActivity {

    //Global variables
    SwipeRefreshLayout swipeRefreshLayout;
    ListView contactList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        //Se toman los parametros enviados
        Bundle extras = getIntent().getExtras();
        String oppeningDate = extras.getString("KEY_DATE");
        //showToast(oppeningDate);
        final List<Contacto> contactos = getContactList();
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshMain);
        contactList = (ListView)findViewById(R.id.listViewMain);
        contactList.setAdapter(new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos));
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                   openPetDetail(contactos.get(position));
                                               }
                                           });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    /**
     * Create click listener
     * @return
     */
    private void openPetDetail(Contacto contacto) {
        Intent i = new Intent(getBaseContext(), PetDetailActivity.class);
        i.putExtra("CONTACT_NAME", contacto.getName());//Envia un parametro a la activity destino
        i.putExtra("CONTACT_PHONE", contacto.getPhone());//Envia un parametro a la activity destino
        i.putExtra("CONTACT_EMAIL", contacto.getEmail());//Envia un parametro a la activity destino
        startActivity(i);
    }

    /**
     * Refresh pet list
     */
    private void refreshContent(){
        final List<Contacto> contactos =  getContactList();
        contactList.setAdapter(new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos));
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPetDetail(contactos.get(position));
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Get a contact list
     * @return
     */
    private List<Contacto> getContactList(){
        List<Contacto> contacts = new ArrayList<>();

        contacts.add(new Contacto("Victor Julio Martinez Barrios",
                "314 531 6197", "vijumaba89@gmail.com"));

        contacts.add(new Contacto("Ana Milena Mejia Diaz",
                "314 571 3323", "amdiaz220285@gmail.com"));

        contacts.add(new Contacto("Nicole Martínez Mejía",
                "314 531 2131", "nicole102014@gmail.com"));

        return contacts;
    }
}
