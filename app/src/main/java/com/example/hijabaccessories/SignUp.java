package com.example.hijabaccessories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    EditText regEmail, regPassword, regName, regPhone;
    Button regBtn;

    ImageView regArrowBackSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        regArrowBackSignUp = (ImageView) findViewById(R.id.arrowBackSignUp);
        regArrowBackSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        regEmail = (EditText) findViewById(R.id.adminEmail);
        regPassword = (EditText) findViewById(R.id.adminPassword);
        regName = (EditText) findViewById(R.id.adminName);
        regPhone = (EditText) findViewById(R.id.adminPhoneNumber);

        regBtn = (Button) findViewById(R.id.adminRegistrationBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAdminData();
            }
        });



    }

    private void insertAdminData() {

        String regAdminEmail = regEmail.getText().toString();
        String regAdminPassword = regPassword.getText().toString();
        String regAdminName = regName.getText().toString();
        String regAdminPhone = regPhone.getText().toString();

        if (TextUtils.isEmpty(regAdminEmail)){
            regEmail.requestFocus();
            regEmail.setError("Enter Email");
        }
        else if (TextUtils.isEmpty(regAdminPassword)){
            regPassword.requestFocus();
            regPassword.setError("Enter Password");
        }
        else if (TextUtils.isEmpty(regAdminName)){
            regName.requestFocus();
            regName.setError("Enter Name");
        }
        else if (TextUtils.isEmpty(regAdminPhone)){
            regPhone.requestFocus();
            regPhone.setError("Enter Phone");
        }
        else if (!regAdminPhone.matches("\\+[0-9]{10,13}$")){
            regPhone.requestFocus();
            regPhone.setError("+923326882570");
        }
        else {

            progressDialog.setTitle("Adding Product");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(regAdminEmail, regAdminPassword)
                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                final HashMap insertAdminData = new HashMap();

                                insertAdminData.put("Email", regAdminEmail);
                                insertAdminData.put("Password", regAdminPassword);
                                insertAdminData.put("Name", regAdminName);
                                insertAdminData.put("Phone", regAdminPhone);
                                insertAdminData.put("Role","Admin");

                                FirebaseDatabase.getInstance().getReference()
                                        .child("Admin")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(insertAdminData)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull  Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(SignUp.this, "Inserted", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(SignUp.this, SignIn.class);
                                                    startActivity(intent);
                                                    progressDialog.dismiss();
                                                }
                                                else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SignUp.this, "FD"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "FA"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }

    }
}