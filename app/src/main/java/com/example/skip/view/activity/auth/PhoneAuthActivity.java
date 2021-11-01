package com.example.skip.view.activity.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.skip.R;
import com.example.skip.databinding.ActivityPhoneAuthBinding;
import com.example.skip.view.activity.user.MainActivity;
import com.example.skip.view.fragment.auth.InputPhoneFragment;
import com.example.skip.view.fragment.auth.OtpVerificationFragment;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity {

    private String phone = "+972599124279";
    private Fragment fragment;
    private ActivityPhoneAuthBinding binding;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragment = new InputPhoneFragment();
        setFragment(fragment);
        signInByEmail();
        //nextButtonOnClick();
        //sendOTP();
    }

    private void signInByEmail() {
        binding.buttonSignInByEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhoneAuthActivity.this, SignInActivity.class));
                finish();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();
    }

    private void nextButtonOnClick() {
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PhoneAuthActivity.this, "Click", Toast.LENGTH_SHORT).show();
                fragment = new OtpVerificationFragment();
                setFragment(fragment);
                binding.buttonNext.setText("GO");
                binding.textViewJoin.setText("Enter ");
            }
        });
    }

    private void sendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                PhoneAuthActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(getApplicationContext(), "onVerificationCompleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(), "onVerificationFailed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Toast.makeText(getApplicationContext(), "onCodeSent", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PhoneAuthActivity.this, OtpVerificationActivity.class);
                        intent.putExtra("PHONE", phone);
                        intent.putExtra("OTP", s);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    public void skipOnClick(View view) {
        startActivity(new Intent(PhoneAuthActivity.this, MainActivity.class));
        finish();
    }
}