package com.example.jangid.androidutilsexample;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;

public class VideoTrimmerActivity extends AppCompatActivity {

//    @BindView(R.id.textTime)
    K4LVideoTrimmer mVideoTrimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_trimmer);

//        ButterKnife.bind(this);
        mVideoTrimmer = findViewById(R.id.timeLine);
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(10);
//            mVideoTrimmer.setOnTrimVideoListener(this);
//            mVideoTrimmer.setOnK4LVideoListener(this);
            mVideoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
            mVideoTrimmer.setVideoURI(Uri.parse("file:///android_asset/bbb.mp4"));
            mVideoTrimmer.setVideoInformationVisibility(true);
        }
    }
}
