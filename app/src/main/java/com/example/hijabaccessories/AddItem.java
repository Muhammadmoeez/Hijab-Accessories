package com.example.hijabaccessories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddItem extends AppCompatActivity {

    // declaration all the variables.......

    ImageView regArrowBackAddItem;
    DatabaseReference addProductDatabaseReference;




    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceForAdminData;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;


    private ImageView firstProductImage;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Uri selectImageUri;

    private ProgressDialog progressDialog;
    StorageReference storageReference;
    private StorageTask uploadTask;





    EditText regProductCode, regProductName, regProductPrice, regProductDescription;
    RadioGroup radioGroup;
    int radioID;
    RadioButton radioButton;

    //CategorySpinnerDeclarationDeclarationWithDataBase
    Spinner categoryFromDataBaseRegistration;
    ArrayList<String> itemCategoryFromDataBaseRegistration;
    ArrayAdapter<String> adapterCategoryFromDataBaseRegistration;
    DatabaseReference databaseReferenceCategoryFromDataBaseRegistration;
    ValueEventListener listenerCategoryFromDataBaseRegistration;


    //SubCategoryOFCitySpinnerDeclarationWithDataBase

    Spinner subCategoryFromDataBaseRegistration;
    ArrayList<String> itemSubCategoryFromDataBaseRegistration;
    ArrayAdapter<String> adapterSubCategoryOFCityFromDataBaseRegistration;
    DatabaseReference databaseReferenceSubCategoryFromDataBaseRegistration;
    ValueEventListener listenerSubCategoryFromDataBaseRegistration;


//    String[] itemStockAvailability;
//    ArrayAdapter<String> adapterStockAvailability;
//    Spinner myStockAvailability;

    Button  regProductSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();



//        myStockAvailability = (Spinner) findViewById(R.id.productStockOROutOFStock);
//        itemStockAvailability = getResources().getStringArray(R.array.stockAvailability);
//        adapterStockAvailability = new ArrayAdapter<>(AddItem.this,R.layout.spinner_layout, R.id.spinnerTextView, itemStockAvailability);
//        myStockAvailability.setAdapter(adapterStockAvailability);

        regArrowBackAddItem = (ImageView) findViewById(R.id.arrowBackAddItem);
        regArrowBackAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        firstProductImage = (ImageView) findViewById(R.id.productImage);
        firstProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });


        //EditText
        regProductCode = (EditText) findViewById(R.id.productCode);
        regProductName = (EditText) findViewById(R.id.productName);
        regProductPrice = (EditText) findViewById(R.id.productPrice);
        regProductDescription = (EditText) findViewById(R.id.productDescription);


        //Radio Group
        radioGroup = findViewById(R.id.radioGroupForStock);
//        radioID = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(radioID);





        //SpinnerStart
        databaseReferenceCategoryFromDataBaseRegistration = FirebaseDatabase.getInstance().getReference("Category");
        databaseReferenceSubCategoryFromDataBaseRegistration = FirebaseDatabase.getInstance().getReference("Sub Category");





        //SpinnerInitializationCity


        categoryFromDataBaseRegistration = (Spinner) findViewById(R.id.productCategory);
        itemCategoryFromDataBaseRegistration = new ArrayList<>();
        adapterCategoryFromDataBaseRegistration = new ArrayAdapter<String >(AddItem.this,
                R.layout.spinner_layout , R.id.spinnerTextView,itemCategoryFromDataBaseRegistration);
        categoryFromDataBaseRegistration.setAdapter(adapterCategoryFromDataBaseRegistration);
        retrieveDataCategoryFromDataBaseRegistration();


        //SpinnerInitializationTourismPointOFCity

        categoryFromDataBaseRegistration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                subCategoryFromDataBaseRegistration = (Spinner) findViewById(R.id.productSubCategory);
                itemSubCategoryFromDataBaseRegistration = new ArrayList<>();
                adapterSubCategoryOFCityFromDataBaseRegistration = new ArrayAdapter<String >(AddItem.this,
                        R.layout.spinner_layout , R.id.spinnerTextView,itemSubCategoryFromDataBaseRegistration);
                subCategoryFromDataBaseRegistration.setAdapter(adapterSubCategoryOFCityFromDataBaseRegistration);
                retrieveDataSubCategoryFromDataBaseRegistration();

            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Button
        regProductSave = (Button) findViewById(R.id.addProductBtn);

        regProductSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                radioID = radioGroup.getCheckedRadioButtonId();
