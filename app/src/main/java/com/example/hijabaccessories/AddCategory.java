package com.example.hijabaccessories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class AddCategory extends AppCompatActivity {


    EditText addCategoryInDatabase, addSubCategoryInDatabase;
    Button addCategoryInDatabaseBtn, addSubCategoryInDatabaseBtn;
    int maxId = 0;
    int maxSubID;

    //String subCategoryInsertDataBase;

    //CategorySpinnerDeclarationDeclarationWithDataBase
    Spinner categoryFromDataBaseRegistration;
    ArrayList<String> itemCategoryFromDataBaseRegistration;
    ArrayAdapter<String> adapterCategoryFromDataBaseRegistration;
    DatabaseReference databaseReferenceCategoryFromDataBaseRegistration;
    ValueEventListener listenerCategoryFromDataBaseRegistration;


    DatabaseReference databaseReference, databaseReferenceSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);




        addCategoryInDatabase = (EditText) findViewById(R.id.addCategory);
        addCategoryInDatabaseBtn = (Button) findViewById(R.id.addCategoryBtn);

        addSubCategoryInDatabase = (EditText) findViewById(R.id.addSubCategory);
        addSubCategoryInDatabaseBtn = (Button) findViewById(R.id.addSubCategoryBtn);


        //SpinnerStart
        databaseReferenceCategoryFromDataBaseRegistration = FirebaseDatabase.getInstance().getReference("Category");
        //SpinnerInitializationCity


        categoryFromDataBaseRegistration = (Spinner) findViewById(R.id.productCategoryShowForAddSubCategory);
        itemCategoryFromDataBaseRegistration = new ArrayList<>();
        adapterCategoryFromDataBaseRegistration = new ArrayAdapter<String >(AddCategory.this,
                R.layout.spinner_layout , R.id.spinnerTextView,itemCategoryFromDataBaseRegistration);
        categoryFromDataBaseRegistration.setAdapter(adapterCategoryFromDataBaseRegistration);
        retrieveDataCategoryFromDataBaseRegistration();


        databaseReference =  FirebaseDatabase.getInstance().getReference().child("Category");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    maxId = (int) dataSnapshot.getChildrenCount();


                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });













        addCategoryInDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCategory();
            }
        });


        addSubCategoryInDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertSubCategory();
            }
        });
    }

    private void insertSubCategory() {

        String subCategoryInsertDataBase = addSubCategoryInDatabase.getText().toString();
        String subCategoryInsertParentCategory = categoryFromDataBaseRegistration.getSelectedItem().toString();





        databaseReferenceSubCategory =  FirebaseDatabase.getInstance().getReference().child("Sub Category")
                .child(subCategoryInsertDataBase);
        databaseReferenceSubCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    maxSubID = (int) dataSnapshot.getChildrenCount();


                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });

        if (subCategoryInsertParentCategory.equals("Select Category")){
            categoryFromDataBaseRegistration.requestFocus();
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(subCategoryInsertDataBase)) {
            addSubCategoryInDatabase.requestFocus();
            addSubCategoryInDatabase.setError("Enter Sub Category");
        }


        else {




            HashMap insertSubCat = new HashMap();
            insertSubCat.put("SubValue",subCategoryInsertDataBase);


            FirebaseDatabase.getInstance().getReference().
                    child("Sub Category")
                    .child(subCategoryInsertParentCategory)
                    .push()
                    .setValue(insertSubCat)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(AddCategory.this, "Inserted New Sub Category", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(AddCategory.this, AdminDashboard.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AddCategory.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void insertCategory() {
        String categoryInsertInDatabase = addCategoryInDatabase.getText().toString();
        if (TextUtils.isEmpty(categoryInsertInDatabase)){
            addCategoryInDatabase.requestFocus();
            addCategoryInDatabase.setError("Enter Category");
        }
        else {

           // maxId = maxId + 1;


            HashMap insertCat = new HashMap();
            insertCat.put("value",categoryInsertInDatabase);


            FirebaseDatabase.getInstance().getReference().
                    child("Category")
                    .child(String.valueOf(maxId + 1))
                    .setValue(insertCat)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                            if (task.isSuccessful()) {



                                HashMap insertSubCat = new HashMap();
                                insertSubCat.put("SubValue","Select Sub Category");

                                FirebaseDatabase.getInstance().getReference().
                                        child("Sub Category")
                                        .child(categoryInsertInDatabase)
                                        .child("1")
                                        .setValue(insertSubCat);


                                Intent intent = new Intent(AddCategory.this, AdminDashboard.class);
                                startActivity(intent);

                                Toast.makeText(AddCategory.this, "Inserted New Category", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddCategory.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }

    }



    private void retrieveDataCategoryFromDataBaseRegistration() {

        listenerCategoryFromDataBaseRegistration = databaseReferenceCategoryFromDataBaseRegistration
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot item:dataSnapshot.getChildren()){
                            itemCategoryFromDataBaseRegistration.add(item.child("value").getValue().toString());

                        }
                        adapterCategoryFromDataBaseRegistration.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(AddCategory.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}