package com.vjmartinez.petagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vjmartinez.petagram.PetagramActivity;
import com.vjmartinez.petagram.R;
import com.vjmartinez.petagram.adapter.ContactDetailAdapter;
import com.vjmartinez.petagram.dto.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewFragment extends Fragment {

    RecyclerView contacList;
    SwipeRefreshLayout swipeRefreshLayout;
    PetagramActivity petagramActivity;

    public PetagramActivity getPetagramActivity() {
        return petagramActivity;
    }

    public void setPetagramActivity(PetagramActivity petagramActivity) {
        this.petagramActivity = petagramActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        contacList = view.findViewById(R.id.rvContactList);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshMain);
        initAdapters(view);
        initEvents();
        return view;
    }


    public void initAdapters(View v) {
        /*We can use GridLayoutManager to see list in grid view
             GridLayoutManager gridLayoutManager =  new GridLayoutManager( this, 2);
        */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contacList.setLayoutManager(linearLayoutManager);
        contacList.setAdapter(new ContactDetailAdapter(getContactList(), getPetagramActivity()));
    }

    public void initEvents() {
      swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }});
    }


    /**
     * Refresh contact list
     */
    private void refreshContent(){
           contacList.setAdapter(new ContactDetailAdapter( getContactList(), getPetagramActivity()));
           swipeRefreshLayout.setRefreshing(false);
    }


    /**
     * Get a contact list
     * @return The contact list
     */
    private List<Contact> getContactList(){
        List<Contact> contacts = new ArrayList<>();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                    new Locale("es_CO"));
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
}
