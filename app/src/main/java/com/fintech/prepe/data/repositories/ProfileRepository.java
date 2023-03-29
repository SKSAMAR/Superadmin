package com.fintech.prepe.data.repositories;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;

import com.fintech.prepe.R;
import com.fintech.prepe.activities.profile.ViewSlabData;
import com.fintech.prepe.constructor.Construct;
import com.fintech.prepe.data.db.AppDatabase;
import com.fintech.prepe.data.db.entities.User;
import com.fintech.prepe.data.db.entities.UserProfile;
import com.fintech.prepe.data.model.HelpSupport;
import com.fintech.prepe.data.network.APIServices;
import com.fintech.prepe.data.network.responses.ConfirmationResponse;
import com.fintech.prepe.data.network.responses.PackageResponseData;
import com.fintech.prepe.data.network.responses.RegularResponse;
import com.fintech.prepe.data.network.responses.SlabInfoResponse;
import com.fintech.prepe.databinding.PackageSelectorDesignBinding;
import com.fintech.prepe.deer_listener.Receiver;
import com.fintech.prepe.util.Accessable;
import com.fintech.prepe.util.MyAlertUtils;
import com.fintech.prepe.util.NetworkUtil;
import com.fintech.prepe.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileRepository {

    public APIServices  apiServices;
    AppDatabase appDatabase;

    String selectedName = "";
    String selectedId = "";

    @Inject
    public ProfileRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }



    public void deleteUser(Context context){
        Construct.logoutExecute(context);
    }


    public void getHelpSupport(Context context, Receiver<HelpSupport> receiver){
        if(Accessable.isAccessable()){
            NetworkUtil.getNetworkResult(apiServices.getHelpSupport("help_support"), context, receiver::getData);
        }
    }



    public void updateProfileDetails(Context context, UserProfile userProfile, User user){
        MyAlertUtils.showProgressAlertDialog(context);
        
        apiServices.updateMyInformation(user.getName(),user.getLastname(),userProfile.getALTERNATE_PHONE_NO(),userProfile.getDOB(),userProfile.getGENDER(), userProfile.getCOUNTRY(), userProfile.getSTATE(), user.getPin(),user.getAddress(),user.getToken(),user.getMobile(),"update_profile_details")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConfirmationResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ConfirmationResponse confirmationResponse) {
                        if(confirmationResponse.isstatus()){
                            MyAlertUtils.showAlertDialog(context, "Success",confirmationResponse.getMessage(), R.drawable.success);
                            userLogin(user.getMobile(), user.getPassword());
                        }
                        else{
                            MyAlertUtils.showAlertDialog(context, "Failed",confirmationResponse.getMessage(), R.drawable.failed);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context,"Failed due to: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateSocialMediaDetails(Context context, UserProfile userProfile, User user){
        MyAlertUtils.showProgressAlertDialog(context);
        
        apiServices.updateMySocialMedia(user.getMobile(), "update_social_media", userProfile.FACEBOOK_URL, userProfile.TWITTER_URL, userProfile.LINKEDIN_URL, userProfile.INSTAGRAM_URL, userProfile.DRIBBLE_BOX_URL, userProfile.DROPBOX_URL, userProfile.GOOGLE_PLUS_URL,userProfile.PINTEREST_URL, userProfile.SKYPE_URL, userProfile.VINE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConfirmationResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ConfirmationResponse confirmationResponse) {
                        if(confirmationResponse.isstatus()){
                            MyAlertUtils.showAlertDialog(context, "Success",confirmationResponse.getMessage(), R.drawable.success);
                        }
                        else{
                            MyAlertUtils.showAlertDialog(context, "Failed",confirmationResponse.getMessage(), R.drawable.failed);
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context,"Failed due to: "+e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateMyPassword(Context context, User user, String new_password, String old_pass){
        MyAlertUtils.showProgressAlertDialog(context);
        
        apiServices.changeMyPassword( "update_my_password", new_password, old_pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConfirmationResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ConfirmationResponse confirmationResponse) {
                        if(confirmationResponse.isstatus()){
                            MyAlertUtils.showAlertDialog(context, "Success",confirmationResponse.getMessage(), R.drawable.success);
                        }
                        else{
                            MyAlertUtils.showAlertDialog(context, "Failed",confirmationResponse.getMessage(), R.drawable.failed);
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context,"Failed due to: "+e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void bringPackageData(Context context, String type){
        User user = appDatabase.getUserDao().getRegularUser();
        
        apiServices.getAllPackageList(user.getId(), user.getUserstatus(), user.getToken(), type, "fetch")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PackageResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        MyAlertUtils.showProgressAlertDialog(context);
                    }

                    @Override
                    public void onNext(@NonNull PackageResponseData responseData) {
                        MyAlertUtils.dismissAlertDialog();
                        if(responseData.getResponse_code()==999){
                            ViewUtils.showToast(context, "Session Expired");
                        }
                        else{
                            bringPackagesList(context, type, responseData.getSelected() ,responseData.getData());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    @SuppressLint("SetTextI18n")
    public void bringPackagesList(Context context, String type, String selected,List<PackageResponseData.PackageData> list){

        if(list==null || list.isEmpty()){
            MyAlertUtils.showAlertDialog(context, "Warning", "No Package Found", R.drawable.warning);
            return;
        }
        selectedName = "";
        selectedId = "";

        PackageSelectorDesignBinding binding = PackageSelectorDesignBinding.inflate(LayoutInflater.from(context));
        Dialog dialogMain = new Dialog(context, R.style.BottomSheetDialogTheme);
        dialogMain.setContentView(binding.getRoot());
        dialogMain.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogMain.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogMain.setCanceledOnTouchOutside(false);

        ArrayAdapter<PackageResponseData.PackageData> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
        binding.MyPackageListView.setAdapter(adapter);
        binding.headTitleSection.setText(type.toUpperCase()+" Packages");
        binding.selectedPackage.setText(selected);
        dialogMain.show();

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

        binding.MyPackageListView.setOnItemClickListener((parent, view, position, id) -> {

            AlertDialog.Builder myDialog=new AlertDialog.Builder(context);
            myDialog.setTitle("Select an option");
            myDialog.setPositiveButton("Select Package",
                    (dialog, which) -> {
                        selectedId = adapter.getItem(position).getId();
                        selectedName = adapter.getItem(position).getName();
                        dialog.dismiss();
                        dialogMain.dismiss();
                        setPackageOnWeb(context, selectedId, type);

                    });
            myDialog.setNeutralButton("View Slab", (dialog, which) -> {
                dialog.dismiss();
                MyAlertUtils.showProgressAlertDialog(context);
                selectedId = adapter.getItem(position).getId();
                
                apiServices.getThisPackageSlabData(selectedId, "fetch_slab")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<SlabInfoResponse>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ArrayList<SlabInfoResponse> slabInfoResponses) {
                                MyAlertUtils.dismissAlertDialog();
                                selectedName = adapter.getItem(position).getName();
                                Intent intent = new Intent(context, ViewSlabData.class);
                                intent.putExtra("package_name",selectedName);
                                intent.putExtra("slabInfoResponse", slabInfoResponses);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                MyAlertUtils.showServerAlertDialog(context, e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            });
            myDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog=myDialog.create();
            alertDialog.show();
        });

    }


    public void setPackageOnWeb(Context context, String selectedId, String selected_type){
        User user = appDatabase.getUserDao().getRegularUser();
        
        apiServices.setThisPackage(user.getId(), user.getUserstatus(), user.getToken(), selectedId, selected_type, "set_package")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        MyAlertUtils.showProgressAlertDialog(context);
                    }

                    @Override
                    public void onNext(@NonNull RegularResponse regularResponse) {
                        if(regularResponse.getResponse_code().equals(999)){
                            ViewUtils.showToast(context, "Session Expired");
                        }
                        else if(regularResponse.isStatus() && regularResponse.getResponse_code().equals(1)){
                            MyAlertUtils.showAlertDialog(context, "Success", regularResponse.getMessage(), R.drawable.success);
                        }
                        else{
                            MyAlertUtils.showServerAlertDialog(context, regularResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    public void userLogin(String mobile, String password) {


    }


    public void updateProfilePicture(Context context, String image_encoded){
        MyAlertUtils.showProgressAlertDialog(context);
                 apiServices.updateProfilePicture(image_encoded)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RegularResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull RegularResponse response) {
                            MyAlertUtils.dismissAlertDialog();
                            if(response.getResponse_code().equals(1)){
                                MyAlertUtils.showAlertDialog(context, "Result", response.getMessage(), R.drawable.success);
                            }
                            else{
                                MyAlertUtils.showAlertDialog(context, "Result", response.getMessage(), R.drawable.warning);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            MyAlertUtils.showServerAlertDialog(context, "Failed due to\n"+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

    }


}
