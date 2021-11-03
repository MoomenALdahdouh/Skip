package com.example.skip.view.activity.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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
import com.example.skip.databinding.ActivityAdminBinding;
import com.example.skip.databinding.ActivityMainBinding;
import com.example.skip.utils.PreferenceUtils;
import com.example.skip.view.activity.auth.PhoneAuthActivity;
import com.example.skip.view.activity.user.MainActivity;
import com.example.skip.view.fragment.admin.AdsFragment;
import com.example.skip.view.fragment.admin.CategoriesFragment;
import com.example.skip.view.fragment.admin.EmployiesFragment;
import com.example.skip.view.fragment.admin.HomeAdminFragment;
import com.example.skip.view.fragment.admin.ServiceFragment;
import com.example.skip.view.fragment.admin.UsersFragment;
import com.example.skip.view.fragment.admin.category.CreateSubcategoryActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAdminBinding binding;
    private FragmentPageAdapter fragmentPageAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String[] titles = new String[]{"Home", "Categories", "Services", "Employees", "Users", "Ads"};
    private Fragment[] fragments = new Fragment[]{new HomeAdminFragment(), new CategoriesFragment(), new ServiceFragment(), new EmployiesFragment(), new UsersFragment(), new AdsFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(findViewById(R.id.toolbar));
        viewPager = findViewById(R.id.view_pager_id);
        tabLayout = findViewById(R.id.tabLayout);
        fragmentPageAdapter = new FragmentPageAdapter(AdminActivity.this, titles, fragments);
        viewPager.setAdapter(fragmentPageAdapter);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_category:
                intent = new Intent(AdminActivity.this, CreateCategoryActivity.class);
                break;
            case R.id.action_sub_category:
                intent = new Intent(AdminActivity.this, CreateSubcategoryActivity.class);
                break;
            case R.id.action_service:
                intent = new Intent(AdminActivity.this, CreateServiceActivity.class);
                break;
            case R.id.action_provider:
                intent = new Intent(AdminActivity.this, CreateProviderActivity.class);
                break;
            case R.id.action_admin:
                intent = new Intent(AdminActivity.this, CreateAdminActivity.class);
                break;
            case R.id.action_ads:
                intent = new Intent(AdminActivity.this, CreateAdsActivity.class);
                break;
            case R.id.action_sign_out:
                signOut();
                intent = new Intent(AdminActivity.this, PhoneAuthActivity.class);
                finish();
                break;
        }
        if (intent != null)
            startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        PreferenceUtils.saveEmail("", AdminActivity.this);
        PreferenceUtils.savePassword("", AdminActivity.this);
        PreferenceUtils.saveUserType("", AdminActivity.this);
        PreferenceUtils.savePhone("", AdminActivity.this);
    }

    public void createCategorySuccessfully() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AdminActivity.this);
        dialogBuilder.setTitle("Create Category")
                .setMessage("Successfully Create Category")
                .setIcon(R.drawable.ic_baseline_done_24)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}