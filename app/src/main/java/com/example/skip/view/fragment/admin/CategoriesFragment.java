package com.example.skip.view.fragment.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skip.R;
import com.example.skip.adapter.CategoriesAdapter;
import com.example.skip.databinding.FragmentCategoriesBinding;
import com.example.skip.model.Category;
import com.example.skip.viewmodel.CategoryViewModel;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    private CategoryViewModel categoryViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillCategoriesRecycle();
    }

    private void fillCategoriesRecycle() {
        final RecyclerView recyclerView = binding.recycleViewCategories;
        final CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext());
        categoryViewModel.getCategoryListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                categoriesAdapter.setCategoryArrayList(categories);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                recyclerView.setAdapter(categoriesAdapter);
                recyclerView.setHasFixedSize(true);
            }
        });
    }
}
