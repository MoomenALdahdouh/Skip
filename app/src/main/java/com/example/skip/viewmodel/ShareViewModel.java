package com.example.skip.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skip.model.OtpAuth;

public class ShareViewModel extends ViewModel {

    private MutableLiveData<OtpAuth> mOtpAuth = new MutableLiveData<>();

    public void setOtpAuth(OtpAuth otpAuth) {
        mOtpAuth.setValue(otpAuth);
    }

    public LiveData<OtpAuth> getOtpAuth() {
        return mOtpAuth;
    }
}