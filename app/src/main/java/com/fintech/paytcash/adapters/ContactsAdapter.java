package com.fintech.paytcash.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.paytcash.data.model.ContactsModel;
import com.fintech.paytcash.databinding.ContactListLayoutBinding;
import com.fintech.paytcash.listeners.ContactsClickListener;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> implements Filterable {


    ArrayList<ContactsModel> arrayListFilter;
    ArrayList<ContactsModel> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    private ContactsClickListener listener;


    public ContactsAdapter(ArrayList<ContactsModel> arrayList, ContactsClickListener listener){
        this.arrayList = arrayList;
        this.listener = listener;
        this.arrayListFilter = new ArrayList<>();
        this.arrayListFilter =arrayList;
    }


    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater==null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        ContactListLayoutBinding binding = ContactListLayoutBinding.inflate(inflater);
        return new ContactsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.setMyContactList(arrayListFilter.get(position));
        holder.binding.getRoot().setOnClickListener(v -> listener.onRecyclerViewContactsClickListener(v, arrayListFilter.get(position)));
    }

    @Override
    public int getItemCount() {
        if(arrayListFilter == null){
            return 0;
        }
        return arrayListFilter.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String character = constraint.toString();
                if(character.isEmpty()){
                    arrayListFilter = arrayList;
                }
                else{
                    ArrayList<ContactsModel> filterList = new ArrayList<>();
                    for(ContactsModel p : arrayList){
                        if(p.getNumber().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                        else if(p.getName().toLowerCase().contains(character.toLowerCase())){
                            filterList.add(p);
                        }
                    }
                    arrayListFilter = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayListFilter = (ArrayList<ContactsModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }





    public static class ContactsViewHolder extends RecyclerView.ViewHolder{

        ContactListLayoutBinding binding;
        public ContactsViewHolder(@NonNull ContactListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
