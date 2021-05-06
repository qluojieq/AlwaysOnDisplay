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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rView = findViewById(R.id.style_container);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item, parent,false);
                ViewHolder viewHolder = new ViewHolder(inflate) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                ((TextView)holder.itemView.findViewById(R.id.style_item_title)).setText("主题风格" + position);
                holder.itemView.setTag(position);

                FrameLayout fragment = (FrameLayout) holder.itemView.findViewById(R.id.style_item_frame);
                StyleWordsFragment styleWordsFragment = new StyleWordsFragment(position+"");
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(fragment.getId(),styleWordsFragment,position+"");
                fragmentTransaction.commit();
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
    }
}