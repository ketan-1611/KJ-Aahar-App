package com.example.kjaaharapp.activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.databinding.ActivityHistoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HistoryActivity extends AppCompatActivity {

    ActivityHistoryBinding binding;
    FirebaseFirestore firestore;
    private CollectionReference collections;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        collections = firestore.collection("User data");

        loadData();
    }

    public void loadData(){
        collections.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            String data="";

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if (documentSnapshot.contains("Name") && documentSnapshot.contains("Description") && documentSnapshot.contains("Type") && documentSnapshot.contains("Userid")){
                                    String name =   (String) documentSnapshot.get("Name");
                                    String type = (String) documentSnapshot.get("Type");
                                    String description = (String) documentSnapshot.get("Description");
                                    String userId = (String) documentSnapshot.get("Userid");
                                    String userID = auth.getCurrentUser().getUid();
                                    Timestamp ts =  (Timestamp) documentSnapshot.get("Timestamp");
                                    String dateAndTime = String.valueOf(ts);

                                    if (userId.equals(userID)){
                                        data += "Name: " + name + "\nUser Type: " + type + "\nDescription: "+description + "\nDate & Time: "+dateAndTime+"\n";
                                    }

                                    binding.tvDataHistory.setText(data);
                                }
                            }
                        }
                        else{
                            Log.d("Tag","Error Fetching data: ",task.getException());
                        }
                    }
                });
    }
}