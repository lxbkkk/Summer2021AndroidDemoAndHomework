package com.example.hw2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        /*
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(),"button click",Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    public void ButtonTwoClick(View view){
        Log.d("tag","click button");
        Toast toast = Toast.makeText(getApplicationContext(),"咕噜咕噜",Toast.LENGTH_SHORT);
        toast.show();
    }
}