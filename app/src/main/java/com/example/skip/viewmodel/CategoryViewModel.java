package com.example.skip.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skip.model.Category;
import com.example.skip.model.OtpAuth;
import com.example.skip.repo.CategoryRepository;

import java.util.ArrayList;

public class CategoryViewModel extends ViewModel {

    private CategoryRepository categoryRepository;
    private MutableLiveData<Category> categoryMutableLiveData;
    private MutableLiveData<ArrayList<Category>> categoryListMutableLiveData;
    private static Category category = new Category();


    public CategoryViewModel() {
        categoryMutableLiveData = new MutableLiveData<>();
        categoryRepository = new CategoryRepository();
        categoryListMutableLiveData = categoryRepository.getCategoriesFromFirebase();
    }

    public static Category getCategory() {
        return category;
    }

    public static void setCategory(Category category) {
        CategoryViewModel.category = category;
    }


    public void addCategoryToFirebase(Category category) {
        categoryRepository.addCategory(category);
    }

    public MutableLiveData<ArrayList<Category>> getCategoryListMutableLiveData() {
        return categoryListMutableLiveData;
    }
}