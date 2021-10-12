package com.example.skip.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.skip.R;
import com.example.skip.adapter.SplashPagerAdapter;
import com.example.skip.model.SplashImage;
import com.example.skip.utils.PreferenceUtils;
import com.example.skip.view.activity.user.MainActivity;

import java.util.ArrayList;

public class ActivityIntro extends AppCompatActivity {
    private SplashPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private ArrayList<SplashImage> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        SplashImage item1 = new SplashImage("Create", "Upload original clips to our Network, and you can be a star and earn cash & gifts!", R.drawable.intro1_2);
        SplashImage item2 = new SplashImage("Enjoy", "Watch clips, Like, Comment, Chat, and Follow other users", R.drawable.intro2_3);
        SplashImage item3 = new SplashImage("Help", "For help on how to use our App, visit VidLap.net/help", R.drawable.intro3_3);

        arrayList.add(item1);
        arrayList.add(item2);
        arrayList.add(item3);


        viewPager = findViewById(R.id.view_pager_id);
        viewPagerAdapter = new SplashPagerAdapter(this, arrayList);
        viewPager.setAdapter(viewPagerAdapter);

    }

    public void skipOnClick(View view) {
        PreferenceUtils.saveIntro(true,ActivityIntro.this);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}