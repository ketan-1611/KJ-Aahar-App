package com.example.kjaaharapp.activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.activity.Models.User;
import com.example.kjaaharapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


    }

    private void userSignUp(){
        showProgressDialog("Please wait.. ");
        String name = binding.etNameSignUp.getText().toString().trim();
        String email = binding.etEmailSignUp.getText().toString().trim();
        String phone = binding.etPhoneSignUp.getText().toString().trim();
        String password = binding.etPasswordSignUp.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            binding.etEmailSignUp.setError("Email is Required");
        }

        if (TextUtils.isEmpty(password)){
            binding.etPasswordSignUp.setError("Password is Required");
        }

        if (binding.etPasswordSignUp.length() < 6){
            binding.etPasswordSignUp.setError("Password must be at Least 6 Length");
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            hideProgressDialog();
                            String userId = mAuth.getCurrentUser().getUid();


                            User user = new User(name,email,phone);


                            firestore.collection("users")
                                    .document(userId)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Not Registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}