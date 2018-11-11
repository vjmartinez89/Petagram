package com.vjmartinez.petagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Victor Martinez on 20/10/2018
 * Adapter class for sw contact detail information in contact card view
 */
public class ContactDetailAdapter
        extends RecyclerView.Adapter<ContactDetailAdapter.ContactDetailViewHolder> {


    private List<Contact> contacts;
    private PetagramActivity activity;

    /**
     * Default constructor
     * @param contacts
     */
    public ContactDetailAdapter(List<Contact> contacts, PetagramActivity activity){
        this.contacts = contacts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ContactDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate card view layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_contact, parent,
                false);
        return new ContactDetailViewHolder(v); //Pass layout to view holder
    }

    //Fill the list contact object in position to view properties
    @Override
    public void onBindViewHolder(@NonNull ContactDetailViewHolder contactDetailViewHolder,
                                 int position) {
        final Contact contact = contacts.get(position);
        contactDetailViewHolder.imgContactProfile.setImageResource(contact.getPhoto());
        contactDetailViewHolder.tviCardviewContactName.setText(contact.getName());
        contactDetailViewHolder.tviCardviewContactPhone.setText(contact.getPhone());
        contactDetailViewHolder.tviCardviewContactEmail.setText(contact.getEmail());

        //Onclick event on user profile image
        contactDetailViewHolder.imgContactProfile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ObjectMapper objectMapper = new ObjectMapper();
                Bundle extras = new Bundle();
                try {
                    extras.putString("CONTACT_OBJECT", objectMapper.writeValueAsString(contact));
                }catch(Exception jse){
                    extras.putString("CONTACT_OBJECT", "" );
                    Log.e("Error", jse.getMessage(), jse);
                }
                activity.go(ContactDetailActivity.class, extras, true);
            }
        });

        //Onclick event on user like button (Using popup menu)
        contactDetailViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu =  new PopupMenu(activity, v);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mi_like:
                                activity.showToast(activity.getResources()
                                        .getString(R.string.msg_like)+" "+contact.getName());
                                break;
                            case R.id.mi_love:
                                activity.showToast(activity.getResources()
                                        .getString(R.string.msg_love)+" "+contact.getName());
                                break;
                        }
                        return true;
                    }
                });

                popupMenu.show();


            }
        });
    }

    /**
     * Return item list count
     * @return
     */
    @Override
    public int getItemCount() { //Total list item
        return contacts.size();
    }

    // Content class of RecyclerView
    public static class ContactDetailViewHolder extends ViewHolder{

        //Declare Card View component
        private ImageView imgContactProfile;
        private TextView tviCardviewContactName;
        private TextView tviCardviewContactPhone;
        private TextView tviCardviewContactEmail;
        private ImageButton btnLike;

        public ContactDetailViewHolder(View itemView){
            super(itemView);
            //Initialize card view component
            imgContactProfile = (ImageView) itemView.findViewById(R.id.imgContactProfile);
            tviCardviewContactName = (TextView)itemView.findViewById(R.id
                    .tvi_cardview_contact_name);
            tviCardviewContactPhone = (TextView) itemView.findViewById(R.id
                    .tvi_cardview_contact_phone);
            tviCardviewContactEmail = (TextView)itemView.findViewById(R.id
                    .tvi_cardview_contact_email);
            btnLike = (ImageButton)itemView.findViewById(R.id.btnLike);

        }

    }

}
