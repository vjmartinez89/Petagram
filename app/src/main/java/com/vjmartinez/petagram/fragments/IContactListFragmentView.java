package com.vjmartinez.petagram.fragments;

import com.vjmartinez.petagram.adapter.ContactDetailAdapter;
import com.vjmartinez.petagram.dto.Contact;

import java.util.List;

public interface IContactListFragmentView {


    void generateVerticalLinearLayout();

    ContactDetailAdapter createContactDetailAdapter(List<Contact> contacts);

    void initAdapter(ContactDetailAdapter contactDetailAdapter);
}
