package com.example.skip.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.skip.R;
import com.example.skip.databinding.ActivitySplashBinding;
import com.example.skip.utils.PreferenceUtils;
import com.example.skip.view.activity.admin.AdminActivity;
import com.example.skip.view.activity.auth.PhoneAuthActivity;
import com.example.skip.view.activity.copmany.CompanyActivity;
import com.example.skip.view.activity.user.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private Animation topAnim, bottomAnim;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        binding.imageViewSplash.setAnimation(topAnim);
        binding.linearLayout4.setAnimation(bottomAnim);

        //
        Pair[] pairs = new Pair[2];
        pairs[0]=new Pair<View,String>(binding.imageViewSplash,"logo_image");
        pairs[1]=new Pair<View,String>(binding.linearLayout4,"logo_text");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceUtils.isIntro(SplashActivity.this)) {
                    if (PreferenceUtils.getEmail(SplashActivity.this) != null && !PreferenceUtils.getEmail(getApplicationContext()).isEmpty()) {
                        switch (PreferenceUtils.getUserType(SplashActivity.this)) {
                            case "0"://user
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                break;
                            case "1"://company
                                startActivity(new Intent(SplashActivity.this, CompanyActivity.class));
                                break;
                            case "2"://admin
                                startActivity(new Intent(SplashActivity.this, AdminActivity.class));
                                break;
                        }
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, PhoneAuthActivity.class),options.toBundle());
                        finish();
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, PhoneAuthActivity.class),options.toBundle());
                    finish();
                }

            }
        }, 3500);
    }
}