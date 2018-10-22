package com.vjmartinez.petagram;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
    private Activity activity;

    /**
     * Default constructor
     * @param contacts
     */
    public ContactDetailAdapter(List<Contact> contacts, Activity activity){
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

        contactDetailViewHolder.imgContactProfile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = new Intent(activity, ContactDetailActivity.class);
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    i.putExtra("CONTACT_OBJECT", objectMapper.writeValueAsString(contact));
                }catch(Exception jse){
                    i.putExtra("CONTACT_OBJECT", "" );
                    Log.e("Error", jse.getMessage(), jse);
                }
                activity.startActivity(i);
                activity.finish(); //Finish PetList Activity
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
        }

    }

}
