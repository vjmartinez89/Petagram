package com.vjmartinez.petagram.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vjmartinez.petagram.R;
import com.vjmartinez.petagram.db.DataBaseHelper;
import com.vjmartinez.petagram.db.DatabaseConstants;
import com.vjmartinez.petagram.dto.Contact;
import com.vjmartinez.petagram.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactDAO {
    private Context context;

    public ContactDAO(Context context){
        this.context = context;
    }

    public List<Contact> getContactList() throws ParseException, SQLException{
        DataBaseHelper dataBaseHelper;
        List<Contact> contacts = new ArrayList<>();
        Cursor rs = null;
        SQLiteDatabase db = null;
        try {
            dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getWritableDatabase();
            String sql = dataBaseHelper.getSqlProperty("select.all.contacts");
            rs = db.rawQuery(sql, null);
            Contact contact;

            /**
             * ID, NAME, PHONE, EMAIL, PHOTO, SEX, ADDRESS, BIRTH_DATE, \
             *                     LIKES, LOVES FROM CONTACTS
             */
            while (rs.moveToNext()) {
                int index = 0;
                contact = new Contact();
                contact.setId(rs.getInt(index));
                contact.setName(rs.getString(++index));
                contact.setPhone(rs.getString(++index));
                contact.setEmail(rs.getString(++index));
                contact.setPhoto(rs.getInt(++index));
                contact.setSex(rs.getString(++index));
                contact.setAddress(rs.getString(++index));
                String date = rs.getString(++index);
                if (!StringUtils.isEmpty(date)) {
                    contact.setBirthDate(new SimpleDateFormat("dd/MM/yyyy",
                            new Locale("es")).parse(date));
                }
                contact.setLikes(rs.getInt(++index));
                contact.setLoves(rs.getInt(++index));
                contacts.add(contact);
            }
        }catch(ParseException pe){
            Log.e("ERROR", "Parsing error " + pe.getMessage(), pe);
            throw pe;
        }catch (SQLException ex){
            Log.e("ERROR", "Database error "+ ex.getMessage(), ex);
            throw ex;
        }finally {
            if(rs!=null && !rs.isClosed()){
                rs.close();
            }
            if(db != null){
                db.close();
            }
        }
        return contacts;
    }

    /**
     * Insert a contact in BD
     * @param contact the contact object to insert
     */
    public void insertContact(Contact contact)
    {
        DataBaseHelper dataBaseHelper;
        SQLiteDatabase db = null;
        ContentValues contentValues;
        try {
            contentValues = buildContact(contact);
            dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getWritableDatabase();
            db.insert(DatabaseConstants.TABLE_CONTACTS, null,  contentValues);
        }catch (SQLException ex){
            Log.e("ERROR", "Database error "+ ex.getMessage(), ex);
            throw ex;
        }finally {
            if(db != null){
                db.close();
            }
        }
    }


    /**
     * Update likes and love quantity
     * @param contact the contact object to update
     */
    public void addLike(Contact contact)
    {
        DataBaseHelper dataBaseHelper;
        SQLiteDatabase db = null;
        ContentValues contentValues;
        try {
            contentValues = new ContentValues();
            contentValues.put(DatabaseConstants.TABLE_CONTACTS_LIKES, contact.getLikes());
            contentValues.put(DatabaseConstants.TABLE_CONTACTS_LOVES, contact.getLoves());
            dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getWritableDatabase();
            db.update(DatabaseConstants.TABLE_CONTACTS,  contentValues,
                    " " + DatabaseConstants.TABLE_CONTACTS_ID + " = ?",
                    new String[]{String.valueOf(contact.getId())});
        }catch (SQLException ex){
            Log.e("ERROR", "Database error "+ ex.getMessage(), ex);
            throw ex;
        }finally {
            if(db != null){
                db.close();
            }
        }
    }

    /**
     * Build a ContentValues object from Contact
     * @param contact The Contact object
     * @return The ContentValues object
     */
    private ContentValues buildContact(Contact contact){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_NAME, contact.getName());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_PHONE, contact.getPhone());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_EMAIL, contact.getEmail());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_SEX, contact.getSex());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_PHONE, contact.getPhone());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_EMAIL, contact.getEmail());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_NAME, contact.getName());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_BIRTH_DATE, new SimpleDateFormat(
                "dd/MM/yyyy", new Locale("es"))
                .format(contact.getBirthDate()));
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_LIKES, contact.getLikes());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_LOVES, contact.getLoves());
        contentValues.put(DatabaseConstants.TABLE_CONTACTS_PHOTO, StringUtils.nvl(contact.getSex(),
                "M", true).equalsIgnoreCase("M") ? R.drawable.ic_user_male :
                         R.drawable.ic_user_female);
        return contentValues;
    }


    /**
     * Obtain a contact object information
     * @param contact The contact with id parameter
     * @return The contact data object
     * @throws ParseException When birth date is wrong
     * @throws SQLException When database fail
     */
    public Contact getContact(Contact contact) throws ParseException, SQLException{
        DataBaseHelper dataBaseHelper;
        Contact res = null;
        Cursor rs = null;
        SQLiteDatabase db = null;
        try {
            dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getWritableDatabase();
            String sql = dataBaseHelper.getSqlProperty("select.one.contact");
            rs = db.rawQuery(sql, new String[]{String.valueOf(contact.getId())});

            /**
             * ID, NAME, PHONE, EMAIL, PHOTO, SEX, ADDRESS, BIRTH_DATE, \
             *                     LIKES, LOVES FROM CONTACTS
             */
            while (rs.moveToNext()) {
                int index = 0;
                res = new Contact();
                res.setId(rs.getInt(index));
                res.setName(rs.getString(++index));
                res.setPhone(rs.getString(++index));
                res.setEmail(rs.getString(++index));
                res.setPhoto(rs.getInt(++index));
                res.setSex(rs.getString(++index));
                res.setAddress(rs.getString(++index));
                String date = rs.getString(++index);
                if (!StringUtils.isEmpty(date)) {
                    res.setBirthDate(new SimpleDateFormat("dd/MM/yyyy",
                            new Locale("es")).parse(date));
                }
                res.setLikes(rs.getInt(++index));
                res.setLoves(rs.getInt(++index));
            }
        }catch(ParseException pe){
            Log.e("ERROR", "Parsing error " + pe.getMessage(), pe);
            throw pe;
        }catch (SQLException ex){
            Log.e("ERROR", "Database error "+ ex.getMessage(), ex);
            throw ex;
        }finally {
            if(rs!=null && !rs.isClosed()){
                rs.close();
            }
            if(db != null){
                db.close();
            }
        }
        return res;
    }


    /**
     * Delete likes and love quantity
     * @param contact the contact object to update
     */
    public void deleteContact(Contact contact)
    {
        DataBaseHelper dataBaseHelper;
        SQLiteDatabase db = null;
        try {
            dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getWritableDatabase();
            db.delete(DatabaseConstants.TABLE_CONTACTS,
                    " " + DatabaseConstants.TABLE_CONTACTS_ID + " = ?",
                    new String[]{String.valueOf(contact.getId())});
        }catch (SQLException ex){
            Log.e("ERROR", "Database error "+ ex.getMessage(), ex);
            throw ex;
        }finally {
            if(db != null){
                db.close();
            }
        }
    }
}
