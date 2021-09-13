package com.example.proj;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RecycleFragment extends Fragment{

    // ↓ add by kk
   public RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public GridLayoutManager gridLayoutManager;
    public StaggeredGridLayoutManager PubuLayoutManager;
    // ↑


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page1, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        initRecyclerView(mRecyclerView);
        return view;
    }

    private void initRecyclerView(RecyclerView mRecyclerView){

        //创建线性布局管理器
        layoutManager = new LinearLayoutManager(this.getActivity());
        //创建格网布局管理器
        gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        //创建瀑布布局管理器
        PubuLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        mRecyclerView.setLayoutManager(PubuLayoutManager);
        //尝试分割线
        SpacesItemDecoration decoration=new SpacesItemDecoration(15);
        mRecyclerView.addItemDecoration(decoration);
        //创建Adapter
        mAdapter = new MyRecyclerAdapter(getActivity());
        //设置Adapter每个item的点击事件
        mAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(View view,List<Message> mdata){
                Intent intent = new Intent(getContext(),PlayActivity.class);
                ArrayList<Message> mList = new ArrayList<>();
                mList.addAll(mdata);
                intent.putExtra("message", (Serializable)mdata);
                startActivity(intent);
            }
        });

        //设置Adapter
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("urlStr","before get data in activity created");
        getData("");
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void getData(String studentId){
        new Thread(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Log.d("urlStr","before base get repos from remote");
                List<Message> msg = baseGetReposFromRemote(studentId);
                Log.d("urlStr","after base get repos from remote");
                new Handler(getActivity().getMainLooper()).post(new Runnable(){
                    @Override
                    public void run(){
                        Log.d("urlStr","in run setdata");
                        mAdapter.setData(msg);
                    }
                });
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Message> baseGetReposFromRemote(String student_id) {
        String urlStr;
        String nullStr = new String("null");
        Log.d("urlStr","" + student_id + student_id.equals(nullStr));

        urlStr = String.format(Constants.BASE_URL + "video");

        Log.d("urlStr","" + urlStr);
        MessageListResponse result = null;
        try{
            // ↓ 由url获得连接对象
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000);

            // ↓ 设置http method
            connection.setRequestMethod("GET");

            connection.setRequestProperty("token",Constants.token);

            if(connection.getResponseCode() == 200){
                // ↓ 从InputStream读取数据
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                // ↓ Gson解析
                result = new Gson().fromJson(reader, new TypeToken<MessageListResponse>(){
                }.getType());

                reader.close();
                in.close();
            }else{
                Log.d("wlzckk","" + connection.getResponseCode());
            }
            connection.disconnect();
        }catch(Exception e){
            e.printStackTrace();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "网络异常" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return result.feeds;
    }

}
