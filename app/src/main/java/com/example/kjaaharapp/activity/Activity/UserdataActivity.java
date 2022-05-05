package com.example.kjaaharapp.activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.service.autofill.UserData;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.activity.Adapter.UserDataAdapter;
import com.example.kjaaharapp.activity.Models.UserHistory;
import com.example.kjaaharapp.databinding.ActivityUserdataBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserdataActivity extends AppCompatActivity {


    ActivityUserdataBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ArrayList<UserHistory>dataList;
    UserDataAdapter adapter;
    CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserdataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("User data");

        dataList = new ArrayList<>();
        adapter = new UserDataAdapter(this,dataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvHistory.setLayoutManager(layoutManager);
        binding.rvHistory.setAdapter(adapter);

       // String fid = FirebaseAuth.getInstance().getCurrentUser().getUid();

//        firestore.collection("User data")
//                .orderBy("Timestamp",Query.Direction.DESCENDING)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                        for (DocumentSnapshot d : list){
//                            UserHistory userHistory = d.toObject(UserHistory.class);
//                            String UserId = (String) d.get("Userid");
//                            if (UserId.equals(fid)){
//                                dataList.add(userHistory);
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                });


       loadData();
    }

    public void loadData(){
        collectionReference
                .orderBy("Timestamp",Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){

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
                                        dataList.add(new UserHistory(name,type,description));
                                    }

                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}



