package com.example.skip.view.activity.admin;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.example.skip.R;
import com.example.skip.adapter.FragmentPageAdapter;
import com.example.skip.databinding.ActivityAdminBinding;
import com.example.skip.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AdminActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAdminBinding binding;
    private FragmentPageAdapter fragmentPageAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String[] titles = new String[]{"Home", "Services", "Categories", "Employees", "Users", "Ads"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewPager = findViewById(R.id.view_pager_id);
        tabLayout = findViewById(R.id.tabLayout);
        fragmentPageAdapter = new FragmentPageAdapter(AdminActivity.this, titles);
        viewPager.setAdapter(fragmentPageAdapter);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();

    }
}