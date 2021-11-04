package com.example.skip.view.fragment.admin.category;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skip.R;
import com.example.skip.databinding.FragmentCategoryDetailsBinding;
import com.example.skip.model.Category;
import com.example.skip.model.OtpAuth;
import com.example.skip.view.activity.admin.CreateCategoryActivity;
import com.example.skip.view.fragment.auth.OtpVerificationFragment;
import com.example.skip.viewmodel.CategoryViewModel;
import com.example.skip.viewmodel.OtpViewModel;
import com.example.skip.viewmodel.ShareViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

public class CategoryDetailsFragment extends Fragment {

    private FragmentCategoryDetailsBinding binding;
    private Category myCategory;
    private String title = "";
    private String describtion = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.editTextCategoryTitle.addTextChangedListener(textWatcher);
        binding.editTextCategoryDescription.addTextChangedListener(textWatcher);
        binding.buttonNext.setEnabled(checkInputDetails());
        nextButton();
    }

    private void nextButton() {
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCategoryDetail();
                Fragment fragment = new CategoryImageFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                ((CreateCategoryActivity) getActivity()).setStep("2");
            }
        });
    }

    private void saveCategoryDetail() {
        String createBy = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String dateOfCreate = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
        myCategory = new Category(title, describtion, "", "", "", "1", dateOfCreate, createBy, "", "","1");
        CategoryViewModel.setCategory(myCategory);
        ((CreateCategoryActivity) getActivity()).setCategoryTitle(title, describtion);
        binding.buttonNext.setEnabled(false);
    }

    private boolean checkInputDetails() {
        title = binding.editTextCategoryTitle.getText().toString().trim();
        describtion = binding.editTextCategoryDescription.getText().toString().trim();
        return !title.isEmpty() && !describtion.isEmpty();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            binding.buttonNext.setEnabled(checkInputDetails());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}