package com.example.favouritecaller.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favouritecaller.model.ContactModel;
import com.example.grandmascaller.MainActivity;
import com.example.favouritecaller.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewholder> {
    Context context;
    ArrayList<ContactModel> adapterList;
    public static final String TAG = ContactAdapter.class.getName();

    public ContactAdapter(Context context, ArrayList<ContactModel> adapterList) {
        this.context = context;
        this.adapterList = adapterList;
    }

    @NonNull
    @Override
    public ContactViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_items, parent,false);
        return new ContactViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewholder holder, int position) {

        final ContactModel contactList = adapterList.get(position);

        holder.contact_name.setText(contactList.getName());
        holder.contact_number.setText(contactList.getPhone_number());

        Uri photoUri = contactList.getPhoto_uri();
        if(photoUri == null){
            holder.contact_image.setImageResource(R.drawable.person);
        }
        else{
            holder.contact_image.setImageURI(photoUri);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), MainActivity.class);

                intent.putExtra("contact_name",contactList.getName());
                Log.d(TAG, "onClick: " + contactList.getName());

                intent.putExtra("contact_number",contactList.getPhone_number());
                Log.d(TAG, "onClick: " + contactList.getPhone_number());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Uri photoUri = contactList.getPhoto_uri();
                Log.d(TAG, "onClick: " + photoUri);

                if(photoUri == null){
                    intent.putExtra("photo_string",R.drawable.person);
                    Log.d(TAG, "onClick: " +R.drawable.person );
                }
                else{
                    intent.putExtra("photo_string",photoUri.toString());
                }
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class ContactViewholder extends RecyclerView.ViewHolder {
        TextView contact_name,contact_number;
        ImageView  contact_image;
        LinearLayout linearLayout;

        public ContactViewholder(@NonNull View itemView) {
            super(itemView);
            contact_image = itemView.findViewById(R.id.contact_thumbnail);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
            linearLayout = itemView.findViewById(R.id.linear_layout);

        }
    }
}
