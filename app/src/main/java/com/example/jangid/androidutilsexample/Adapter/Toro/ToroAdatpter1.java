package com.example.jangid.androidutilsexample.Adapter.Toro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jangid.androidutilsexample.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.List;

public class ToroAdatpter1 extends RecyclerView.Adapter<VideoPostViewHolder1> {
    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private List<String> list;
    private final int KEY_PAYLOAD = 111;

    public ToroAdatpter1(Context applicationContext, List<String> list1) {
        this.context = applicationContext;
        this.list = list1;
    }

    @NonNull
    @Override
    public VideoPostViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_home_video1, parent, false);
        return new VideoPostViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPostViewHolder1 holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        if (payloads.contains(KEY_PAYLOAD)) {
            holder.btn_like.setText("Dislike");
        }

        Log.e(TAG, "onBindViewHolder: ");

    }

    @Override
    public void onBindViewHolder(@NonNull final VideoPostViewHolder1 holder, final int position) {
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context);
        holder.bind(list.get(position));
        holder.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(position, KEY_PAYLOAD);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VideoPostViewHolder1 holder) {
        // Hide the player, reset the image and play icon back
        final VideoPostViewHolder1 videoPostViewHolder = (VideoPostViewHolder1) holder;
        // videoPostViewHolder.ahv_ivThumb.setImageResource(0);
        videoPostViewHolder.ahv_ivThumb.setVisibility(View.VISIBLE);
        videoPostViewHolder.ahv_ivPlay.setImageResource(android.R.drawable.ic_media_play);
        videoPostViewHolder.ahv_player.setVisibility(View.INVISIBLE);
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewRecycled(@NonNull VideoPostViewHolder1 holder) {
        VideoPostViewHolder1 videoPostViewHolder = (VideoPostViewHolder1) holder;
        videoPostViewHolder.ahv_ivThumb.setImageResource(0);
        super.onViewRecycled(holder);
    }
}


