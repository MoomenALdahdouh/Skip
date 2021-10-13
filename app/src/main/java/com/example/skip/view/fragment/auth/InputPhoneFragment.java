package com.example.skip.view.fragment.auth;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.skip.R;
import com.example.skip.databinding.FragmentInputPhoneBinding;
import com.example.skip.model.OtpAuth;
import com.example.skip.viewmodel.ShareViewModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class InputPhoneFragment extends Fragment {

    private String phone;
    private ShareViewModel shareViewModel;
    private FragmentInputPhoneBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInputPhoneBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        nextButtonOnClick();
        binding.editTextPhone.addTextChangedListener(textWatcher);
        binding.buttonNext.setEnabled(checkInputPhone());
        return root;
    }

    private void nextButtonOnClick() {
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOTP(phone);
            }
        });
    }

    private void sendOTP(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                (Activity) getContext(),
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(getContext(), "onVerificationCompleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getContext(), "onVerificationFailed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Toast.makeText(getContext(), "onCodeSent", Toast.LENGTH_SHORT).show();
                        OtpAuth otpAuth = new OtpAuth(phone, s);
                        shareViewModel.setOtpAuth(otpAuth);
                        Fragment fragment = new OtpVerificationFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
                    }
                }
        );
    }

    private boolean checkInputPhone() {
        phone = binding.editTextPhone.getText().toString().trim();
        if (phone.length() == 13)
            return true;
        else
            return false;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            binding.buttonNext.setEnabled(checkInputPhone());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}