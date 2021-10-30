package com.example.skip.view.fragment.user;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;
import com.example.skip.R;
import com.example.skip.adapter.CategoriesAdapter;
import com.example.skip.databinding.FragmentHomeBinding;
import com.example.skip.model.Category;
import com.example.skip.view.activity.admin.AdminActivity;
import com.example.skip.view.activity.auth.SignInActivity;
import com.example.skip.viewmodel.CategoryViewModel;
import com.example.skip.viewmodel.HomeViewModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private CategoryViewModel categoryViewModel;
    private int PLACE_PICKER_REQUEST = 1;
    ActivityResultLauncher<Intent> activityResultLauncher;

    private TextView mLocationText, mLatitudeTv, mLongitudeTv;
    private GoogleMap map;
    private MapView mapView;
    private EditText mSupportedAreaEt;

    private String[] countryListIso = {"eg", "sau", "om", "mar", "usa", "ind"};
    private String[] addressLanguageList = {"en", "ar"};

    private Spinner mCountryListSpinner, mLanguageSpinner;
    private LinearLayout mLlResult;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillCategoriesRecycle();
        selectLocation();
    }

    private void registerAsProvider() {
        binding.buttonRegisterAsProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });
    }

    private void fillCategoriesRecycle() {
        final RecyclerView recyclerView = binding.recycleViewCategories;
        final CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext());
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 1) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });

        categoryViewModel.getCategoryListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                categoriesAdapter.setCategoryArrayList(categories);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(categoriesAdapter);
                recyclerView.setHasFixedSize(true);
            }
        });
    }

    private void selectLocation() {
        binding.constraintLayoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPermissionInManifest(getActivity(), 1, Manifest.permission.ACCESS_FINE_LOCATION))
                    selectLocationOnMap();
            }
        });
    }

    private void selectLocationOnMap() {
        String apiKey = getString(R.string.places_api_key);
        String mCountry = "";
        String mLanguage = "";
        String[] mSupportedAreas = {};
        startMapActivity(apiKey, mCountry, mLanguage, mSupportedAreas);
    }

    private void startMapActivity(String apiKey, String country, String language, String[] supportedAreas) {
        Intent intent = new Intent(getContext(), MapActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(SimplePlacePicker.API_KEY, apiKey);
        bundle.putString(SimplePlacePicker.COUNTRY, country);
        bundle.putString(SimplePlacePicker.LANGUAGE, language);
        bundle.putStringArray(SimplePlacePicker.SUPPORTED_AREAS, supportedAreas);

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

    public void createCategorySuccessfully(Intent data) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirm_location_map_layout, null);
        dialogBuilder.setView(dialogView);
        mLocationText = dialogView.findViewById(R.id.tv_location_text);
        mLatitudeTv = dialogView.findViewById(R.id.tv_latitude);
        mLongitudeTv = dialogView.findViewById(R.id.tv_longitude);
        updateUiData(data);
        Button buttonOK = dialogView.findViewById(R.id.buttonOk);
        AlertDialog alertDialog = dialogBuilder.create();
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private String locationText;
    private double latitudeTv;
    private double longitudeTv;

    private void updateUiData(Intent data) {
        locationText = data.getStringExtra(SimplePlacePicker.SELECTED_ADDRESS);
        latitudeTv = data.getDoubleExtra(SimplePlacePicker.LOCATION_LAT_EXTRA, -1);
        longitudeTv = data.getDoubleExtra(SimplePlacePicker.LOCATION_LNG_EXTRA, -1);
        mLocationText.setText(locationText);
        mLatitudeTv.setText(String.valueOf(latitudeTv));
        mLongitudeTv.setText(String.valueOf(longitudeTv));
        //mLlResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SimplePlacePicker.SELECT_LOCATION_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            if (data != null)
                createCategorySuccessfully(data);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*map = googleMap;
        LatLng home = new LatLng(latitudeTv, longitudeTv);
        map.addMarker(new MarkerOptions().position(home).title("Here lives " + locationText));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 16));*/
    }
}