package com.example.jangid.androidutilsexample.Screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jangid.androidutilsexample.Adapter.DataAdapter;
import com.example.jangid.androidutilsexample.Adapter.SimpleDividerItemDecoration;
import com.example.jangid.androidutilsexample.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleviewSample extends AppCompatActivity {

    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_sample);
        ButterKnife.bind(this);





        // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
        recyleview.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyleview.setLayoutManager(mLayoutManager);
        recyleview.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        DataAdapter adapter = new DataAdapter(getApplicationContext());
        recyleview.setAdapter(adapter);



    }
}
