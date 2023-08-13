package com.fintech.superadmin.activities.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;

import com.fintech.superadmin.activities.common.BaseActivity;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.creation.CreationActivity;
import com.fintech.superadmin.activities.pins.PinActivity;
import com.fintech.superadmin.adapters.ProfileListAdapter;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.model.ProfileListModel;
import com.fintech.superadmin.data.network.responses.SystemResponse;
import com.fintech.superadmin.data.sys.TPinResponse;
import com.fintech.superadmin.databinding.ActivityProfileBinding;
import com.fintech.superadmin.databinding.ContactusLayoutBinding;
import com.fintech.superadmin.deer_listener.TaskListener;
import com.fintech.superadmin.encoder.Base64Encoder;
import com.fintech.superadmin.listeners.ProfileListListener;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.ProfileViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ProfileActivity extends BaseActivity implements ProfileListListener {

    ActivityProfileBinding binding;
    ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setProfileViewModel(viewModel);
        binding.toolbar.titleText.setText(getResources().getString(R.string.profile_title));
        binding.toolbar.back.setOnClickListener(view -> onBackPressed());
        setLiveData();
        setElementsInList();
        binding.onChangePicture.setOnClickListener(
                view -> ImagePicker.with(ProfileActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent(intentention -> {
                            someActivityResultLauncher.launch(intentention);
                            return null;
                        })
        );
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

    private void setElementsInList() {
        List<ProfileListModel> list = new ArrayList<>();
//        list.add(new ProfileListModel(R.drawable.ic_profile_socialmedia,"Social Media"));
        list.add(new ProfileListModel(R.drawable.ic_account, "My Profile"));
//        list.add(new ProfileListModel(R.drawable.cf_ic_qr, "Show QR"));
        list.add(new ProfileListModel(R.drawable.ic_profile_bank, "KYC REGISTRATION"));
        list.add(new ProfileListModel(R.drawable.ic_profile_settings, "M-PIN"));
        list.add(new ProfileListModel(R.drawable.ic_profile_settings, "Change Password"));
//        list.add(new ProfileListModel(R.drawable.ic_account,"AePS Package"));
//        list.add(new ProfileListModel(R.drawable.ic_account,"My Commission"));
        list.add(new ProfileListModel(R.drawable.ic_account, "Customer Care"));
        list.add(new ProfileListModel(R.drawable.ic_profile_logout, "Logout"));
        binding.listRecycler.setAdapter(new ProfileListAdapter(list, this));
    }

    @SuppressLint("SetTextI18n")
    private void setLiveData() {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(ProfileActivity.this);
        appDatabase.getUserDao().getUser().observe(this, user -> {
            if (user != null) {
                binding.setNameNumber(user);
                binding.fullName.setText(user.getName() + " " + user.getLastname());
                Glide.with(ProfileActivity.this).load("" + user.image).into(binding.profileImage);
            } else {
                binding.profileImage.setImageResource(R.drawable.ic_profile_user);
            }
        });
    }

    @Override
    public void onListItemSelected(View view, ProfileListModel model) {
        Intent intent;
        switch (model.getTitle()) {
            case "Social Media":
                intent = new Intent(ProfileActivity.this, SocialMedia.class);
                startActivity(intent);
                break;
            case "My Profile": {
                startActivity(new Intent(view.getContext(), ProfileDetails.class));
            }
            break;
            case "KYC REGISTRATION":
                intent = new Intent(ProfileActivity.this, BankDetails.class);
                startActivity(intent);
                break;
            case "M-PIN":
                m_pin_execution();
                break;
            case "My Commission":
                intent = new Intent(ProfileActivity.this, MyCommissionActivity.class);
                startActivity(intent);
                break;
            case "Change Password":
                intent = new Intent(ProfileActivity.this, Settings.class);
                startActivity(intent);
                break;
            case "Add User":
                intent = new Intent(ProfileActivity.this, CreationActivity.class);
                startActivity(intent);
                break;
            case "Customer Care":
                myModal();
                break;
            case "Show QR":
                viewModel.profileRepository.getQrCode(ProfileActivity.this);
                break;
            case "Micro-ATM Package":
                viewModel.profileRepository.bringPackageData(ProfileActivity.this, "m-atm");
                break;
            case "AePS Package":
                viewModel.profileRepository.bringPackageData(ProfileActivity.this, "aeps");
                break;
            case "Logout":
                viewModel.profileRepository.deleteUser(ProfileActivity.this);
                break;
            default:
                ViewUtils.showToast(ProfileActivity.this, "Coming Soon");
        }
    }


    private void myModal() {

        viewModel.profileRepository.getHelpSupport(ProfileActivity.this, data -> {

            ContactusLayoutBinding binding = ContactusLayoutBinding.inflate(LayoutInflater.from(ProfileActivity.this));
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
            alertDialog.setView(binding.getRoot());
            alertDialog.setPositiveButton("Exit", (dialog, which) -> dialog.dismiss());
            binding.dialMobile.setText(data.mobile);
            binding.firstEmail.setText(data.email);
            alertDialog.show();
            String dialMobile = binding.dialMobile.getText().toString();
            String sendFEmail = binding.firstEmail.getText().toString();

            binding.dialMobile.setOnClickListener(v -> {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + dialMobile));
                startActivity(callIntent);
            });


            binding.firstEmail.setOnClickListener(v -> {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", sendFEmail, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            });
        });

    }


    private void m_pin_execution() {
        viewModel.checkM_pinStatus(new TaskListener<SystemResponse<TPinResponse>>() {
            @Override
            public void onLoading() {
                DisplayMessageUtil.loading(ProfileActivity.this);
            }

            @Override
            public void onResponse(SystemResponse<TPinResponse> data) {
                DisplayMessageUtil.dismissDialog();
                Intent intent = new Intent(ProfileActivity.this, PinActivity.class);
                if (data.getStatus() && data.getResponse_code().equals(1)) {
                    //T-Pin Already Exists
                    intent.putExtra("action_given", "CHANGE");
                    startActivity(intent);
                } else if (data.getResponse_code().equals(2)) {
                    //create new T-Pin
                    intent.putExtra("action_given", "CREATE");
                    t_pin_task_launcher.launch(intent);
                }
            }

            @Override
            public void onError(String message) {
                DisplayMessageUtil.error(ProfileActivity.this, message);
            }
        });
    }

    ActivityResultLauncher<Intent> t_pin_task_launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    Bundle bundle = Objects.requireNonNull(data).getExtras();
                    boolean status = bundle.getBoolean("action_status", false);
                    String val = bundle.getString("action_result");
                    if (status) {
                        DisplayMessageUtil.success(ProfileActivity.this, val);
                    } else {
                        DisplayMessageUtil.error(ProfileActivity.this, val);
                    }
                }
            });


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = Objects.requireNonNull(result.getData()).getData();
                    binding.profileImage.setImageURI(uri);
                    final InputStream imageStream;
                    String encodedImage;
                    try {
                        imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        encodedImage = Base64Encoder.encodeImage(selectedImage);
                        //Update Profile picture
                        viewModel.profileRepository.updateProfilePicture(ProfileActivity.this, encodedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


}