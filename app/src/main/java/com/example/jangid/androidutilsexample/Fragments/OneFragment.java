package com.example.jangid.androidutilsexample.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jangid.androidutilsexample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {


    public OneFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.trimevideo)
    TextView trimevideo;
    @BindView(R.id.textTime)
    K4LVideoTrimmer mVideoTrimmer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        trimevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "DSD", Toast.LENGTH_SHORT).show();
            }
        });
        ;
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(10);
//            mVideoTrimmer.setOnTrimVideoListener(this);
//            mVideoTrimmer.setOnK4LVideoListener(this);
            //mVideoTrimmer.setDestinationPath("/storage/emulated/0/DCIM/CameraCustom/");
            mVideoTrimmer.setVideoURI(Uri.parse("file:///android_asset/bbb.mp4"));
            mVideoTrimmer.setVideoInformationVisibility(true);
        }
        return view;
    }

}
