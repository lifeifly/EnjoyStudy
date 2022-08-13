package com.lifei.customview.吸顶recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lifei.customview.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> {
    private Context mContext;
    private List<Star> mDatas;

    public StarAdapter(Context context, List<Star> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @NonNull
    @NotNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.start_item, parent, false);

        return new StarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StarAdapter.StarViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position).getName());
    }

    /**
     * 判断position是不是组头
     *
     * @param position
     * @return
     */
    public boolean isGroupHeader(int position) {
        if (position == 0) {
            return true;
        } else {
            String curGroupName = getItemGroupName(position);
            String preGroupName = getItemGroupName(position - 1);
            if (curGroupName.equals(preGroupName)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public String getItemGroupName(int position) {
        return (mDatas == null || position >= mDatas.size()) ? null : mDatas.get(position).getGroupName();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public static class StarViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public StarViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
