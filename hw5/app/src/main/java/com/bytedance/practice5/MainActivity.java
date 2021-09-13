package com.bytedance.practice5;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bytedance.practice5.model.Message;
import com.bytedance.practice5.model.MessageListResponse;
import com.bytedance.practice5.socket.SocketActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import retrofit2.http.Url;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private FeedAdapter adapter = new FeedAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_mine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Constants.STUDENT_ID);
            }
        });

        findViewById(R.id.btn_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("null");
            }
        });
        findViewById(R.id.btn_socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SocketActivity.class);
                startActivity(intent);
            }
        });



    }

    //TODO 2
    // 用HttpUrlConnection实现获取留言列表数据，用Gson解析数据，更新UI（调用adapter.setData()方法）
    // 注意网络请求和UI更新分别应该放在哪个线程中
    private void getData(String studentId){
        new Thread(new Runnable(){
            @Override
            public void run() {
                List<Message> msg = baseGetReposFromRemote(studentId);
                new Handler(getMainLooper()).post(new Runnable(){
                    @Override
                    public void run(){
                        adapter.setData(msg);
                    }
                });
            }
        }).start();
    }

    public List<Message> baseGetReposFromRemote(String student_id) {
        String urlStr;
        String nullStr = new String("null");
        Log.d("urlStr","" + student_id + student_id.equals(nullStr));
        if(student_id.equals(nullStr)){
            urlStr = String.format(Constants.BASE_URL + "messages?");
            Log.d("urlStr","without id");
        }else{
            urlStr = String.format(Constants.BASE_URL + "messages?" + "student_id=%s#",student_id);
            Log.d("urlStr","with id");
        }
        Log.d("urlStr","" + urlStr);
        MessageListResponse result = null;
        try{
            // ↓ 由url获得连接对象
            Log.d("wlzckk","网络1");
            URL url = new URL(urlStr + "message");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000);

            // ↓ 设置http method
            Log.d("wlzckk","网络2");
            connection.setRequestMethod("GET");

            // ↓ 设置header
            // accept是什么(*Φ皿Φ*)
            Log.d("wlzckk","网络3");
            connection.setRequestProperty("token",Constants.token);

            if(connection.getResponseCode() == 200){
                // ↓ 从InputStream读取数据
                Log.d("wlzckk","网络4");
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                // ↓ Gson解析
                Log.d("wlzckk","网络5");
                result = new Gson().fromJson(reader, new TypeToken<MessageListResponse>(){
                }.getType());
                Log.d("wlzckk","网络6");

                reader.close();
                in.close();
            }else{
                Log.d("wlzckk","" + connection.getResponseCode());

            }
            connection.disconnect();
        }catch(Exception e){
            e.printStackTrace();
            //Toast.makeText(this,"网络异常" + e.toString(), Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "网络异常" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return result.feeds;
    }
}