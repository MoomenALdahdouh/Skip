package com.example.skip.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skip.model.Category;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Category>> listMutableLiveData;


    public HomeViewModel() {
        listMutableLiveData = new MutableLiveData<>();
        /*ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        Category category = new Category("Clean Cars", "30 USD", "","");
        categoryArrayList.add(category);
        categoryArrayList.add(category);
        categoryArrayList.add(category);
        categoryArrayList.add(category);
        categoryArrayList.add(category);
        categoryArrayList.add(category);
        categoryArrayList.add(category);
        listMutableLiveData.setValue(categoryArrayList);*/
    }

    public LiveData<ArrayList<Category>> getCategories() {
        return listMutableLiveData;
    }
}