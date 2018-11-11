package com.example.jangid.androidutilsexample.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jangid.androidutilsexample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.sub_recyleview)
    RecyclerView sub_recyleview;
    public DataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
