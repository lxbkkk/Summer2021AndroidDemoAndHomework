package com.example.chapter3.homework;


import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

public class PlaceholderFragment extends Fragment {

    public LottieAnimationView animationView;
    public View animationView2;

    // ↓ add by kk
    private RecyclerView mRecyclerView;
    private ReAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    // ↑


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件

        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);

        animationView = view.findViewById(R.id.animation_view_3);
        animationView2 = view.findViewById(R.id.rlv);

        // 缩小动画至0.3倍
        animationView.setScale(0.3f);

        // ↓ add by kk
        initRecyclerView((RecyclerView)animationView2);
        // ↑

        animationView.setAlpha(1f);
        animationView2.setAlpha(0f);
        return view;
    }

    // ↓ add by kk
    private void initRecyclerView(RecyclerView mRecyclerView){

        //更改数据时不会变更宽高
        mRecyclerView.setHasFixedSize(true);
        //创建线性布局管理器
        layoutManager = new LinearLayoutManager(this.getActivity());
        //创建格网布局管理器
        gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //创建Adapter
        mAdapter = new ReAdapter(TestDataSet.getData());
        //设置Adapter每个item的点击事件
        //mAdapter.setOnItemClickListener((ReAdapter.IOnItemClickListener)this.getFragmentManager());
        //设置Adapter
        mRecyclerView.setAdapter(mAdapter);
        //分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(Color.BLUE);
    }
    // ↑

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {

            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animationView2,"alpha",0f,1.0f);
                animator1.setDuration(1000);
                animator1.start();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(animationView,
                        "alpha",
                        1.0f,
                        0f);
                animator2.setDuration(1000);
                animator2.start();
                /*
                animationView.setAlpha(0f);
                animationView2.setAlpha(1f);
                */
            }
        }, 5000);
    }
}
