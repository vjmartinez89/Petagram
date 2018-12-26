package com.vjmartinez.petagram.db;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.vjmartinez.petagram.dao.ContactDAO;
import com.vjmartinez.petagram.dto.Contact;

import java.text.ParseException;
import java.util.List;

public class ContactBuilder {

    private Context context;
    public ContactBuilder(Context context){
        this.context = context;
    }

    /**
     * Get the contact list
     * @return The list of contacts
     */
    public List<Contact> getContactList() throws ParseException, SQLException {
        ContactDAO contactDAO;
        contactDAO = new ContactDAO(context);
        return contactDAO.getContactList();
    }

    /**
     * Insert a contact object in BD
     * @param contact The contact object
     */
    public void insertContact(Contact contact)  throws SQLException {
        ContactDAO contactDAO;
        contactDAO = new ContactDAO(context);
        contactDAO.insertContact(contact);
    }
}



