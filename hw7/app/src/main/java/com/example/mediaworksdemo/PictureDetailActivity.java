package com.example.mediaworksdemo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PictureDetailActivity extends AppCompatActivity {

    String mockUrl = "https://lf1-cdn-tos.bytescm.com/obj/static/ies/bytedance_official_cn/_next/static/images/0-390b5def140dc370854c98b8e82ad394.png";
    String mockErrorUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Android_logo_2019_%28stacked%29.svg/400px-Android_logo_2019_%28stacked%29.svg.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);

        ImageView imageView = findViewById(R.id.iv_detail);
        Button btnSuccess = findViewById(R.id.btn_load_success);
        Button btnFail = findViewById(R.id.btn_load_fail);
        Button btnRoundedCorners = findViewById(R.id.btn_rounded_corners);

        // TODO 1 展示图片，加载中、加载失败有占位图
        // TODO 2 渐变显示
        btnSuccess.setOnClickListener( v -> {
            Glide.with(this).load(mockUrl)
                    .placeholder(R.drawable.loading_green)
                    .error(R.drawable.error_red)
                    .transition(withCrossFade(500))
                    .into(imageView);
        });

        btnFail.setOnClickListener( v -> {
            Glide.with(this).load(mockErrorUrl)
                    .placeholder(R.drawable.loading_green)
                    .error(R.drawable.error_red)
                    .transition(withCrossFade(500))
                    .into(imageView);
        });

        // TODO 3 圆角
        btnRoundedCorners.setOnClickListener( v-> {

            DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(500).setCrossFadeEnabled(true).build();

            Glide.with(this).load(mockUrl)
                    .placeholder(R.drawable.loading_green)
                    .error(R.drawable.error_red)
                    .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                    //.transition(withCrossFade(500))
                    // ↑ 如果直接用这个渐变显示圆角的原图片后四角会有占位图剩余 (*Φ皿Φ*)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                    .into(imageView);
        });
    }
}
