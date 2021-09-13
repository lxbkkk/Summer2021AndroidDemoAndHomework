package com.example.hw2_2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2_2.recycler.LinearItemDecoration;
import com.example.hw2_2.recycler.MyAdapter;
import com.example.hw2_2.recycler.TestData;
import com.example.hw2_2.recycler.TestDataSet;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements MyAdapter.IOnItemClickListener{

    private RecyclerView mRecyclerView;
    //private List<TestData> testdata = new ArrayList<>();

    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Log.i("tag", "------ RecyclerViewActivity onCreate ------");

        initView();

        /*
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rlv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adaoter = new MyAdapter(testdata);
        */
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rlv);
        //获取实例
        //更改数据时不会变更宽高
        mRecyclerView.setHasFixedSize(true);
        //创建线性布局管理器
        layoutManager = new LinearLayoutManager(this);
        //创建格网布局管理器
        gridLayoutManager = new GridLayoutManager(this, 2);
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //创建Adapter
        mAdapter = new MyAdapter(TestDataSet.getData());
        //设置Adapter每个item的点击事件
        mAdapter.setOnItemClickListener((MyAdapter.IOnItemClickListener)this);
        //设置Adapter
        mRecyclerView.setAdapter(mAdapter);
        //分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(Color.BLUE);
        //recyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //动画
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(3000);
        mRecyclerView.setItemAnimator(animator);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("tag", "------ RecyclerViewActivity onStart ------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("tag", "------ RecyclerViewActivity onResume ------");
    }


    //@Override
    public void onItemCLick(int position, TestData data) {
        Toast.makeText(RecyclerViewActivity.this, "点击了第" + position + "条", Toast.LENGTH_SHORT).show();
        //mAdapter.addData(position + 1, new TestData("新增一只大胖", "0w"));
    }

    //@Override
    public void onItemLongCLick(int position, TestData data) {
        Toast.makeText(RecyclerViewActivity.this, "长按了第" + position + "条", Toast.LENGTH_SHORT).show();
        //mAdapter.removeData(position);
    }
}