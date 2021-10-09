package com.example.skip.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.skip.R;
import com.example.skip.utils.PreferenceUtils;
import com.example.skip.view.activity.admin.AdminActivity;
import com.example.skip.view.activity.copmany.CompanyActivity;
import com.example.skip.view.activity.user.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(getBaseContext(), ActivityIntro.class));
                    finish();
                }

            }
        }, 1500);
    }
}