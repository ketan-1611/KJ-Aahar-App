package com.example.kjaaharapp.activity.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.activity.Activity.AboutActivity;
import com.example.kjaaharapp.activity.Activity.ContactUsActivity;
import com.example.kjaaharapp.activity.Activity.DonateActivity;
import com.example.kjaaharapp.activity.Activity.FoodMapActivity;
import com.example.kjaaharapp.activity.Activity.HistoryActivity;
import com.example.kjaaharapp.activity.Activity.MyPinsActivity;
import com.example.kjaaharapp.activity.Activity.ReceiveActivity;
import com.example.kjaaharapp.activity.Activity.SignUpActivity;
import com.example.kjaaharapp.activity.Activity.UserdataActivity;
import com.example.kjaaharapp.activity.Models.DashboardModel;
import com.example.kjaaharapp.databinding.SampleDashboardDesignBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder>{

    private Context context;
    private ArrayList<DashboardModel> dashboardModels;

    public DashboardAdapter(Context context, ArrayList<DashboardModel> dashboardModels) {
        this.context = context;
        this.dashboardModels = dashboardModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_dashboard_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DashboardModel model = dashboardModels.get(position);

        holder.binding.ivDashboard.setImageResource(model.getImg());
        holder.binding.tvDashboard.setText(model.getName());

        holder.binding.cardDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                if (position==0)
                {
                    Intent intent = new Intent(context, DonateActivity.class);
                    context.startActivity(intent);
                }
                else if (position==1)
                {
                    Intent intent = new Intent(context, ReceiveActivity.class);
                    context.startActivity(intent);
                }
                else if (position==2)
                {
                    Intent intent = new Intent(context, FoodMapActivity.class);
                    context.startActivity(intent);
                }
                else if (position==3)
                {
                    Intent intent = new Intent(context, MyPinsActivity.class);
                    context.startActivity(intent);
                }
                else if (position==4)
                {
                    Intent intent = new Intent(context, UserdataActivity.class);
                    context.startActivity(intent);
                }
                else if (position==5)
                {
                    Intent intent = new Intent(context, AboutActivity.class);
                    context.startActivity(intent);
                }
                else if (position==6)
                {
                    Intent intent = new Intent(context, ContactUsActivity.class);
                    context.startActivity(intent);
                }
                else if (position==7)
                {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(context, SignUpActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return dashboardModels.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        SampleDashboardDesignBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleDashboardDesignBinding.bind(itemView);

        }
    }
}
