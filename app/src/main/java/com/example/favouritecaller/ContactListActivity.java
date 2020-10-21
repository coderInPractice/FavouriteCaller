package com.example.favouritecaller;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favouritecaller.Adapters.ContactAdapter;
import com.example.favouritecaller.Database.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    RecyclerView contactRecyclerView;
    ContactAdapter contactAdapter;

    List<ContactModel> contactList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        contactRecyclerView = findViewById(R.id.contact_recycler_view);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            loadContacts();
        }
    }

    private void loadContacts() {
        ContentResolver resolver = getContentResolver();

        ContactModel contactModel;

        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));

                System.out.println("LOOKUP_KEY : " + id);

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                String photoString = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));

                contactModel = new ContactModel(name, number, photoString, id);
                contactList.add(contactModel);

            }
            cursor.close();
        } else {
            Toast.makeText(this, "No contacts found :(", Toast.LENGTH_SHORT).show();
        }

        contactAdapter = new ContactAdapter(this, contactList,getApplication());
        contactRecyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
            }
        }
    }
}