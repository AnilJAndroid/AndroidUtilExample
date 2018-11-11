package com.example.jangid.androidutilsexample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jangid.androidutilsexample.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubDataAdapter extends RecyclerView.Adapter<SubDataViewHolder>{
    private List<String> models = new ArrayList<>();
    private Context context;
    private LayoutInflater mLayoutInflater;
      // Defined Array values to show in ListView




    public SubDataAdapter(Context context) {
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        String[] values = new String[] {
                "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i] + " " + i;
        }
        models.addAll(Arrays.asList(values));
    }

    @NonNull
    @Override
    public SubDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.subrowview, parent, false);
        return new SubDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubDataViewHolder holder, int position) {
        holder.title.setText(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
