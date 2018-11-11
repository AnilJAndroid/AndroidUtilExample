package com.example.jangid.androidutilsexample.Adapter.Toro;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.jangid.androidutilsexample.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPostViewHolder1 extends RecyclerView.ViewHolder {
    private static final String TAG = "Toro:Basic:Holder";

    Uri mediaUri;

    @BindView(R.id.ahv_player)
    PlayerView ahv_player;

    /*@BindView(R.id.videoLoading)
    ImageView videoLoading;*/

    @BindView(R.id.ahv_ivThumb)
    ImageView ahv_ivThumb;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;


    @BindView(R.id.btn_like)
    Button btn_like;
    public ImageView getAhv_ivThumb() {

        return ahv_ivThumb;
    }



    @BindView(R.id.ahv_rlPostNormal)
    RelativeLayout ahv_rlPostNormal;

    public RelativeLayout getAhv_rlPostNormal() {
        return ahv_rlPostNormal;
    }


    @BindView(R.id.ahv_ivPlay)
    ImageView ahv_ivPlay;

    public ImageView getAhv_ivPlay() {
        return ahv_ivPlay;
    }

    SimpleExoPlayer player;
    public VideoPostViewHolder1(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
    void bind(String media) {
        this.mediaUri = Uri.parse(media);
    }

}
