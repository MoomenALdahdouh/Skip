package com.example.skip.view.fragment.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.skip.R;
import com.example.skip.databinding.FragmentInputPhoneBinding;
import com.example.skip.databinding.FragmentOtpVerificationBinding;
import com.example.skip.model.OtpAuth;
import com.example.skip.model.User;
import com.example.skip.utils.PreferenceUtils;
import com.example.skip.view.activity.user.MainActivity;
import com.example.skip.viewmodel.OtpViewModel;
import com.example.skip.viewmodel.ShareViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.DateFormat;
import java.util.Calendar;

public class OtpVerificationFragment extends Fragment {

    private FragmentOtpVerificationBinding binding;
    private String phone;
    private String otp;
    private String num1, num2, num3, num4, num5, num6;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOtpVerificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getShearedPhone();
        goOnClick();
        addListenerToEditText();
        return root;
    }

    private void addListenerToEditText() {
        binding.editTextTextPersonName1.addTextChangedListener(textWatcher);
        binding.editTextTextPersonName2.addTextChangedListener(textWatcher);
        binding.editTextTextPersonName3.addTextChangedListener(textWatcher);
        binding.editTextTextPersonName4.addTextChangedListener(textWatcher);
        binding.editTextTextPersonName5.addTextChangedListener(textWatcher);
        binding.editTextTextPersonName6.addTextChangedListener(textWatcher);
        binding.buttonNext.setEnabled(getAndCheckInputNum());
    }

    private void getShearedPhone() {
        ShareViewModel shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        shareViewModel.getOtpAuth().observe(getViewLifecycleOwner(), new Observer<OtpAuth>() {
            @Override
            public void onChanged(OtpAuth s) {
                phone = s.getPhone();
                binding.textViewPhone.setText(phone);
                otp = s.getOtp();
            }
        });
    }

    private void goOnClick() {
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputOtp = num1 + num2 + num3 + num4 + num5 + num6;
                checkOTP(inputOtp);
            }
        });
    }

    private boolean getAndCheckInputNum() {
        num1 = binding.editTextTextPersonName1.getText().toString().trim();
        num2 = binding.editTextTextPersonName2.getText().toString().trim();
        num3 = binding.editTextTextPersonName3.getText().toString().trim();
        num4 = binding.editTextTextPersonName4.getText().toString().trim();
        num5 = binding.editTextTextPersonName5.getText().toString().trim();
        num6 = binding.editTextTextPersonName6.getText().toString().trim();
        if (!num1.isEmpty() && !num2.isEmpty() && !num3.isEmpty() && !num4.isEmpty() && !num5.isEmpty() && !num6.isEmpty())
            return true;
        else
            return false;
    }

    private boolean register = false;

    private void checkOTP(String inputOtp) {
        register = false;
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(otp, inputOtp);
        OtpViewModel otpViewModel = new ViewModelProvider(OtpVerificationFragment.this).get(OtpViewModel.class);
        otpViewModel.loginMutableLiveData(phoneAuthCredential).observe(OtpVerificationFragment.this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                String date = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
                user = new User("", "", "", "", phone, "", "0", date, "", "1", "");
                if (!register) {
                    otpViewModel.registerMutableLiveData(user).observe(OtpVerificationFragment.this, new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if (!register) {
                                PreferenceUtils.savePhone(phone, getContext());
                                PreferenceUtils.saveUserType("0", getContext());
                                Toast.makeText(getContext(), "Register True", Toast.LENGTH_SHORT).show();
                                register = true;
                                startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
                            }
                        }
                    });
                }
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            binding.buttonNext.setEnabled(getAndCheckInputNum());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}