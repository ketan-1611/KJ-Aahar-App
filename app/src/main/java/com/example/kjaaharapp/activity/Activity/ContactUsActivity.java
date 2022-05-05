package com.example.kjaaharapp.activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.databinding.ActivityContactUsactivityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ContactUsActivity extends BaseActivity {

    ActivityContactUsactivityBinding binding;
    Boolean isNameValid,isEmailValid, isMessageValid;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        binding.btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValidate();
            }
        });


    }

    public void setValidate(){
        showProgressDialog("Please wait data is updating ");
        if (binding.etNameContact.getText().toString().isEmpty()){
            binding.nameContact.setError("Please enter name");
            isNameValid = false;
        }else {
            binding.nameContact.setErrorEnabled(false);
            isNameValid = true;
        }

        if (binding.etEmailContact.getText().toString().isEmpty()) {
            binding.emailContact.setError("Please enter email address");
            isEmailValid = false;
        }
         else  {
            isEmailValid = true;
            binding.emailContact.setErrorEnabled(false);
        }

        if (binding.etMessageContact.getText().toString().isEmpty()){
            binding.messageContact.setError("Please enter some message");
            isMessageValid = false;
        }
        else{
            binding.messageContact.setErrorEnabled(false);
            isMessageValid = true;
        }

        if (isNameValid && isEmailValid && isMessageValid){
            String name = binding.etNameContact.getText().toString().trim();
            String email = binding.etEmailContact.getText().toString().trim();
            String message = binding.etMessageContact.getText().toString().trim();

            String userId = auth.getCurrentUser().getUid();

            Map<String,Object>data = new HashMap<>();
            data.put("timestamp", FieldValue.serverTimestamp());
            data.put("name",name);
            data.put("email",email);
            data.put("message",message);
            data.put("userId",userId);

            CollectionReference collectionReference = firestore.collection("Contact data");

            collectionReference.add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            hideProgressDialog();
                            Toast.makeText(ContactUsActivity.this, "Your data successfully updated", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    hideProgressDialog();
                    Toast.makeText(ContactUsActivity.this, "Error! data not updated", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}