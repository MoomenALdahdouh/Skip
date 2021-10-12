package com.example.skip.view.activity.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skip.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpVerificationActivity extends AppCompatActivity {
    String phone;
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        EditText otpEdit = findViewById(R.id.editTextTextPersonName);
        Button button = findViewById(R.id.button);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PHONE")) {
            phone = intent.getStringExtra("PHONE");
            otp = intent.getStringExtra("OTP");

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpInput = otpEdit.getText().toString().trim();
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(otp, otpInput);
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            Toast.makeText(getApplicationContext(), "You are register", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Faild", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}