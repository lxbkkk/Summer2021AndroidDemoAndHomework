package com.example.hw2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ButtonToA(View view){
        Log.d("tag","------ 跳转至PracticeActivity ------");
        Intent intent = new Intent(this, PracticeActivity.class);
        startActivity(intent);
    }

    public void ButtonToB(View view){
        Log.d("tag","------ 跳转至百度页面 ------");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    public void ButtonToP(View view){
        //Intent intent= new Intent(Intent.ACTION_DIAL);
        //intent.setClassName("com.android.contacts","com.android.contacts.DialtactsActivity");
        //startActivity(intent);

        Log.d("tag","------ 跳转至电话拨号页面 ------");
        Intent dialIntent =  new Intent(Intent.ACTION_CALL_BUTTON);//跳转到拨号界面
        startActivity(dialIntent);
    }

    public void ButtonToD(View view){
        //跳转至绘制简单页面

        Log.d("tag","------ 跳转至绘制简单页面 ------");
        Intent intent = new Intent(this, DrawActivity.class);
        startActivity(intent);
    }

}