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
import com.vjmartinez.petagram.presenter.ContactListFragmentPresenter;
import com.vjmartinez.petagram.presenter.IContactListFragmentPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactListFragment extends Fragment implements IContactListFragmentView {

    RecyclerView contacList;
    SwipeRefreshLayout swipeRefreshLayout;
    PetagramActivity petagramActivity;
    private IContactListFragmentPresenter presenter;

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
        //Initialize the presenter to show data
        try {
            presenter = new ContactListFragmentPresenter(this, getContext());
        }catch(Exception ex){
            petagramActivity.showToast(getString(R.string.error_list_contact));
        }
        initEvents();
        return view;
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
        try {
            presenter.getContactList();
        }catch(Exception ex){
            petagramActivity.showToast(getString(R.string.error_list_contact));
        }finally {
            swipeRefreshLayout.setRefreshing(false);
        }
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
                    "M", "Car. 59 # 70 - 349", 5, 3, getPhotoList()));

            contacts.add(new Contact("Ana Milena Mejia Diaz",
                    "314 571 3323", "amdiaz220285@gmail.com", R.drawable.ic_user_female,
                    dateFormat.parse("22/02/1985"),
                    "F", "Car. 59 # 70 - 349", 2, 0, getPhotoList()));

            contacts.add(new Contact("Nicole Martínez Mejía",
                    "314 531 2131", "nicole102014@gmail.com", R.drawable.ic_user_female,
                    dateFormat.parse("01/10/2014"),
                    "F", "Car. 59 # 70 - 349", 10, 3, getPhotoList()));


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
        list.add(new ContactPhoto(5,3,R.drawable.ic_user_male));
        list.add(new ContactPhoto(3,6,R.drawable.ic_user_female));
        list.add(new ContactPhoto(7,1,R.drawable.ic_user_male));
        list.add(new ContactPhoto(2,8,R.drawable.ic_user_female));
        list.add(new ContactPhoto(1,3,R.drawable.ic_user_male));
        list.add(new ContactPhoto(0,2,R.drawable.ic_user_female));
        return list;
    }

    @Override
    public void generateVerticalLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contacList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public ContactDetailAdapter createContactDetailAdapter(List<Contact> contacts) {
        return new ContactDetailAdapter(contacts, getPetagramActivity());
    }

    @Override
    public void initAdapter(ContactDetailAdapter contactDetailAdapter) {
        contacList.setAdapter(contactDetailAdapter);
    }
}
