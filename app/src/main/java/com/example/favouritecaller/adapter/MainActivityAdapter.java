package com.example.favouritecaller.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favouritecaller.R;
import com.example.favouritecaller.model.ContactDbHelper;

import java.util.ArrayList;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MainActivityViewHolder>
{
    Context mContext;
    Cursor mCursor;

    public MainActivityAdapter(Context mContext, Cursor cursor) {
        this.mContext = mContext;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public MainActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_recycler_view_items,parent,false);
        return new MainActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityViewHolder holder, int position) {
            Uri photoUri;
//        final ContactModel contactModel = mainList.get(position);
//
//        int photoId = contactModel.getPhotoId();
//        Log.d("Viewholder: ", "photoId: " + photoId);
//
//        Uri photoUri = contactModel.getPhoto_uri();
//        Log.d("Viewholder", "photoUri: " + photoUri);
//
//        if(photoUri == null){
//            holder.photoThumb.setImageResource(photoId);
//        }
//        else{
//            holder.photoThumb.setImageURI(photoUri);
//        }

        if (!mCursor.moveToPosition(position))
            return;

        int photoId = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(ContactDbHelper.CONTACTS_PHOTO_ID)));
       Log.d("Viewholder: ", "photoId: " + photoId);

       String photoString = mCursor.getString(mCursor.getColumnIndex(ContactDbHelper.CONTACTS_PHOTO_URI));

       if(photoString == null){
           return;
       }else{
           photoUri = Uri.parse(photoString);
           Log.d("Viewholder", "photoUri: " + photoUri);
       }

       if(photoUri == null){
           holder.photoThumb.setImageResource(photoId);
       }else{
           holder.photoThumb.setImageURI(photoUri);
       }

        holder.photoThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber ="tel:" + mCursor.getString(mCursor.getColumnIndex(ContactDbHelper.CONTACTS_NUMBER)).trim();
                Log.d("phoneNumber: ", "onClick: " + phoneNumber);


                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(phoneNumber));

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class MainActivityViewHolder extends RecyclerView.ViewHolder{

        ImageView photoThumb;

        public MainActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            photoThumb = itemView.findViewById(R.id.main_contact_iv);

        }
    }
}
