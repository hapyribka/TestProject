package com.preggiapp.testproject.gui.contactlist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import com.preggiapp.testproject.gui.base.BasePresenter;
import com.preggiapp.testproject.model.Contact;
import org.apache.commons.validator.routines.EmailValidator;
import java.util.ArrayList;

public class ContactListPresenter extends BasePresenter<ContactListMvpView> implements  LoaderManager.LoaderCallbacks<Cursor> {

    final static String SORT_ORDER = ContactsContract.Contacts.SORT_KEY_PRIMARY;

    final static String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY ,
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
    };

    final static String SELECTION = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY  + "<>'' AND " +
            ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1 AND " +
            ContactsContract.CommonDataKinds.Email.ADDRESS + " IS NOT NULL";


    public void attachView(ContactListMvpView mvpView) {
        super.attachView(mvpView);
        startProgressDialog();
        ((Fragment)getMvpView()).getActivity().getSupportLoaderManager().initLoader(666, Bundle.EMPTY, this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<Contact> array = new ArrayList<>();
        while(data.moveToNext()) {
            String[] arr = new String[data.getColumnNames().length];
            for (int i = 0; i < data.getColumnNames().length; i++) {
                arr[i] = data.getString(data.getColumnIndex(data.getColumnNames()[i]));
            }
            if (EmailValidator.getInstance().isValid(arr[2].trim())) {
                array.add(new Contact(arr[1], arr[3], arr[2]));
                Log.i("ContactListFr", "Contact: " + arr[0] + " | " + arr[1] + " | " + arr[2] + " | " + arr[3]);
            }
        }
        getMvpView().updateList(array);
        stopProgressDialog();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        stopProgressDialog();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        startProgressDialog();
        CursorLoader loader =
                new CursorLoader(
                        getContext(),
                        ContactsContract.Data.CONTENT_URI,
                        PROJECTION,
                        SELECTION,
                        null,
                        SORT_ORDER
                );
        return loader;
    }
}