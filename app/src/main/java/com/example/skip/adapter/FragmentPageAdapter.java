package com.example.skip.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.skip.view.fragment.admin.AdsFragment;
import com.example.skip.view.fragment.admin.CategoriesFragment;
import com.example.skip.view.fragment.admin.EmployiesFragment;
import com.example.skip.view.fragment.admin.HomeFragment;
import com.example.skip.view.fragment.admin.ServiceFragment;
import com.example.skip.view.fragment.admin.UsersFragment;

public class FragmentPageAdapter extends FragmentStateAdapter {


    private String[] titles;

    public FragmentPageAdapter(@NonNull FragmentActivity fragmentActivity, String [] titles) {
        super(fragmentActivity);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ServiceFragment();
                break;
            case 2:
                fragment = new CategoriesFragment();
                break;
            case 3:
                fragment = new EmployiesFragment();
                break;
            case 4:
                fragment = new UsersFragment();
                break;
            case 5:
                fragment = new AdsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
