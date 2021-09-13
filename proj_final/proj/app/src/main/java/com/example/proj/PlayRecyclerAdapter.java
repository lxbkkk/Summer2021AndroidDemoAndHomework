package com.example.proj;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.net.URISyntaxException;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

class PlayRecyclerAdapter extends RecyclerView.Adapter<PlayRecyclerAdapter.VideoViewHolder> {
    public Context activity;
    private static List<Message> data;
    public PlayRecyclerAdapter.OnItemClickListener onItemClickListener;

    public long lastTime = 0;

    public PlayRecyclerAdapter(Context activity){
        this.activity = activity;
    }

    public void setData(List<Message> messageList){
        data = messageList;
        notifyDataSetChanged();
    }

    @Override
    public PlayRecyclerAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root =LayoutInflater.from(parent.getContext()).inflate(R.layout.playrecycleritem,parent,false);
        return new PlayRecyclerAdapter.VideoViewHolder(root);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PlayRecyclerAdapter.VideoViewHolder holder, int position){
        try {
            holder.bind(data.get(position),activity);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
            private VideoView videoView;
            private TextView textView;
            private ImageButton like;
            private LottieAnimationView animationView;
            private TextView flag;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.playVideo);
            textView = itemView.findViewById(R.id.idNumber);
            like = itemView.findViewById(R.id.like);
            animationView = itemView.findViewById(R.id.likeAnimation);
            flag = itemView.findViewById(R.id.flag);
            videoView.setOnTouchListener(new View.OnTouchListener(){
               @Override
               public boolean onTouch(View v, MotionEvent event){
                   if(System.currentTimeMillis() - lastTime < 500 && lastTime != 0){
                       //双击
                       //时间线修复 (*Φ皿Φ*)
                       if(!videoView.isPlaying()){
                           videoView.start();
                       }
                       else{
                           videoView.pause();
                       }

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

                   }else{
                       lastTime=System.currentTimeMillis();
                       if(!videoView.isPlaying()){
                           videoView.start();
                       }
                       else{
                           videoView.pause();
                       }
                   }
                   return false;
               }
            });
        }
        public void bind(Message message,Context activity) throws URISyntaxException {

            videoView.setVideoPath(message.getVideoUrl());
            videoView.start();

            MediaController mediaController = new MediaController(activity);
            mediaController.setVisibility(View.GONE);

            videoView.setMediaController(mediaController);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.start();
                }
            });

            textView.setText("id: "+message.getStudentId());
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view, Message mdata);
    }

    public void setOnItemClickListener(PlayRecyclerAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}


