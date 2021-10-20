package com.example.skip.view.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.skip.R;
import com.example.skip.databinding.ActivityCreateAdminBinding;
import com.example.skip.view.fragment.admin.category.CategoryDetailsFragment;

public class CreateCategoryActivity extends AppCompatActivity {

    private ActivityCreateAdminBinding binding;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragment = new CategoryDetailsFragment();
        setFragment(fragment);
    }

    private void setFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
    }
}