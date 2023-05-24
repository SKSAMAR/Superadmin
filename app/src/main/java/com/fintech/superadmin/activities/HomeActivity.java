package com.fintech.superadmin.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.activities.customersupport.NewTicketRise;
import com.fintech.superadmin.activities.profile.ProfileActivity;
import com.fintech.superadmin.activities.qrscanner.QrMobilePayActivity;
import com.fintech.superadmin.data.repositories.UserRepository;
import com.fintech.superadmin.databinding.ActivityHomeBinding;
import com.fintech.superadmin.fragments.AnalyticFragment;
import com.fintech.superadmin.fragments.screenmenus.CustomerMenuFragments;
import com.fintech.superadmin.fragments.screenmenus.HomeMenuFragments;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.StartGettingLocation;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.HomeViewModel;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeActivity extends BaseActivity {

    public final int STORAGE_PERMISSION = 786;
    private ActivityHomeBinding homeBinding;
    boolean exit = false;

    @Inject
    UserRepository userRepository;


    //HomeMenuFragments homeMenuFragments;
    HomeViewModel homeViewModel;

    private long mLastClickTime = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeBinding.setHomeViewModel(homeViewModel);
        checkPermission();
        userRepository.getSignedInUser().observe(this, user -> {
            if (user != null) {
                homeBinding.toolbar.userFirstName.setText(user.getName() + "");
                homeBinding.toolbar.partnerId.setText("" + user.getPartner_id());
                HomeViewModel.aepsBalance = "AePS: " + user.getAepsbalance();
                HomeViewModel.mainBalance = "Main: " + user.getMainbalance();

            }
        });

        homeBinding.toolbar.userFirstName.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        homeBinding.toolbar.toolbarLogoImage.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        StartGettingLocation.setAllTheLocations(HomeActivity.this);
        homeBinding.toolbar.customerSupport.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, NewTicketRise.class)));
        homeBinding.fab.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, QrMobilePayActivity.class));
        });
        homeBinding.toolbar.qrCode.setOnClickListener(v -> homeViewModel.displayMobilePay(HomeActivity.this));

        String appType = getString(R.string.app_type);
        if (appType.trim().equalsIgnoreCase("b2c")) {
            CustomerMenuFragments homeMenuFragments = new CustomerMenuFragments();
            setFragment(homeMenuFragments, homeBinding.HomeMenuFragment);
            setNavigationClick(homeMenuFragments);
        } else {
            HomeMenuFragments homeMenuFragments = new HomeMenuFragments();
            setFragment(homeMenuFragments, homeBinding.HomeMenuFragment);
            setNavigationClick(homeMenuFragments);
        }
    }


    public void setFragment(Fragment fragment, View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(view.getId(), fragment);
        fragmentTransaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    private <T extends Fragment> void setNavigationClick(T homeMenuFragments) {
        homeBinding.bottomNavigation.setBackground(null);
//        homeBinding.bottomNavigation.getMenu().getItem(2).setEnabled(false);
        homeBinding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                return false;
            } else {

                int id = item.getItemId();
                switch (id) {
                    //check id
                    case R.id.navHistory:
                        setFragment(new AnalyticFragment(), homeBinding.HomeMenuFragment);
                        break;

                    case R.id.navAccount:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        item.setCheckable(false);
                        break;

                    case R.id.navHome:
                        setFragment(homeMenuFragments, homeBinding.HomeMenuFragment);
                        break;
                    default:
                        ViewUtils.showToast(HomeActivity.this, "Invalid choice");
                        break;
                }

            }
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;
        });

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, STORAGE_PERMISSION);
        }
        //When Permission is Granted

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED
                && grantResults[3] == PackageManager.PERMISSION_GRANTED && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
            StartGettingLocation.setAllTheLocations(HomeActivity.this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (homeViewModel != null) {
            userRepository.refreshUserData(HomeActivity.this);
        }
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            ViewUtils.showToast(HomeActivity.this, "Press Again to exit");
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                exit = false;
            }
        }, 2000);
        exit = true;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean result = intent.getBooleanExtra("status", false);
        if (result) {
            String message = intent.getStringExtra("message");
            DisplayMessageUtil.success(HomeActivity.this, message);
        }
    }
}