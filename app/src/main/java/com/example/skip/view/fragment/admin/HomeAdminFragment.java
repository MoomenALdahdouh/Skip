package com.example.skip.view.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skip.R;
import com.example.skip.databinding.FragmentHelpBinding;
import com.example.skip.databinding.FragmentHomeAdminBinding;
import com.example.skip.databinding.FragmentHomeBinding;

public class HomeAdminFragment extends Fragment {

    private FragmentHomeAdminBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeAdminBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

}