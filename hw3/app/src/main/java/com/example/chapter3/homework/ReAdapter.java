package com.example.chapter3.homework;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chapter3.homework.R;
import com.example.chapter3.homework.TestData;

import java.util.ArrayList;
import java.util.List;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.MyViewHolder> {
    private List<TestData> mDataset = new ArrayList<>();
    private IOnItemClickListener mItemClickListener;

    @Override
    public ReAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.onBind(position, mDataset.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemCLick(position, mDataset.get(position));
                }
            }
        });
        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemLongCLick(position, mDataset.get(position));
                }
                return false;
            }

        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface IOnItemClickListener {

        void onItemCLick(int position, TestData data);

        void onItemLongCLick(int position, TestData data);
    }

    public ReAdapter(List<TestData> myDataset) {
        mDataset.addAll(myDataset);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView wcname;
        private TextView wcdate;
        private TextView wctalk;
        private ImageView wcphoto;
        private View contentView;


        public MyViewHolder(View v) {
            super(v);
            contentView = v;
            wcname = v.findViewById(R.id.wc_name);
            wcdate = v.findViewById(R.id.wc_date);
            wctalk = v.findViewById(R.id.wc_talk);
            wcphoto = v.findViewById(R.id.im);
        }

        public void onBind(int position, TestData data) {
            wcname.setText(data.name);
            wcdate.setText(data.date);
            wctalk.setText(data.talk);
            //Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.a);
            if(position == 0){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.d);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 1){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.j);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 2){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.p);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 3){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.e);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 4){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.f);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 5){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.i);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 6){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.k);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 7){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.l);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 8){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.m);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 9){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.n);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 10){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.o);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 11){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.q);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 12){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.b);
                wcphoto.setImageDrawable(myDrawable);
            }else if(position == 13){
                Drawable myDrawable = wcphoto.getResources().getDrawable(R.drawable.c);
                wcphoto.setImageDrawable(myDrawable);
            }


        }

        public void setOnClickListener(View.OnClickListener listener) {
            if (listener != null) {
                contentView.setOnClickListener(listener);
            }
        }

        public void setOnLongClickListener(View.OnLongClickListener listener) {
            if (listener != null) {
                contentView.setOnLongClickListener(listener);
            }
        }
    }
}
