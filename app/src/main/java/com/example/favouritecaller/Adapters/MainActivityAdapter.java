package com.example.favouritecaller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.favouritecaller.Database.ContactModel;
import com.example.favouritecaller.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MainActivityViewHolder>
{
    Context mContext;
    List<ContactModel> mList = new ArrayList<>();

    public MainActivityAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MainActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_recycler_view_items,parent,false);
        return new MainActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityViewHolder holder, int position) {
        ContactModel contactModel = mList.get(position);

        Glide
                .with(mContext)
                .load(contactModel.getPhoto_uri())
                .fallback(R.drawable.face)
                .into(holder.photoThumb);

        holder.name_tv.setText(contactModel.getContact_name().trim());

        mContext = holder.itemView.getContext();

        String phoneNumber = "tel:" + contactModel.getPhone_number().trim();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calling_intent = new Intent(Intent.ACTION_CALL);
                calling_intent.setData(Uri.parse(phoneNumber));
               mContext.startActivity(calling_intent);

                System.out.println(phoneNumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void getList(List<ContactModel> contactModelList) {
        mList  = contactModelList;
        notifyDataSetChanged();
    }

    public class MainActivityViewHolder extends RecyclerView.ViewHolder{

        ImageView photoThumb;
        TextView name_tv;

        public MainActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            photoThumb = itemView.findViewById(R.id.main_contact_iv);
            name_tv = itemView.findViewById(R.id.contact_name_tv);

        }
    }
}
