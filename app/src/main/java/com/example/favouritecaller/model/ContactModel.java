package com.example.favouritecaller.model;

import android.net.Uri;

public class ContactModel
{
    private String phone_number;
    private String name;
    private Uri photo_uri;
    private int photoId;

    public ContactModel(String name, String phone_number, Uri photo_uri) {
        this.name = name;
        this.phone_number = phone_number;
        this.photo_uri = photo_uri;
    }

    public ContactModel(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }

    public ContactModel(String name, String phone_number, int photoId) {
        this.name = name;
        this.phone_number = phone_number;
        this.photoId = photoId;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getName() {
        return name;
    }

    public Uri getPhoto_uri() {
        return photo_uri;
    }

    public int getPhotoId() {
        return photoId;
    }
}
