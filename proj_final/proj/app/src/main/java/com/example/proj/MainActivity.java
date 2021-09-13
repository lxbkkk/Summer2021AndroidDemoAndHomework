package com.example.proj;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.proj.MyPagerAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class MainActivity extends AppCompatActivity implements OnClickListener, ViewPager.OnPageChangeListener {

    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private LinearLayout ll_3;

    private ImageView iv_1;
    private ImageView iv_2;
    private ImageView iv_3;

    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;

    private ViewPager viewPager;

    private MyPagerAdapter adapter;
    private ArrayList<Fragment> FragmentList = new ArrayList<>();

    //RecyclerView
    private MyRecyclerAdapter recyclerAdapter;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private static final int REQUEST_CODE_VIDEO = 102;
    private static final int REQUEST_CODE_IMAGE = 101;

    UploadFragment uploadFragment = new UploadFragment();
    RadioFragment radioFragment = new RadioFragment();
    RecycleFragment recycleFragment = new RecycleFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.d("mainact","----- before set -----");

        setContentView(R.layout.activity_main);

        Log.d("mainact","----- after set -----");

        Fresco.initialize(MainActivity.this);
        initView();
        initEvent();
    }

    private void initEvent() {

        //底部按钮监听
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);

        // ViewPager滑动监听
        viewPager.addOnPageChangeListener(this);
    }

    private void initView() {

        // 底部按钮
        this.ll_1 = (LinearLayout) findViewById(R.id.ll_view);
        this.ll_2 = (LinearLayout) findViewById(R.id.ll_upload);
        this.ll_3 = (LinearLayout) findViewById(R.id.ll_radio);

        this.iv_1 = (ImageView) findViewById(R.id.iv_view);
        this.iv_2 = (ImageView) findViewById(R.id.iv_upload);
        this.iv_3 = (ImageView) findViewById(R.id.iv_radio);

        this.tv_1 = (TextView) findViewById(R.id.tv_view);
        this.tv_2 = (TextView) findViewById(R.id.tv_upload);
        this.tv_3 = (TextView) findViewById(R.id.tv_radio);

        //滑动viewPager
        this.viewPager = (ViewPager) findViewById(R.id.vp_content);

        FragmentList.add(recycleFragment);
        FragmentList.add(uploadFragment);
        FragmentList.add(radioFragment);
        //FragmentList.add(new RecycleFragment());

        this.adapter = new MyPagerAdapter(fragmentManager,FragmentList);
        viewPager.setOffscreenPageLimit(FragmentList.size() - 1);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        restartBotton();
        switch (v.getId()) {
            case R.id.ll_view:
                iv_1.setImageResource(R.drawable.viewblue);
                tv_1.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(0);
                break;

            case R.id.ll_upload:
                iv_2.setImageResource(R.drawable.uploadblue);
                tv_2.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(1);
                break;

            case R.id.ll_radio:
                iv_3.setImageResource(R.drawable.radioblue);
                tv_3.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(2);
                break;

            default:
                break;
        }
    }

    //初始化按钮
    private void restartBotton() {
        iv_1.setImageResource(R.drawable.viewnew);
        iv_2.setImageResource(R.drawable.uploadnew);
        iv_3.setImageResource(R.drawable.radionew);
        tv_1.setTextColor(getResources().getColor(R.color.white));
        tv_2.setTextColor(getResources().getColor(R.color.white));
        tv_3.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        restartBotton();
        //切换page
        switch (arg0) {
            case 0:
                iv_1.setImageResource(R.drawable.viewblue);
                tv_1.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:
                iv_2.setImageResource(R.drawable.uploadblue);
                tv_2.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                iv_3.setImageResource(R.drawable.radioblue);
                tv_3.setTextColor(getResources().getColor(R.color.black));
                break;
            default:
                break;
        }
    }

    public void onClickSelect(View view){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,REQUEST_CODE_VIDEO);

        uploadFragment.video.start();
        uploadFragment.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();
            }
        });
    }

    public void onClickUpload(View view){
        Toast.makeText(this, "开始上传", Toast.LENGTH_LONG).show();
        uploadFragment.submit();
    }

    public void onClickImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,REQUEST_CODE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_VIDEO){
            if(resultCode == RESULT_OK){
                uploadFragment.videoUri = data.getData();
                uploadFragment.video.setVideoURI(uploadFragment.videoUri);
                uploadFragment.video.setVisibility(View.VISIBLE);
            }
        }else if(requestCode == REQUEST_CODE_IMAGE){
            if(resultCode == RESULT_OK){
                uploadFragment.coverImageUri = data.getData();
                uploadFragment.coverImage.setImageURI(uploadFragment.coverImageUri);
                uploadFragment.coverImage.setVisibility(View.VISIBLE);
            }
        }
    }

    public void record(View view) {
        if (radioFragment.isRecording) {
            radioFragment.mRecordButton.setText("RECORD");

            radioFragment.mMediaRecorder.setOnErrorListener(null);
            radioFragment.mMediaRecorder.setOnInfoListener(null);
            radioFragment.mMediaRecorder.setPreviewDisplay(null);

            try {
                radioFragment.mMediaRecorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            radioFragment.mMediaRecorder.reset();
            radioFragment.mMediaRecorder.release();
            radioFragment.mMediaRecorder = null;
            radioFragment.mCamera.lock();

            //设置page2的video
            uploadFragment.video.setVideoPath(radioFragment.mp4Path);
            uploadFragment.video.setVisibility(View.VISIBLE);
            uploadFragment.video.start();
            uploadFragment.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.start();
                }
            });

            //获得视频uri
            uploadFragment.videoUri = PathUtils.getUriForFile(this,radioFragment.mp4Path);

            //跳转进page2
            viewPager.setCurrentItem(1);

            //释放相机
            //radioFragment.surfaceDestroyed(radioFragment.mHolder);

        } else {
            if (radioFragment.prepareVideoRecorder()) {
                radioFragment.mRecordButton.setText("STOP");
                radioFragment.mMediaRecorder.start();
            }
        }
        radioFragment.isRecording = !radioFragment.isRecording;
    }


    public void ClickImageButton(View view){

        recycleFragment.mRecyclerView.scrollToPosition(0);
        recycleFragment.getData("");
        recycleFragment.onResume();

    }

}