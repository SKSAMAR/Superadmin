package com.fintech.webspidysoftware.activities.rechargeactivities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import com.fintech.webspidysoftware.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.webspidysoftware.adapters.ContactsAdapter;
import com.fintech.webspidysoftware.data.model.ContactsModel;
import com.fintech.webspidysoftware.databinding.ActivityMobileRechargeBinding;
import com.fintech.webspidysoftware.listeners.ContactsClickListener;
import com.fintech.webspidysoftware.viewmodel.MobileRechargeViewModel;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class MobileRecharge extends BaseActivity implements ContactsClickListener {

    ProgressDialog dialog;
    ActivityMobileRechargeBinding binding;
    ArrayList<ContactsModel> allContactsList;
    ContactsAdapter adapter;
    MobileRechargeViewModel rechargeViewModel;

    String opName, logo, opCode, purpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMobileRechargeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select Contacts");
        rechargeViewModel= new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        binding.setRechargeViewModel(rechargeViewModel);
        Bundle bundle = getIntent().getExtras();
        purpose = bundle.getString("purpose");
        if(purpose.equals("Recharge")){
            opName = bundle.getString("name");
            opCode = bundle.getString("operatorCode");
            logo = bundle.getString("logo");
        }
        else{
//            binding.enteredNumberSelf.setVisibility(View.GONE);
        }
        allContactsList = new ArrayList<>();
        binding.allContactsCard.setVisibility(View.GONE);
        dialog = new ProgressDialog(MobileRecharge.this);
        makeItWork();
        int search_plateId = getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = ((View) binding.contactsSearchView.findViewById(search_plateId));
        mSearchPlate.setBackgroundColor(Color.TRANSPARENT);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void makeItWork(){

        dialog.setMessage("Please wait, Loading..");
        dialog.show();

        Observable.just("one")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getContactList();
                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        binding.allContactsRecycler.setLayoutManager(new GridLayoutManager(MobileRecharge.this,1, GridLayoutManager.VERTICAL, false));
                        adapter = new ContactsAdapter(allContactsList, MobileRecharge.this);
                        binding.allContactsRecycler.setAdapter(adapter);
                        binding.allContactsRecycler.setOverScrollMode(View.OVER_SCROLL_NEVER);
                        binding.allContactsCard.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        searchAble();
                    }
                });
    }



    public void searchAble(){
        binding.contactsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                rechargeViewModel.enteredNumber = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                rechargeViewModel.enteredNumber = newText;
                return false;
            }
        });

    }



    private void getContactList(){


        //Initialize uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";
        //Initialize Cursor
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(uri, null, null, null, sort);

        if(cursor.getCount() > 0){

            while (cursor.moveToNext()){
                //ID
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                //Name
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                //Initialize phone uri
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                //Initialize selection
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?";

                //Initialize phone cursor
                Cursor phoneCursor = getContentResolver().query(uriPhone, null, selection, new String[]{id}, null);

                if(phoneCursor.moveToNext()){
                    @SuppressLint("Range") String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    String message = number.replaceAll("[^\\w]", "");

                    ContactsModel model = new ContactsModel(name, message);
                    allContactsList.add(model);
                    phoneCursor.close();
                }
            }
        }
    }


    @Override
    public void onRecyclerViewContactsClickListener(View view, ContactsModel model) {
        if(purpose.equals("Number")){


            String message = model.getNumber().replaceAll("[^\\w]", "");
            StringBuilder builder = new StringBuilder(message);
            if(builder.charAt(0)=='9' && builder.charAt(1)=='1'){
                builder.deleteCharAt(0);
                builder.deleteCharAt(0);
            }

            final Intent data = new Intent();
            data.putExtra("number",String.valueOf(builder));
            data.putExtra("name",model.getName());
            setResult(Activity.RESULT_OK, data);
            finish();

        }
        else{

            Intent intent = new Intent(MobileRecharge.this, RechargeMyPlan.class);
            intent.putExtra("name", model.getName());
            intent.putExtra("number", model.getNumber());
            intent.putExtra("operatorName", opName);
            intent.putExtra("operatorCode", opCode);
            intent.putExtra("logo", logo);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        if(purpose.equals("Number")) {
            setResult(Activity.RESULT_CANCELED);
        }
        super.onBackPressed();
    }

}