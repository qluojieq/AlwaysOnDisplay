package com.brandon.alwaysondisplay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

/**
 * Created by Brandon on 2021/5/6.
 **/
public class StyleWordsFragment extends Fragment {

    String type = "";
    public StyleWordsFragment(String Type) {
        this.type = Type;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.style_item_fragment,null);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.inner_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(StyleWordsFragment.this.getContext(),LinearLayoutManager.HORIZONTAL,false));
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
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((TextView)holder.itemView.findViewById(R.id.style_item_title_inner)).setText("风格类" + type);
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });
        return view;
    }


}
