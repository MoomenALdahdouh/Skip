package com.example.skip.repo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.skip.model.Category;
import com.example.skip.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    MutableLiveData<Category> categoryMutableLiveData;
    private MutableLiveData<ArrayList<Category>> categoryListMutableLiveData;
    private MutableLiveData<ArrayList<Category>> subcategoryListMutableLiveData;

    public CategoryRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        categoryMutableLiveData = new MutableLiveData<>();
        categoryListMutableLiveData = new MutableLiveData<>();
        subcategoryListMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Category> addCategory(Category category) {
        firebaseFirestore.collection("Categories").add(category).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                categoryMutableLiveData.setValue(category);
            }
        });
        return categoryMutableLiveData;
    }

    public MutableLiveData<ArrayList<Category>> getCategoriesFromFirebase() {
        /*firebaseFirestore.collection("Categories").whereEqualTo("type", "1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    ArrayList<Category> categoryArrayList = new ArrayList<>();
                    categoryArrayList.addAll(task.getResult().toObjects(Category.class));
                    categoryListMutableLiveData.setValue(categoryArrayList);
                }
            }
        });*/
        firebaseFirestore.collection("Categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Category> categoryArrayList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    Category category = doc.toObject(Category.class);
                    if (category.getType().equals("1"))
                        categoryArrayList.add(category);
                }
                categoryListMutableLiveData.setValue(categoryArrayList);
            }
        });
        return categoryListMutableLiveData;
    }

    public MutableLiveData<ArrayList<Category>> getSubCategoriesFromFirebase() {
        firebaseFirestore.collection("Categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Category> categoryArrayList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    Category category = doc.toObject(Category.class);
                    if (category.getType().equals("0"))
                        categoryArrayList.add(category);
                }
                subcategoryListMutableLiveData.setValue(categoryArrayList);
            }
        });
        return subcategoryListMutableLiveData;
    }

}
