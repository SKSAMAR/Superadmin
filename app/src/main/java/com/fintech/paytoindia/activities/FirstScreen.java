package com.fintech.paytoindia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;

import com.fintech.paytoindia.R;
import com.fintech.paytoindia.activities.common.BaseActivity;
import com.fintech.paytoindia.auth.AuthActivity;
import com.fintech.paytoindia.data.db.AppDatabase;
import com.fintech.paytoindia.data.db.entities.User;
import com.fintech.paytoindia.util.DisplayMessageUtil;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class FirstScreen extends BaseActivity {

    //Variables
    Animation topAnimation, bottomAnimation;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();
        topAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_animation);
        logo = findViewById(R.id.LogoSplash);
        logo.setAnimation(topAnimation);
        User user = AppDatabase.getAppDatabase(FirstScreen.this).getUserDao().getRegularUser();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                chooseFate(user);
            }
        }, 3000);
    }

    private void chooseFate(User user){
        Intent intent;
        if (user != null){
            intent = new Intent(FirstScreen.this, HomeActivity.class);
        }
        else{
            intent = new Intent(FirstScreen.this, AuthActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        DisplayMessageUtil.dismissDialog();
        super.onPause();
    }
}