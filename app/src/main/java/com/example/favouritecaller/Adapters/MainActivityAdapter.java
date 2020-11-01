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

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    Context mContext;
    List<ContactModel> mList = new ArrayList<>();

    public MainActivityAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_recycler_view_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContactModel savedContactModel = mList.get(position);

        holder.saved_contact_name.setText(savedContactModel.getContact_name());

        Glide
                .with(mContext)
                .load(savedContactModel.getPhoto_uri())
                .fallback(R.drawable.face)
                .into(holder.saved_contact_pic);

        String phone_number = "tel:" + savedContactModel.getPhone_number().trim();

        mContext = holder.itemView.getContext();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call_intent = new Intent(Intent.ACTION_CALL);
                call_intent.setData(Uri.parse(phone_number));
                view.getContext().startActivity(call_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void getList(List<ContactModel> contactModelList){
        mList = contactModelList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView saved_contact_pic;
        TextView saved_contact_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            saved_contact_name = itemView.findViewById(R.id.contact_name_tv);
            saved_contact_pic = itemView.findViewById(R.id.main_contact_iv);
        }
    }
}
