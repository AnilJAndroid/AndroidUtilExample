package com.example.jangid.androidutilsexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jangid.androidutilsexample.Adapter.Toro.ToroAdatpter;
import com.example.jangid.androidutilsexample.Adapter.Toro.ToroAdatpter1;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.widget.Container;

public class ExoPlayerActivity extends AppCompatActivity {

    @BindView(R.id.container)
    Container container;
    LinearLayoutManager layoutManager;
    ToroAdatpter1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exo_player);
        ButterKnife.bind(this);

        String[] strings = new String[]{
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                ,"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
                ,"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                ,"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"};

        layoutManager = new LinearLayoutManager(this);
        container.setLayoutManager(layoutManager);
        adapter = new ToroAdatpter1(getApplicationContext(), Arrays.asList(strings));
        container.setAdapter(adapter);
    }
}
