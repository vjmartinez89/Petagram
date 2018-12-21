package com.vjmartinez.petagram.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.vjmartinez.petagram.PetagramActivity;
import com.vjmartinez.petagram.R;
import com.vjmartinez.petagram.adapter.ContactPhotoAdapter;
import com.vjmartinez.petagram.dto.Contact;
import com.vjmartinez.petagram.dto.ContactPhoto;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    RecyclerView rvPrfPhotoList;
    CircularImageView civPrfPhoto;
    SwipeRefreshLayout srPrfPhotoList;
    PetagramActivity petagramActivity;
    TextView tviPrfContactName;
    Contact contact;


    public PetagramActivity getPetagramActivity() {
        return petagramActivity;
    }

    public void setPetagramActivity(PetagramActivity petagramActivity) {
        this.petagramActivity = petagramActivity;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        rvPrfPhotoList = view.findViewById(R.id.rv_prf_photo_list);
        srPrfPhotoList = view.findViewById(R.id.sr_prf_photo_list);
        civPrfPhoto = view.findViewById(R.id.civ_prf_photo);
        tviPrfContactName = view.findViewById(R.id.tvi_prf_contact_name);
        if(contact != null) {
            tviPrfContactName.setText(contact.getName());
        }
        initAdapters(view);
        initEvents();
        return view;
    }

    /**
     * Init adapters
     * @param v The view
     */
    public void initAdapters(View v) {
        GridLayoutManager gridLayoutManager =  new GridLayoutManager( v.getContext(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPrfPhotoList.setLayoutManager(gridLayoutManager);
        rvPrfPhotoList.setAdapter(new ContactPhotoAdapter(getPhotoList(), getPetagramActivity()));
    }

    /**
     * Init the events
     */
    public void initEvents() {
        srPrfPhotoList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }});
    }


    /**
     * Refresh contact photo list
     */
    private void refreshContent(){
        rvPrfPhotoList.setAdapter(new ContactPhotoAdapter( contact!=null ? contact.getPhotos()
                : getPhotoList(), getPetagramActivity()));
        srPrfPhotoList.setRefreshing(false);
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