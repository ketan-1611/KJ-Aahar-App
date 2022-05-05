package com.example.kjaaharapp.activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.activity.Adapter.DashboardAdapter;
import com.example.kjaaharapp.activity.Models.DashboardModel;
import com.example.kjaaharapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<DashboardModel> dashboardModels;
    DashboardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        dashboardModels = new ArrayList<>();
        dashboardModels.add(new DashboardModel("Donate",R.drawable.donate));
        dashboardModels.add(new DashboardModel("Receive",R.drawable.rcv));
        dashboardModels.add(new DashboardModel("Food Map",R.drawable.map));
        dashboardModels.add(new DashboardModel("My Pins",R.drawable.mypins));
        dashboardModels.add(new DashboardModel("History",R.drawable.history));
        dashboardModels.add(new DashboardModel("About Us",R.drawable.about));
        dashboardModels.add(new DashboardModel("Contact Us",R.drawable.contact));
        dashboardModels.add(new DashboardModel("Log Out",R.drawable.lgout));

        adapter = new DashboardAdapter(this,dashboardModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.rvMain.setLayoutManager(gridLayoutManager);
        binding.rvMain.setAdapter(adapter);




    }
}