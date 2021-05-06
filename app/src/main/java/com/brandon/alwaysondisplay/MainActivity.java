package com.brandon.alwaysondisplay;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class MainActivity extends FragmentActivity {
    // 在recycleview中加入fragment；https://stackoverflow.com/questions/37194653/fragment-replacing-in-recyclerview-item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rView = findViewById(R.id.style_container);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item, parent,false);
                MyViewHolder viewHolder = new MyViewHolder(inflate);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                ((MyViewHolder)holder).title.setText("主题风格" + position);
                RecyclerView recyclerView = ((MyViewHolder)holder).fragment;

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                recyclerView.setAdapter(new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View viewIn = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_inner,parent,false);
                        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(viewIn) {
                            @Override
                            public String toString() {
                                return super.toString();
                            }
                        };
                        return viewHolder;
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position1) {
                        ((TextView)holder.itemView.findViewById(R.id.style_item_title_inner)).setText(position+"风格类" + position1);
                    }

                    @Override
                    public int getItemCount() {
                        return 100;
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
    }
    public class MyViewHolder extends ViewHolder{
        TextView title;
        RecyclerView fragment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.style_item_title);
            fragment = itemView.findViewById(R.id.style_item_frame);
        }
    }
}