package com.example.favouritecaller.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(ContactModel contactModel);

    @Query("SELECT * FROM my_contacts")
    LiveData<List<ContactModel>> getContact();

}
