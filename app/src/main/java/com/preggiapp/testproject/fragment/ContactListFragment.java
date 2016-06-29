package com.preggiapp.testproject.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.preggiapp.testproject.R;
import com.preggiapp.testproject.activity.MainActivity;
import com.preggiapp.testproject.adapter.ContactAdapter;
import com.preggiapp.testproject.data.AppData;
import com.preggiapp.testproject.model.Contact;
import org.apache.commons.validator.routines.EmailValidator;
import java.util.ArrayList;

public class ContactListFragment extends BaseConnectionFragment implements LoaderManager.LoaderCallbacks<Cursor> {

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


    private ContactAdapter adapter;
    private RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        commonView = inflater.inflate(R.layout.contactlist_fragment, container, false);
        list = (RecyclerView)commonView.findViewById(R.id.list);
        list.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        return commonView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).enableBackButton();
        getActivity().getSupportLoaderManager().initLoader(666, Bundle.EMPTY, this);
    }

    @Override
    public void update() {
        if(AppData.getInstance().getContactList() != null) {
            adapter = new ContactAdapter(getActivity(), AppData.getInstance().getContactList());
            list.setAdapter(adapter);
        }
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
        AppData.getInstance().setContactList(array);
        stopProgressDialog();
        update();
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
                        getActivity(),
                        ContactsContract.Data.CONTENT_URI,
                        PROJECTION,
                        SELECTION,
                        null,
                        SORT_ORDER
                );
        return loader;
    }
}