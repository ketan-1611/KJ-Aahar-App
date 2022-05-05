package com.example.kjaaharapp.activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.activity.Models.UserHistory;

import com.example.kjaaharapp.databinding.SampleDataBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.DataViewHolder>{

    private Context context;
    private ArrayList<UserHistory> userHistories;



    public UserDataAdapter(Context context, ArrayList<UserHistory> userHistories) {
        this.context = context;
        this.userHistories = userHistories;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_data,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        UserHistory userHistory = userHistories.get(position);

        holder.binding.tvNameAns.setText(userHistory.getName());
        holder.binding.tvTypeAns.setText(userHistory.getType());
        holder.binding.tvDescriptionAns.setText(userHistory.getDescription());

    }

    @Override
    public int getItemCount() {
        return userHistories.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        SampleDataBinding binding;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleDataBinding.bind(itemView);
        }
    }
}
