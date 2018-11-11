package com.example.jangid.androidutilsexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jangid.androidutilsexample.Adapter.Toro.ToroAdatpter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.widget.Container;


public class ToroActivity extends AppCompatActivity {

    @BindView(R.id.container) Container container;
    LinearLayoutManager layoutManager;
    ToroAdatpter adapter;

//    PressablePlayerSelector selector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toro);
        ButterKnife.bind(this);

        String[] strings = new String[]{
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                ,"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
                ,"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                ,"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"};

        List<String> list = new ArrayList<>(Arrays.asList(strings));
        layoutManager = new LinearLayoutManager(this);
        container.setLayoutManager(layoutManager);
        adapter = new ToroAdatpter(getApplicationContext(),list);
        container.setAdapter(adapter);
//
//        selector = new PressablePlayerSelector(container);
//        container.setPlayerSelector(selector);

//        adapter = new ToroAdatpter(getApplicationContext(),selector);
//        container.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        layoutManager = null;
        adapter = null;
//        selector = null;
        super.onDestroy();

    }
}
