package com.lifei.enjoystudy.view;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lifei.enjoystudy.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.FirstViewHolder> {
    private List<String> datas;

    public FirstAdapter(List<String> datas) {
        this.datas = datas;
    }

    @NonNull
    @NotNull
    @Override
    public FirstViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.first_item,parent,false);
        return new FirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FirstAdapter.FirstViewHolder holder, int position) {
        holder.tv.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class FirstViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public FirstViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
