package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.hw1.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the GO button */
    public void sendMessage(View view){
        // do something in response to button
        Log.d("tag","----- 点击按钮 -----");
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editTextTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editTextTextPersonName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
        Log.d("tag","----- Welcome -----");
        Log.d("tag","----- 按钮结束 -----");
    }

    public void selectCheckbox1(View view){
        //do something in response to checkbox
        Log.d("tag","----- 选择pika_1 -----");
    }

    public void selectCheckbox2(View view){
        //do something in response to checkbox
        Log.d("tag","----- 选择pika_2 -----");
    }

    public void selectCheckbox3(View view){
        //do something in response to checkbox
        Log.d("tag","----- 选择pika_3 -----");
    }

}