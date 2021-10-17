package com.example.skip.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowMoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShowMoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("ASDSDFDAS");
    }

    public LiveData<String> getText() {
        return mText;
    }
}