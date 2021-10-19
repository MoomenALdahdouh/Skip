package com.example.skip.view.activity.copmany;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.example.skip.R;
import com.example.skip.adapter.FragmentPageAdapter;
import com.example.skip.databinding.ActivityCompanyBinding;
import com.example.skip.databinding.ActivityMainBinding;
import com.example.skip.view.activity.admin.AdminActivity;
import com.example.skip.view.fragment.admin.AdsFragment;
import com.example.skip.view.fragment.admin.CategoriesFragment;
import com.example.skip.view.fragment.admin.EmployiesFragment;
import com.example.skip.view.fragment.admin.HomeAdminFragment;
import com.example.skip.view.fragment.admin.ServiceFragment;
import com.example.skip.view.fragment.admin.UsersFragment;
import com.example.skip.view.fragment.comapny.ActivityFragment;
import com.example.skip.view.fragment.comapny.HomeCompanyFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CompanyActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityCompanyBinding binding;
    private FragmentPageAdapter fragmentPageAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String[] titles = new String[]{"Home", "Services", "Activity"};
    private Fragment[] fragments = new Fragment[]{new HomeCompanyFragment(), new ServiceFragment(), new ActivityFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewPager = findViewById(R.id.view_pager_id);
        tabLayout = findViewById(R.id.tabLayout);
        fragmentPageAdapter = new FragmentPageAdapter(CompanyActivity.this, titles, fragments);
        viewPager.setAdapter(fragmentPageAdapter);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();
       /* setSupportActionBar(binding.appBarCompany.toolbar);
        binding.appBarCompany.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_company);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.company, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_company);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}