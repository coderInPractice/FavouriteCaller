package com.example.favouritecaller.ViewModel;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.favouritecaller.Database.ContactDao;
import com.example.favouritecaller.Database.ContactDatabase;
import com.example.favouritecaller.Database.ContactModel;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactDatabase mContactDatabase;
    private ContactDao mContactDao;
    private ContactModel mContactModel;
    public ContactViewModel(@NonNull Application application) {
        super(application);

        mContactDatabase = ContactDatabase.getInstance(application);
        mContactDao = mContactDatabase.contactDao();
    }

    public LiveData<List<ContactModel>> getAllContact (){
        return mContactDao.getContact();
    }

    public void contactQuery(String look_up_key){
        Log.d("contactQuery: " , "entered." );
        ContentResolver resolver = getApplication().getContentResolver();
        String selection = ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY + " LIKE ? ";
        String [ ] selectionArgs = {look_up_key};

        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection, selectionArgs, null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                Log.d("contactQuery: ", "is successful");

                String id = look_up_key;

                System.out.println("LOOKUP_KEY : " + id);

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                System.out.println("name: " + name);

                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                System.out.println("number: " + number);

                String photoString = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));
                System.out.println("photo: " + photoString);

                mContactModel = new ContactModel(name, number, photoString, id);

                addContactIntoDb(mContactModel);
            }
        }

    }

    private void addContactIntoDb(ContactModel contactModel){
        ContactDatabase.databaseWriteExecutor.execute(()->{
            mContactDao.insert(contactModel);
        });
    }
}
