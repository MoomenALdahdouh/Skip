package com.example.skip.view.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.skip.R;
import com.example.skip.databinding.ActivityCreateAdminBinding;
import com.example.skip.view.fragment.admin.category.CategoryDetailsFragment;
import com.squareup.picasso.Picasso;

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

    public void setFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    public void setCategoryTitle(String title, String subTitle) {
        binding.categoryItemTitle.setText(title);
        binding.categoryItemSubTitle.setText(subTitle);
    }

    public void setCategoryImage(String image) {
        Picasso.get().load(image).into(binding.categoryItemImage);
    }

    public void setCategoryBackground(String background) {
        Picasso.get().load(background).into(binding.categoryItemBackground);
    }
}