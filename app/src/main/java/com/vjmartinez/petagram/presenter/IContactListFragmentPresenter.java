package com.vjmartinez.petagram.presenter;

import com.vjmartinez.petagram.dto.Contact;

import java.sql.SQLException;
import java.text.ParseException;

public interface IContactListFragmentPresenter {

    void getContactList() throws ParseException, SQLException;

    void showContacts();

}
