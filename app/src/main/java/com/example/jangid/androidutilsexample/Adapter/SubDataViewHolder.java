package com.example.jangid.androidutilsexample.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jangid.androidutilsexample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubDataViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView title;
    public SubDataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
