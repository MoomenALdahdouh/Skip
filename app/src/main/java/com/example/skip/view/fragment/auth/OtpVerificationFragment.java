package com.example.skip.view.fragment.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.skip.R;
import com.example.skip.databinding.FragmentInputPhoneBinding;
import com.example.skip.databinding.FragmentOtpVerificationBinding;

public class OtpVerificationFragment extends Fragment {
    private FragmentOtpVerificationBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOtpVerificationBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        /*Fragment fragment = new InputPhoneFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment)
                .addToBackStack(null).commit();*/
        //fragmentTransaction.replace(R.id.container_category_fragment, fragment).addToBackStack(null).commit();
        return root;
    }

    private void goOnClick(){
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new OtpVerificationFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, fragment)
                        .addToBackStack(null).commit();
                //fragmentTransaction.replace(R.id.container_category_fragment, fragment).addToBackStack(null).commit();
            }
        });
    }
}