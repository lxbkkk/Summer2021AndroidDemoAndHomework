package com.example.proj;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.VideoViewHolder>{
    private static List<Message> data;
    public Context activity;
    public OnItemClickListener onItemClickListener;
    public MyRecyclerAdapter(Context activity){
        this.activity = activity;
    }
    public void setData(List<Message> messageList){
        data = messageList;
        notifyDataSetChanged();
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root =LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new VideoViewHolder(root);
    }

    @Override

    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(data.get(position),activity);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        private ImageView coverSD;
        private TextView idTV;
        private TextView nameTV;

        private View contentView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            contentView = itemView;
            idTV = itemView.findViewById(R.id.tv_from);
            nameTV = itemView.findViewById(R.id.tv_to);
            coverSD = itemView.findViewById(R.id.sd_cover);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null){
                        Message m = data.get(0);
                        data.set(0,data.get(getAdapterPosition()));
                        data.set(getAdapterPosition(),m);
                        onItemClickListener.OnItemClick(v,data);
                    }
                }
            });
        }
        public void bind(Message message,Context activity){

            DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(800).setCrossFadeEnabled(true).build();

            Glide.with(activity).load(message.getImageUrl())
                    .placeholder(R.drawable.ll)
                    .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))
                    .into(coverSD);

            idTV.setText("id: "+message.getStudentId());
            nameTV.setText("name:"+message.getUserName());
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view, List<Message> mdata);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

}
