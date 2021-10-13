package com.example.skip.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skip.model.OtpAuth;
import com.example.skip.model.User;
import com.example.skip.repo.Repository;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;

public class OtpViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<FirebaseUser> mutableLiveData;

    public OtpViewModel(){
        repository = new Repository();
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<FirebaseUser> loginMutableLiveData(PhoneAuthCredential credential) {
        mutableLiveData = repository.login(credential);
        return mutableLiveData;
    }

    public MutableLiveData<FirebaseUser> registerMutableLiveData(User user) {
        mutableLiveData = repository.register(user);
        return mutableLiveData;
    }

}