package com.example.hw2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void ButtonTwoClick(View view){
        Log.d("tag","click button");
        Toast toast = Toast.makeText(getApplicationContext(),"咕噜咕噜",Toast.LENGTH_SHORT);
        toast.show();
    }

}