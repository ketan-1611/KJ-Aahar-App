package com.example.kjaaharapp.activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.kjaaharapp.R;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);



    }

     void showProgressDialog(String message){
        dialog = new ProgressDialog(this);

        dialog.setTitle(message);
        dialog.show();
    }

     void hideProgressDialog(){
        dialog.dismiss();
    }


}