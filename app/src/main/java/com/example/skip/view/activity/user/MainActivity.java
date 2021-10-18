package com.example.skip.view.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.skip.R;
import com.example.skip.utils.PreferenceUtils;
import com.example.skip.view.activity.auth.SignInActivity;
import com.example.skip.view.activity.admin.AdminActivity;
import com.example.skip.view.activity.copmany.CompanyActivity;
import com.example.skip.view.fragment.user.HomeFragment;
import com.example.skip.view.fragment.user.SlideshowFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skip.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_wallet, R.id.nav_account_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        MenuItem showMoreItem = bottomNavigationView.getMenu().findItem(R.id.nav_show_more);
        showMoreItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (!drawer.isDrawerOpen(GravityCompat.START))
                    drawer.openDrawer(Gravity.START);
                else drawer.closeDrawer(Gravity.END);
                return true;
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        if (isLogin()) {
            //TODO: MAKE SOMETHING
        } else
            showSnackBar();

        //bottomNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint({"WrongConstant", "NonConstantResourceId"})
    private void bottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.nav_slideshow:
                        fragment = new SlideshowFragment();
                        break;

                    case R.id.nav_gallery:
                        if (!drawer.isDrawerOpen(GravityCompat.START))
                            drawer.openDrawer(Gravity.START);
                        else drawer.closeDrawer(Gravity.END);
                        break;
                }
                setFragment(fragment);
                return true;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean isLogin() {
        return PreferenceUtils.getUserType(getApplicationContext()) != null && !PreferenceUtils.getUserType(getApplicationContext()).isEmpty();
    }

    private void checkUserTypeToSignIn() {
        if (PreferenceUtils.getUserType(getApplicationContext()) != null && !PreferenceUtils.getUserType(getApplicationContext()).isEmpty()) {
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            firebaseFirestore.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String userType = documentSnapshot.getString("userType");
                    if (userType != null) {
                        switch (userType) {
                            case "0"://User
                                //startActivity(new Intent(MainActivity.this, MainActivity.class));
                                break;
                            case "1"://company
                                startActivity(new Intent(MainActivity.this, CompanyActivity.class));
                                finish();
                                break;
                            case "2"://admin
                                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                                finish();
                                break;
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showSnackBar() {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, "You must sign in!", Snackbar.LENGTH_LONG);
        snackbar.setAction("Sign in", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                snackbar.dismiss();
            }
        }).setActionTextColor(getResources().getColor(R.color.purple_500)).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserTypeToSignIn();
    }


}