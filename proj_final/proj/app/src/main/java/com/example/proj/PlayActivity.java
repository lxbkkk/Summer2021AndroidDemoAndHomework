package com.example.proj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private RecyclerView playRecyclerview;
    private PlayRecyclerAdapter playRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Message> messagesList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        playRecyclerview = findViewById(R.id.playRecycler);
        initRecyclerView(playRecyclerview);

        intent = getIntent();
        messagesList = (List<Message>) intent.getSerializableExtra("message");
        playRecyclerAdapter.setData(messagesList);
    }

    private void initRecyclerView(RecyclerView playRecyclerview) {
        layoutManager = new LinearLayoutManager(this);
        playRecyclerview.setLayoutManager(layoutManager);
        playRecyclerAdapter = new PlayRecyclerAdapter(this);

        playRecyclerview.setAdapter(playRecyclerAdapter);
    }

    public void good(View view){
        LottieAnimationView animationView = this.findViewById(R.id.likeAnimation);
        ImageButton like = this.findViewById(R.id.like);
        TextView flag = this.findViewById(R.id.flag);
        if (flag.getText().toString().equals("0"))
        {
            flag.setText("1");
            animationView.setSpeed(2);
            animationView.setVisibility(View.VISIBLE);
            like.setBackgroundResource(R.drawable.goodup);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(animationView,"alpha",1f,0f);
            animator1.setDuration(2000);
            animator1.start();
        }
        else
        {
            flag.setText("0");
            like.setBackgroundResource(R.drawable.good);
        }
    }
}