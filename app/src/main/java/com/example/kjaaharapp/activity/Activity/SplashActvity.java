package com.example.kjaaharapp.activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.databinding.ActivitySplashActvityBinding;

public class SplashActvity extends AppCompatActivity {

    ActivitySplashActvityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashActvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.tvSplash.setAnimation(AnimationUtils.loadAnimation(this,R.anim.slideup));

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActvity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }
}