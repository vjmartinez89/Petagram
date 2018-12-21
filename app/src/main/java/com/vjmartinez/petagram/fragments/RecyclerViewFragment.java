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
import com.vjmartinez.petagram.dto.ContactPhoto;

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
     * Return the contact selected object
     * @return
     */
    public Contact getSelected(){
        return getContactList().get(0);
    }

    /**
     * Get a contact list
     * @return The contact list
     */
    private List<Contact> getContactList(){
        List<Contact> contacts = new ArrayList<>();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                    new Locale("es"));
            contacts.add(new Contact("Victor Julio Martinez Barrios",
                    "314 531 6197", "vijumaba89@gmail.com", R.drawable.ic_user_male,
                    dateFormat.parse("11/08/1989"),
                    "M", "Car. 59 # 70 - 349", 5, getPhotoList()));

            contacts.add(new Contact("Ana Milena Mejia Diaz",
                    "314 571 3323", "amdiaz220285@gmail.com", R.drawable.ic_user_female,
                    dateFormat.parse("22/02/1985"),
                    "F", "Car. 59 # 70 - 349", 2, getPhotoList()));

            contacts.add(new Contact("Nicole Martínez Mejía",
                    "314 531 2131", "nicole102014@gmail.com", R.drawable.ic_user_female,
                    dateFormat.parse("01/10/2014"),
                    "F", "Car. 59 # 70 - 349", 10, getPhotoList()));


        }catch(Exception ex){
            ex.printStackTrace();
        }
        return contacts;
    }


    /**
     * Get the photo list of user
     * @return the list of photos
     */
    private List<ContactPhoto> getPhotoList() {
        List<ContactPhoto> list = new ArrayList<>();
        list.add(new ContactPhoto(5,R.drawable.ic_user_male));
        list.add(new ContactPhoto(3,R.drawable.ic_user_female));
        list.add(new ContactPhoto(7,R.drawable.ic_user_male));
        list.add(new ContactPhoto(2,R.drawable.ic_user_female));
        list.add(new ContactPhoto(1,R.drawable.ic_user_male));
        list.add(new ContactPhoto(0,R.drawable.ic_user_female));
        return list;
    }
}
