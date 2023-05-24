package com.fintech.superadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.activities.rebranded.RebrandedHome;
import com.fintech.superadmin.auth.AuthActivity;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.data.repositories.UserRepository;
import com.fintech.superadmin.databinding.ActivitySplashScreenBinding;
import com.fintech.superadmin.ui.theme.MyColors;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.NetworkUtil;
import com.fintech.superadmin.util.ThemeColors;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FirstScreen extends BaseActivity {

    //Variables
    Animation topAnimation, bottomAnimation;
    ActivitySplashScreenBinding binding;

    @Inject
    UserRepository userRepository;
    @Inject
    APIServices apiServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();
        topAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_animation);
        binding.LogoSplash.setAnimation(topAnimation);
        getColorModel();
    }

    private void chooseFate(User user) {
        Intent intent;
        if (user != null) {
            intent = new Intent(FirstScreen.this, HomeActivity.class);
        } else {
            intent = new Intent(FirstScreen.this, AuthActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        DisplayMessageUtil.dismissDialog();
        super.onPause();
    }

    private void getColorModel() {
        User user = userRepository.getSignedInRegularUser();
        NetworkUtil.getNetworkResult(apiServices.getAppColor("appColor"), null, res -> {
            MyColors.INSTANCE.setColorsAgain(res.RED, res.GREEN, res.BLUE);
            ThemeColors.setThemeColor(FirstScreen.this, res.RED, res.GREEN, res.BLUE);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    chooseFate(user);
                }
            }, 1500);
        });
    }
}