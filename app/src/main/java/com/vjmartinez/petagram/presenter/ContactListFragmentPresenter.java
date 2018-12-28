package com.vjmartinez.petagram.presenter;

import android.content.Context;

import com.vjmartinez.petagram.db.ContactBuilder;
import com.vjmartinez.petagram.dto.Contact;
import com.vjmartinez.petagram.fragments.IContactListFragmentView;

import java.text.ParseException;
import java.util.List;

public class ContactListFragmentPresenter implements IContactListFragmentPresenter {

    private IContactListFragmentView iContactListFragmentView;
    private Context context;
    private ContactBuilder contactBuilder;
    List<Contact> contacts = null;

    public ContactListFragmentPresenter(IContactListFragmentView iContactListFragmentView,
                                        Context context)throws Exception{
        this.iContactListFragmentView = iContactListFragmentView;
        this.context = context;
        getContactList();
    }

    @Override
    public void getContactList() throws ParseException {
        contactBuilder = new ContactBuilder(context);
        contacts = contactBuilder.getContactList();
        showContacts();
    }

    @Override
    public void showContacts() {
        iContactListFragmentView.initAdapter(iContactListFragmentView
                .createContactDetailAdapter(contacts));
        iContactListFragmentView.generateVerticalLinearLayout();
    }
}
