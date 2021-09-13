package com.example.hw2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
    }

    public void ButtonToToast(View view){
        // print a toast in response to button
        //Toast.makeText(this,"1222222",Toast.LENGTH_SHORT).show();
        //midToast("hello");

        //Toast.makeText(getApplicationContext(),"默认的Toast",Toast.LENGTH_LONG).show();
        //maketext决定Toast显示内容
        //Toast toastCenter = Toast.makeText(getApplicationContext(),"咕噜咕噜",Toast.LENGTH_SHORT);

        //setGravity决定Toast显示位置
        //↓ 版本不适用(*Φ皿Φ*)
        //toastCenter.setGravity(Gravity.CENTER,0,0);

        //调用show使得toast得以显示
        //toastCenter.show();

        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }

}