package com.example.favouritecaller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favouritecaller.adapter.MainActivityAdapter;
import com.example.favouritecaller.model.ContactDbHelper;
import com.example.favouritecaller.model.ContactModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    public static final String TAG = MainActivity.class.getName();

   // ArrayList<ContactModel> contactList;
    RecyclerView mainRecyclerview;
    MainActivityAdapter mainAdapter;
    Intent intent;
    ContactDbHelper contactDbHelper = new ContactDbHelper(this);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerview = findViewById(R.id.main_recycler_view);

        intent = getIntent();
        dataFromContactList(intent);

        Cursor mCursor = contactDbHelper.getAllContacts();
        mainAdapter = new MainActivityAdapter(this,mCursor);

        mainRecyclerview.setLayoutManager(new GridLayoutManager(this,3));

        mainRecyclerview.setAdapter(mainAdapter);

       if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
           requestPermissions(new String[] {Manifest.permission.CALL_PHONE},1);
       }
       else{
           dataFromContactList(intent);
       }

    }

    private void dataFromContactList(Intent intent) {
       // contactList = new ArrayList<>();
        ContactModel contactModel;

        Uri photoUri;

        String name = intent.getStringExtra("contact_name");
        Log.d(TAG, "dataFromContactList: name " + name);

        String number = intent.getStringExtra("contact_number");
        Log.d(TAG, "dataFromContactList: number " + number);

        String photoString = intent.getStringExtra("photo_string");
        Log.d(TAG, "dataFromContactList: photoString " + photoString);

        if(photoString == null){
            return;
        }
        else{
            photoUri = Uri.parse(photoString);
        }

        int photoId = intent.getIntExtra("photo_string",R.drawable.person);
        Log.d(TAG, "dataFromContactList: photoId " + photoId);

        if(photoUri == null){
            contactModel = new ContactModel(name,number,photoId);
        }
        else{
            contactModel = new ContactModel(name,number,photoUri);
        }

        Log.d("contactModel: ", "dataFromContactList: " + contactModel.getPhotoId() + " " + contactModel.getName()
                + " " + contactModel.getPhone_number() + " " + contactModel.getPhoto_uri());

        contactDbHelper.addContacts(contactModel);


        //contactList.add(contactModel);

//        for(ContactModel  e : contactList){
//            Log.d("Contact List: ", "dataFromContactList:  contactList Items: " + e.getName() + " " + e.getPhone_number() + " "  + e.getPhoto_uri() + " " + e.getPhotoId());
//        }
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(getApplicationContext(),ContactListActivity.class);
        startActivity(intent);

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dataFromContactList(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainAdapter.swapCursor(contactDbHelper.getAllContacts());
    }
}