package com.example.skip.view.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.skip.R;
import com.example.skip.utils.PreferenceUtils;
import com.example.skip.view.activity.SignInActivity;
import com.example.skip.view.activity.admin.AdminActivity;
import com.example.skip.view.activity.copmany.CompanyActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        firebaseFirestore = FirebaseFirestore.getInstance();
        if (isLogin()) {
            //TODO: MAKE SOMETHING
        } else
            showSnackBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean isLogin() {
        return PreferenceUtils.getEmail(getApplicationContext()) != null && !PreferenceUtils.getEmail(getApplicationContext()).isEmpty();
    }

    private void checkUserTypeToSignIn() {
        if (PreferenceUtils.getEmail(getApplicationContext()) != null && !PreferenceUtils.getEmail(getApplicationContext()).isEmpty()) {
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            firebaseFirestore.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String userType = documentSnapshot.getString("userType");
                    if (userType != null || !userType.isEmpty()) {
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
        }).setActionTextColor(getResources().getColor(R.color.teal_500)).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserTypeToSignIn();
    }
}