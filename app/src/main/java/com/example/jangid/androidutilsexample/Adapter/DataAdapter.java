package com.example.jangid.androidutilsexample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jangid.androidutilsexample.R;

import java.util.Arrays;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder>{
    private LayoutInflater mLayoutInflater;
    private SubDataAdapter subDataAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SimpleDividerItemDecoration decoration;
    public DataAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.subDataAdapter = new SubDataAdapter(context);
        this.decoration = new SimpleDividerItemDecoration(context);
        this.linearLayoutManager = new LinearLayoutManager(context);
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.rowview, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.sub_recyleview.addItemDecoration(decoration);
        holder.sub_recyleview.setLayoutManager(linearLayoutManager);
        holder.sub_recyleview.setAdapter(subDataAdapter);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
