package com.example.skip.view.fragment.user;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;
import com.example.skip.R;
import com.example.skip.adapter.CategoriesAdapter;
import com.example.skip.databinding.FragmentHomeBinding;
import com.example.skip.model.Category;
import com.example.skip.viewmodel.CategoryViewModel;
import com.example.skip.viewmodel.HomeViewModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private CategoryViewModel categoryViewModel;
    private int PLACE_PICKER_REQUEST = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;

    private TextView mLocationText, mLatitudeTv, mLongitudeTv;
    private EditText mSupportedAreaEt;
    private String [] countryListIso = {"eg","sau","om","mar","usa","ind"};
    private String [] addressLanguageList = {"en","ar"};

    private Spinner mCountryListSpinner,mLanguageSpinner;
    private LinearLayout mLlResult;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = binding.recycleViewCategories;
        final CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext());
        categoryViewModel.getCategoryListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                categoriesAdapter.setCategoryArrayList(categories);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(categoriesAdapter);
                recyclerView.setHasFixedSize(true);
            }
        });
        selectLocation();

        /*activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == PLACE_PICKER_REQUEST) {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Place place = PlacePicker.getPlace(result.getData(), getContext());
                        StringBuilder stringBuilder = new StringBuilder();
                        String latitude = String.valueOf(place.getLatLng().latitude);
                        String longitude = String.valueOf(place.getLatLng().longitude);
                        stringBuilder.append("LATITUDE :");
                        stringBuilder.append(latitude);
                        stringBuilder.append("\n");
                        stringBuilder.append("LONGITUDE");
                        stringBuilder.append(longitude);
                        binding.textView4.setText(stringBuilder.toString());
                    }
                }
            }
        });*/
    }

    private void selectLocation() {
        binding.constraintLayoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPermissionInManifest(getActivity(),1, Manifest.permission.ACCESS_FINE_LOCATION))
                    selectLocationOnMap();
            }
        });
    }

    private void selectLocationOnMap() {
        String apiKey = getString(R.string.places_api_key);
        String mCountry = "";
        String mLanguage = "";
        String [] mSupportedAreas = {"Gaza"};
        startMapActivity(apiKey,mCountry,mLanguage,mSupportedAreas);
    }

    private void startMapActivity(String apiKey, String country, String language, String[]supportedAreas){
        Intent intent = new Intent(getContext(), MapActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(SimplePlacePicker.API_KEY,apiKey);
        bundle.putString(SimplePlacePicker.COUNTRY,country);
        bundle.putString(SimplePlacePicker.LANGUAGE,language);
        bundle.putStringArray(SimplePlacePicker.SUPPORTED_AREAS,supportedAreas);

        intent.putExtras(bundle);
        startActivityForResult(intent, SimplePlacePicker.SELECT_LOCATION_REQUEST_CODE);
    }

    //check for location permission
    public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    requestCode);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}