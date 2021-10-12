package com.example.skip.view.fragment.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.skip.R;
import com.example.skip.databinding.FragmentInputPhoneBinding;

public class InputPhoneFragment extends Fragment {
    private FragmentInputPhoneBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInputPhoneBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        /*Fragment fragment = new InputPhoneFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment)
                .addToBackStack(null).commit();*/
        //fragmentTransaction.replace(R.id.container_category_fragment, fragment).addToBackStack(null).commit();
        nextButtonOnClick();
        return root;
    }

    private void nextButtonOnClick(){
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Click",Toast.LENGTH_SHORT).show();
                Fragment fragment = new OtpVerificationFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                /*fragmentTransaction.add(R.id.fragmentContainer, fragment)
                        .addToBackStack(null).commit();*/
                fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
            }
        });
    }
}