//                radioButton = findViewById(radioID);
//                Toast.makeText(AddItem.this, ""+radioButton.getText(), Toast.LENGTH_SHORT).show();

                addMyProduct();

            }
        });




    }

    private void addMyProduct() {



        radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

        final String productCode = regProductCode.getText().toString();
        final String productName = regProductName.getText().toString();
        final String productPrice = regProductPrice.getText().toString();
        final String productCategory = categoryFromDataBaseRegistration.getSelectedItem().toString();
        final String productSubCategory = subCategoryFromDataBaseRegistration.getSelectedItem().toString();
        //final String productStockAvailability = myStockAvailability.getSelectedItem().toString();
        final String productStockAvailability = radioButton.getText().toString();
        final String productDescription = regProductDescription.getText().toString();


        if (TextUtils.isEmpty(productCode)){
            regProductCode.requestFocus();
            regProductCode.setError("Set Product Code");
        }
        else if (TextUtils.isEmpty(productName)){
            regProductName.requestFocus();
            regProductName.setError("Set Product Name");

        }

        else if (TextUtils.isEmpty(productPrice)){
            regProductPrice.requestFocus();
            regProductPrice.setError("Set Product Price");

        }
        else if (productCategory.equals("Select Category")){

            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();

        }
        else if (productSubCategory.equals("Select Sub Category")){
            Toast.makeText(this, "Select Sub Category", Toast.LENGTH_SHORT).show();
        }
//        else if (productStockAvailability.equals("Stock Availability")){
//            Toast.makeText(this, "Stock Availability", Toast.LENGTH_SHORT).show();
//
//        }
        else if (TextUtils.isEmpty(productDescription)){
            regProductDescription.requestFocus();
            regProductDescription.setError("Write Description");

        }
        else {

            progressDialog.setTitle("Adding Product");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            storageReference = FirebaseStorage.getInstance().getReference().child("Product");
            final StorageReference myRef = storageReference.child(System.currentTimeMillis() + "." + getExtension(selectImageUri));
            uploadTask = myRef.putFile(selectImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String productFirstImage = String.valueOf(uri);



                                    // create Custom Id's for save child
                                    addProductDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Product");
                                    String id = addProductDatabaseReference.push().getKey();








                                    databaseReferenceForAdminData = firebaseDatabase.getReference("Admin")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    databaseReferenceForAdminData.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            //    String adminEmail = snapshot.child("Email").getValue(String.class);
                                            String adminNumber = snapshot.child("Phone").getValue(String.class);
                                            String adminUserID = snapshot.child("").getValue().toString();


                                            HashMap insertProductData = new HashMap();
                                            insertProductData.put("ProductID",id);
                                            insertProductData.put("AdminNumber",adminNumber);
                                            insertProductData.put("AdminId",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            insertProductData.put("ProductCode",productCode);
                                            insertProductData.put("ProductImage", productFirstImage);
                                            insertProductData.put("ProductName",productName);
                                            insertProductData.put("ProductPrice",productPrice);
                                            insertProductData.put("ProductDescription",productDescription);
                                            insertProductData.put("ProductCategory",productCategory);
                                            insertProductData.put("ProductSubCategory",productSubCategory);
                                            insertProductData.put("ProductStock",productStockAvailability);

                                            FirebaseDatabase.getInstance().getReference().child("Product")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child(id).setValue(insertProductData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){


//                                                        Toast.makeText(AddItem.this, "Data Uploded", Toast.LENGTH_SHORT).show();
//                                                        Intent intent = new Intent(AddItem.this, AdminDashboard.class);
//                                                        startActivity(intent);

                                                                FirebaseDatabase.getInstance().getReference().child("AllProduct")
                                                                        .child(id)
                                                                        .setValue(insertProductData)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                if (task.isSuccessful()){
                                                                                    Toast.makeText(AddItem.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                                                                    Intent intent = new Intent(AddItem.this, AdminDashboard.class);
                                                                                    startActivity(intent);
                                                                                }
                                                                                else {
                                                                                    Toast.makeText(AddItem.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                                    progressDialog.dismiss();
                                                                                }
                                                                            }
                                                                        });
                                                            }
                                                            else {

                                                                Toast.makeText(AddItem.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                progressDialog.dismiss();
                                                            }

                                                        }
                                                    });


                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });











                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

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

                Toast.makeText(AddItem.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void retrieveDataSubCategoryFromDataBaseRegistration() {


        final String selectedCityRegistration = categoryFromDataBaseRegistration.getSelectedItem().toString();

        listenerSubCategoryFromDataBaseRegistration = databaseReferenceSubCategoryFromDataBaseRegistration
                .child(selectedCityRegistration).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot item:dataSnapshot.getChildren()){
                            itemSubCategoryFromDataBaseRegistration.add(item.child("SubValue").getValue().toString());

                        }
                        adapterSubCategoryOFCityFromDataBaseRegistration.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AddItem.this, ""+databaseError, Toast.LENGTH_SHORT).show();

                    }
                });
    }


    // select Image Gallery and Camera code start
    private void SelectImage(){
//        final CharSequence[] items={"Camera", "Gallery", "Cancel"};
        final CharSequence[] items={ "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddItem.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

//                if (items[i].equals("Camera")){
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                    {
//                        if (checkSelfPermission(Manifest.permission.CAMERA) ==
//                                PackageManager.PERMISSION_DENIED ||
//                                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
//                                        PackageManager.PERMISSION_DENIED)
//                        {
//                            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                            requestPermissions(permission, PERMISSION_CODE);
//                        }
//
//                        else
//                        {
//                            openCamera();
//                        }
//                    }
//                    else
//                    {
//
//                        openCamera();
//                    }
//
//                }
//                else

                    if (items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);

                }
                else if (items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });

        builder.show();
    }
//    private void openCamera() {
//
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "New Picture");
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
//        selectImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT , selectImageUri);
//        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                   // openCamera();
                }
                else {
                    Toast.makeText(this, "Permission Denied....", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if (resultCode == Activity.RESULT_OK){
            firstProductImage.setImageURI(selectImageUri);

            if (requestCode == REQUEST_CAMERA){


            }
            else if (requestCode == SELECT_FILE){
                selectImageUri = data.getData();
                firstProductImage.setImageURI(selectImageUri);

            }
        }
    }
    // select Image Gallery and Camera code End


    private  String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

}






