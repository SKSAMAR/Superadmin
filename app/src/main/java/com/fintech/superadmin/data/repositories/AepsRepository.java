package com.fintech.superadmin.data.repositories;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.fintech.superadmin.R;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.databinding.PackageSelectorDesignBinding;
import com.fintech.superadmin.util.ViewUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class AepsRepository {

    public APIServices  apiServices;
    AppDatabase appDatabase;
    @Inject
    public AepsRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }


    public void bringPackagesList(Context context){
        User user = appDatabase.getUserDao().getRegularUser();
        //        MyAlertUtils.showProgressAlertDialog(context);
        PackageSelectorDesignBinding binding = PackageSelectorDesignBinding.inflate(LayoutInflater.from(context));
        Dialog dialog = new Dialog(context, R.style.BottomSheetDialogTheme);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        ArrayList<String> data = new ArrayList<>();
        for(int i = 0; i<50; i++){
            data.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, data);
        binding.MyPackageListView.setAdapter(adapter);
        dialog.show();

        binding.searchPackages.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });

        binding.MyPackageListView.setOnItemClickListener((parent, view, position, id) -> ViewUtils.showToast(context,adapter.getItem(position)));
    }
}
