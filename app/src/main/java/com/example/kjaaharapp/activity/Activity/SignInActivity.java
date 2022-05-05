package com.example.kjaaharapp.activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends BaseActivity {

    FirebaseAuth mAuth;
    ActivitySignInBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignIn();
            }
        });

        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        if (mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }




    }

    private void userSignIn(){
        showProgressDialog("Please Wait.. ");
        String email = binding.etEmailSignIn.getText().toString().trim();
        String password = binding.etPasswordSignIn.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            binding.etEmailSignIn.setError("Email is required");
        }

        if (TextUtils.isEmpty(password)){
            binding.etPasswordSignIn.setError("Password is required");
        }

        if (binding.etPasswordSignIn.length() < 6){
            binding.etPasswordSignIn.setError("Password must be at Least 6 Length");
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            hideProgressDialog();
                            Toast.makeText(SignInActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                Toast.makeText(SignInActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}