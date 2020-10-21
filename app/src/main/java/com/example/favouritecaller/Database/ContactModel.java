package com.example.favouritecaller.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_contacts")
public class ContactModel
{
    private String phone_number;

    @NonNull
    private String contact_name;

    @ColumnInfo(name = "contact_photo_url")
    private String photo_uri;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "look_up_key")
    private String contact_Id;

    public ContactModel(String contact_name, String phone_number, String photo_uri,String contact_Id) {
        this.contact_name = contact_name;
        this.phone_number = phone_number;
        this.photo_uri = photo_uri;
        this.contact_Id = contact_Id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }

    public String getContact_Id() {
        return contact_Id;
    }

    public void setContact_Id(String contact_Id) {
        this.contact_Id = contact_Id;
    }
}
