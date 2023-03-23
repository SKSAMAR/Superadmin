package com.fintech.webspidysoftware.viewmodel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputEditText;
import com.fintech.webspidysoftware.R;
import com.fintech.webspidysoftware.data.model.PackageType;
import com.fintech.webspidysoftware.data.model.PersonUserModel;
import com.fintech.webspidysoftware.data.model.UserTypeModel;
import com.fintech.webspidysoftware.data.repositories.CreationRepository;
import com.fintech.webspidysoftware.listeners.CreationListener;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreationViewModel extends ViewModel implements CreationListener {

    public CreationRepository repository;

    public UserTypeModel userTypemodel = null;
    public PackageType packageType = null;
    public PersonUserModel personUserModel = null;

    public String first_name =null;
    public String last_name  =null;
    public String mobile = null;
    public String email = null;
    public List<UserTypeModel> userTypeModelList = null;


    @Inject
    public CreationViewModel(CreationRepository repository){
        this.repository = repository;
    }


    public void onUserTypeClick(View view){
        if(userTypeModelList==null){
            repository.bringUserType(this, view);
        }else{
            onUserTypeSpinnerClick(view);
        }
    }

    public void onOwnerClick(View view){

    }

    public void onUserPackageTypeClick(View view){

    }

    public void onAddUserClick(View view){

    }

    @Override
    public void achieveUserType(List<UserTypeModel> userTypeModelList) {
        this.userTypeModelList = userTypeModelList;
    }

    @Override
    public void achievePersonUser(List<PersonUserModel> personUserModelList) {

    }

    @Override
    public void achievePackageType(List<PackageType> packageTypeList) {

    }


    @SuppressLint("SetTextI18n")
    public void onUserTypeSpinnerClick(View view){
        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView head_title_section = dialog.findViewById(R.id.head_title_section);
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        head_title_section.setText("User Types");
        ArrayAdapter<UserTypeModel> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,userTypeModelList);
        myOperatorListView.setAdapter(adapter);
        searchOperator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myOperatorListView.setOnItemClickListener((parent, view1, position, id) -> {
            TextInputEditText editText = view.findViewById(R.id.user_type);
            editText.setText(adapter.getItem(position).name);
            userTypemodel = adapter.getItem(position);
            dialog.dismiss();
        });
    }
}
