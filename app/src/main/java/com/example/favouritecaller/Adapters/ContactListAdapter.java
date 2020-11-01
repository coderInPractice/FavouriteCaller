package com.example.favouritecaller.Adapters;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.favouritecaller.Database.ContactModel;
import com.example.favouritecaller.R;
import com.example.favouritecaller.ViewModel.ContactViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> implements Filterable {

    ContactViewModel mContactViewModel;
    List<ContactModel> mList;
    Context mContext;
    Application mApplication;
    List<ContactModel> mListAll;

    public ContactListAdapter(List<ContactModel> mList, Context mContext, Application mApplication) {
        this.mList = mList;
        this.mContext = mContext;
        this.mApplication = mApplication;
        this.mListAll = new ArrayList<>(mList);

        mContactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(mApplication).create(ContactViewModel.class);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_list_items,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        ContactModel  contactModel = mList.get(position);

        holder.contact_name.setText(contactModel.getContact_name());
        holder.contact_number.setText(contactModel.getPhone_number());

        Glide
                .with(mContext)
                .load(contactModel.getPhoto_uri())
                .fallback(R.drawable.face)
                .circleCrop()
                .into(holder.contact_image);

        mContext = holder.itemView.getContext();

        String look_up_key = contactModel.getContact_Id();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContactViewModel.contactQuery(look_up_key);
                ((Activity) mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ContactModel> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(mListAll);
            }else {
               for(ContactModel element : mListAll){
                   if(element.getContact_name().toLowerCase().contains(charSequence.toString().toLowerCase())){
                       filteredList.add(element);
                   }
               }
            }

            FilterResults filterResults = new FilterResults();

            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mList.clear();
            mList.addAll((Collection<? extends ContactModel>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        ImageView contact_image;
        TextView contact_name,contact_number;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contact_image = itemView.findViewById(R.id.contact_thumbnail);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
        }
    }
}
