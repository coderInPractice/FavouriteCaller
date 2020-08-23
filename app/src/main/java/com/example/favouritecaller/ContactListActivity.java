package com.example.favouritecaller;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favouritecaller.adapter.ContactAdapter;
import com.example.favouritecaller.model.ContactModel;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        myRecyclerView = findViewById(R.id.contact_recycler_view);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        && checkSelfPermission(Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},1);
        }
        else{
            loadContacts();
        }
    }

    private void loadContacts() {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);

        ArrayList<ContactModel> contactList = new ArrayList<>();
        ContactModel contactModel;
        assert cursor != null;
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                  //String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String photoString  = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));

                if(photoString == null){
                    contactModel = new ContactModel(name,number);
                }
                else{
                    Uri photoUri = Uri.parse(photoString);
                    contactModel = new ContactModel(name,number,photoUri);
                }
                contactList.add(contactModel);
            }
            cursor.close();
        }
        else{
            Toast.makeText(this, "No contacts found :(", Toast.LENGTH_SHORT).show();
        }

        contactAdapter = new ContactAdapter(getApplicationContext(),contactList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myRecyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                loadContacts();
            }
        }
    }
}