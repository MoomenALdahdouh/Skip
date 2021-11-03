package com.example.skip.view.fragment.admin.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.skip.R;
import com.example.skip.adapter.CategoriesAdapter;
import com.example.skip.databinding.ActivityCreateSubcategoryBinding;
import com.example.skip.model.Category;
import com.example.skip.view.activity.DoneCreateActivity;
import com.example.skip.viewmodel.CategoryViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class CreateSubcategoryActivity extends AppCompatActivity {

    private ActivityCreateSubcategoryBinding binding;
    private Category myCategory;
    private String logo = "";
    private String title = "";
    private String description = "";
    private String categoryName = "";
    private String categoryId = "";

    private String imageName;
    private Bitmap compressor;
    private String downloadUri;
    private Uri imageUri = null;
    private static final int MAX_LENGTH = 100;
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private CategoryViewModel categoryViewModel;

    private ArrayList<String> arrayListCategoryName;
    private ArrayList<Category> categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateSubcategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility(View.GONE);
        binding.editTextLogoUrl.addTextChangedListener(textWatcher);
        binding.editTextCategoryTitle.addTextChangedListener(textWatcher);
        binding.editTextCategoryDescription.addTextChangedListener(textWatcher);
        binding.buttonCreate.setEnabled(checkInputDetails());
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        arrayListCategoryName = new ArrayList<>();
        categoriesList = new ArrayList<>();
        createButtonOnClick();
        selectImage();
        getAllCategories();
    }

    private boolean checkInputDetails() {
        logo = binding.editTextLogoUrl.getText().toString().trim();
        title = binding.editTextCategoryTitle.getText().toString().trim();
        description = binding.editTextCategoryDescription.getText().toString().trim();
        if (!logo.isEmpty()) {
            viewLogoInCard(logo);
            binding.include.categoryItemTitle.setText(title);
            binding.include.categoryItemSubTitle.setText(description);
        }
        if (!title.isEmpty() || !description.isEmpty()) {
            binding.include.categoryItemTitle.setText(title);
            binding.include.categoryItemSubTitle.setText(description);
        }
        return !logo.isEmpty() && !title.isEmpty() && !description.isEmpty() && !categoryName.isEmpty();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            binding.buttonCreate.setEnabled(checkInputDetails());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void createButtonOnClick() {
        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSubCategory();
            }
        });
    }

    private void createSubCategory() {
        binding.progressBar.setVisibility(View.VISIBLE);
        String createBy = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String dateOfCreate = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
        myCategory = new Category(title, description, logo, "", categoryName, "1", dateOfCreate, createBy, "", "");
        CategoryViewModel categoryViewModel = new CategoryViewModel();
        categoryViewModel.addCategoryToFirebase(myCategory);
        showSuccessfullyDialog();
    }

    private void showSuccessfullyDialog() {
        startActivity(new Intent(this, DoneCreateActivity.class));
        finish();
    }

    private void selectImage() {
        binding.imageViewSelectLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage();
            }
        });
        binding.imageEditLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage();
            }
        });
    }

    private void cropImage() {
        imageUri = null;
        imageName = "";
        downloadUri = "";
        if (ContextCompat.checkSelfPermission(CreateSubcategoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) CreateSubcategoryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(4, 4)
                    .start(CreateSubcategoryActivity.this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                postLogoOnFireBase(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void postLogoOnFireBase(Uri imageUri) {
        if (imageUri != null) {
            binding.progressBar.setVisibility(View.VISIBLE);
            compressAndNameImage();
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            compressor.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayInputStream);
            byte[] thumpData = byteArrayInputStream.toByteArray();
            StorageReference filePath = storageReference.child("category_background").child(imageName);
            UploadTask uploadTask = filePath.putBytes(thumpData);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUri = task.getResult().toString();
                                viewLogoInCard(downloadUri);
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            });
        } else {
            //Please upload image
        }
    }

    private void viewLogoInCard(String downloadUri) {
        Picasso.get().load(downloadUri).into(binding.include.categoryItemImage);
    }

    //Name image
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void compressAndNameImage() {
        imageName = random() + ".jpg";
        File imageFile = new File(imageUri.getPath());
        try {
            compressor = new Compressor(CreateSubcategoryActivity.this)
                    .setMaxHeight(240)
                    .setMaxWidth(360)
                    .setQuality(5)
                    .compressToBitmap(imageFile);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void getAllCategories() {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryListMutableLiveData().observe(this, new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                for (Category category : categories) {
                    arrayListCategoryName.add(category.getTitle());
                    //categoriesList.add(category);
                }
                spinnerCreate(binding.spinner, arrayListCategoryName);
            }
        });
    }

    private void spinnerCreate(Spinner spinner, ArrayList<String> arrayList) {
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayList);
        adapterSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryName = arrayList.get(0);
            }
        });
    }

}