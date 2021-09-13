package com.example.proj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

public class OpenAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_animation);


        LottieAnimationView animationView;
        animationView = findViewById(R.id.animation);

        animationView.setAlpha(1f);


        Intent intent = new Intent(this, MainActivity.class);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Log.d("inrun","------ in run ------");
                startActivity(intent);
                Log.d("inrun","------ before finish ------");
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
                Log.d("inrun","------ after finish ------");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,3000);

        ObjectAnimator animator = ObjectAnimator.ofFloat(animationView,
                "alpha",
                1.0f,
                0f);
        animator.setDuration(3000);
        animator.start();

        Log.d("nullpointer","----- after task ------");
    }
}