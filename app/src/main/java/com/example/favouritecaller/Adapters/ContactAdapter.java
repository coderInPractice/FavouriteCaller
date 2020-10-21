package com.example.favouritecaller.Adapters;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.favouritecaller.MainActivity;
import com.example.favouritecaller.R;
import com.example.favouritecaller.Database.ContactModel;
import com.example.favouritecaller.ViewModel.ContactViewModel;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewholder> {
    ContactViewModel mContactViewModel;
    Application mApplication;
    Context context;
    List<ContactModel> adapterList;
    public static final String TAG = ContactAdapter.class.getName();

    public ContactAdapter(Context context, List<ContactModel> adapterList,Application mApplication) {
        this.context = context;
        this.adapterList = adapterList;
        this.mApplication = mApplication;
        mContactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(mApplication).create(ContactViewModel.class);
    }

    @NonNull
    @Override
    public ContactViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_items, parent,false);
        return new ContactViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewholder holder, int position) {

        final ContactModel contactModel = adapterList.get(position);

        holder.contact_name.setText(contactModel.getContact_name());
        holder.contact_number.setText(contactModel.getPhone_number());

        Glide
                .with(context)
                .load(contactModel.getPhoto_uri())
                .circleCrop()
                .fallback(R.drawable.face)
                .into(holder.contact_image);

        String look_up_key = contactModel.getContact_Id();


        context = holder.itemView.getContext();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, look_up_key);

//                Intent main_activity_intent = new Intent(context, MainActivity.class);
//                main_activity_intent.putExtra("look_up_key",look_up_key);
//
//                context.startActivity(main_activity_intent);
                mContactViewModel.contactQuery(look_up_key);

                ((Activity)context).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public static class ContactViewholder extends RecyclerView.ViewHolder {
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
