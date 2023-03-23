package com.fintech.payware.activities.mobilenumber;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import com.fintech.payware.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.payware.R;
import com.fintech.payware.adapters.ContactsAdapter;
import com.fintech.payware.data.model.ContactsModel;
import com.fintech.payware.data.network.responses.AuthResponse;
import com.fintech.payware.databinding.ActivityMobileNumberHomePageBinding;
import com.fintech.payware.listeners.ContactsClickListener;
import com.fintech.payware.listeners.NumberPayListener;
import com.fintech.payware.util.ExecuteUtil;
import com.fintech.payware.util.ViewUtils;
import com.fintech.payware.viewmodel.MobileNumberPayViewModel;

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
public class MobileNumberPay extends BaseActivity implements NumberPayListener, ContactsClickListener {

    private static final String[] PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };


    ProgressDialog dialog;
    ArrayList<ContactsModel> allContactsList;
    ContactsAdapter adapter;
    ActivityMobileNumberHomePageBinding binding;
    MobileNumberPayViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMobileNumberHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mobile Number");
        viewModel = new ViewModelProvider(this).get(MobileNumberPayViewModel.class);
        binding.setMobileNumbersViewModel(viewModel);
        viewModel.listener = this;
        allContactsList = new ArrayList<>();
        binding.allContactsCard.setVisibility(View.GONE);
        dialog = new ProgressDialog(MobileNumberPay.this);
//        makeItWork();
        //dialog.show();
        new TestAsync().execute();
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
        else if(item.getItemId() == R.id.homePage){
            ExecuteUtil.ThrowOut(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.response_menu, menu);
        menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRecyclerViewContactsClickListener(View view, ContactsModel model) {
        viewModel.checkIfAccountExists(model.getNumber());
    }

    private void makeItWork(){
        dialog.setMessage("Please wait, Loading..");
        dialog.show();

        Observable.just("one")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        contactsHere();
                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        binding.allContactsRecycler.setLayoutManager(new GridLayoutManager(MobileNumberPay.this,1, GridLayoutManager.VERTICAL, false));
                        adapter = new ContactsAdapter(allContactsList, MobileNumberPay.this);
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }




    private void contactsHere(){
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);


                while (cursor.moveToNext()) {
                  String name = cursor.getString(nameIndex);
                  String number = cursor.getString(numberIndex);
//                    String message = number.replaceAll("[^\\w]", "");
//                    StringBuilder builder = new StringBuilder(message);
//                    if(builder.charAt(0)=='0'){
//                        builder.deleteCharAt(0);
//                    }
//                    if(builder.charAt(0)=='9' && builder.charAt(1)=='1'){
//                        builder.deleteCharAt(0);
//                        builder.deleteCharAt(0);
//                    }
                    ContactsModel model = new ContactsModel(name, number);
                    allContactsList.add(model);

                }
            } finally {
                cursor.close();
            }
        }
    }



    @Override
    public void isNumberValid(AuthResponse authResponse) {
        if(authResponse.isStatus()){
            Intent intent = new Intent(MobileNumberPay.this, SendMoney.class);
            intent.putExtra("receiver_id", authResponse.getUser().getId());
            intent.putExtra("receiver_name", authResponse.getUser().getName()+" "+authResponse.getUser().getLastname());
            intent.putExtra("receiver_mobile", authResponse.getUser().getMobile());
            startActivity(intent);
        }else{
            ViewUtils.showToast(MobileNumberPay.this, authResponse.getMessage());
        }
        dialog.dismiss();

    }

    @Override
    public void showMessage(String message) {
        dialog.setTitle(message);
        dialog.show();
    }

    @Override
    public void showProgress(String message) {
        dialog.setTitle(message);
        dialog.show();
    }


    public class TestAsync extends AsyncTask<Void, Void, ArrayList<ContactsModel>> {
        String TAG = getClass().getSimpleName();
        ArrayList<ContactsModel> arrayList = new ArrayList<>();
        @Override
        protected ArrayList<ContactsModel> doInBackground(Void... voids) {
            String lastnumber = "";
            ContentResolver cr = getContentResolver();
            Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                try {
                    final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String name, number;
                    while (cursor.moveToNext()) {
                        name = cursor.getString(nameIndex);
                        number = cursor.getString(numberIndex).trim();
                        number = number.replaceAll("\\s", "");
                        if (number.equals(lastnumber)) {

                        } else {
                            lastnumber = number;
                            ContactsModel model = new ContactsModel(name,number);
                            arrayList.add(model);
                            publishProgress();
                        }
                    }
                } finally {
                    cursor.close();
                }
            }

            return arrayList;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            binding.allContactsRecycler.setLayoutManager(new GridLayoutManager(MobileNumberPay.this,1, GridLayoutManager.VERTICAL, false));
            adapter = new ContactsAdapter(arrayList, MobileNumberPay.this);
            binding.allContactsRecycler.setAdapter(adapter);
            binding.allContactsRecycler.setOverScrollMode(View.OVER_SCROLL_NEVER);
            binding.allContactsCard.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<ContactsModel> contactsModels) {
            super.onPostExecute(contactsModels);
            searchAble();
            //dialog.dismiss();
        }
    }

}