package com.example.skip.view.fragment.admin.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skip.R;
import com.example.skip.databinding.FragmentCategoryDetailsBinding;
import com.example.skip.databinding.FragmentCategoryImageBinding;
import com.example.skip.model.Category;
import com.example.skip.view.activity.admin.CreateCategoryActivity;
import com.example.skip.viewmodel.CategoryViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

public class CategoryImageFragment extends Fragment {

    private FragmentCategoryImageBinding binding;
    private Category myCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryImageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nextButton();
    }

    private void nextButton() {
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCategoryImage();
                Fragment fragment = new CategoryBackgroundFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }

    private void saveCategoryImage() {
        String image = "https://i.ibb.co/dmvn6Dx/maintenance.png";
        ((CreateCategoryActivity) getActivity()).setCategoryImage(image);
        myCategory = CategoryViewModel.getCategory();
        myCategory.setImage(image);
        CategoryViewModel.setCategory(myCategory);
        /*CategoryViewModel categoryViewModel = new ViewModelProvider(CategoryImageFragment.this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                myCategory = category;
                myCategory.setImage(image);
                categoryViewModel.setCategory(myCategory);
            }
        });*/
    }
}