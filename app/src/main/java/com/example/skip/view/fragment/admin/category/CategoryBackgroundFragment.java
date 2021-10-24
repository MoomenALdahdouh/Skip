package com.example.skip.view.fragment.admin.category;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.skip.R;
import com.example.skip.databinding.FragmentCategoryBackgroundBinding;
import com.example.skip.databinding.FragmentCategoryImageBinding;
import com.example.skip.model.Category;
import com.example.skip.view.activity.admin.CreateCategoryActivity;
import com.example.skip.viewmodel.CategoryViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class CategoryBackgroundFragment extends Fragment {

    private FragmentCategoryBackgroundBinding binding;
    private Category myCategory;
    private String imageName;
    private Bitmap compressor;
    private String downloadUri;
    private Uri imageUri = null;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private static final int MAX_LENGTH = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBackgroundBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectImage();
        nextButton();
    }

    private void selectImage() {
        binding.imageViewSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage();
            }
        });
    }

    private void nextButton() {
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Show Dialog with green true image successfully create

            }
        });
    }

    private void saveCategoryBackground(String background) {
        //String background = "https://i.ibb.co/smbnYHG/2021-10-21-10-54-43.png";
        ((CreateCategoryActivity) getActivity()).setCategoryBackground(background);
        myCategory = CategoryViewModel.getCategory();
        myCategory.setBackground(background);
        CategoryViewModel.setCategory(myCategory);
        CategoryViewModel categoryViewModel = new CategoryViewModel();
        categoryViewModel.addCategoryToFirebase(myCategory);
        /*CategoryViewModel categoryViewModel = new ViewModelProvider(CategoryBackgroundFragment.this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                myCategory = category;
                myCategory.setImage(background);
                categoryViewModel.setCategory(myCategory);
            }
        });*/
    }

    private void cropImage() {
        imageUri = null;
        imageName = "";
        downloadUri = "";
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {
            //TODO: Must add activity on Manifest file add this line code
            //            AndroidManifest.xml
            //            <activity
            //            android:name="com.theartofdev.edmodo.cropper.CropImageActivity"
            //            android:theme="@style/Base.Theme.AppCompat" />
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    //.setMinCropResultSize(512,512)
                    .setAspectRatio(4, 2)
                    .start(getContext(), CategoryBackgroundFragment.this);
        }
    }

    //Crop image

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                imageUri = result.getUri();
                //imageViewItem.setImageURI(imageUri);
                postBackgroundOnFireBase();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void postBackgroundOnFireBase() {
        if (imageUri != null) {
            //progressBarBottomSheet.setVisibility(View.VISIBLE);
            compressAndNameImage();
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            compressor.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayInputStream);
            byte[] thumpData = byteArrayInputStream.toByteArray();
            StorageReference filePath = storageReference.child("category_background/").child(imageName);
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
                                saveCategoryBackground(downloadUri);
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
            compressor = new Compressor(getContext())
                    .setMaxHeight(240)
                    .setMaxWidth(360)
                    .setQuality(5)
                    .compressToBitmap(imageFile);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}