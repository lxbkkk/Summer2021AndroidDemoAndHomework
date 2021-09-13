package com.example.hw2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
    }

    public void ButtonToRecycler(View view){
        Log.d("tag","------ 跳转至RecyclerViewActivity ------");
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }
}