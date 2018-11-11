package com.example.jangid.androidutilsexample.Adapter.Toro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jangid.androidutilsexample.R;

import java.util.List;

public class ToroAdatpter extends RecyclerView.Adapter<VideoPostViewHolder> {
    private Context context;
    private List<String> list;

    public ToroAdatpter(Context applicationContext, List<String> list1) {
        this.context = applicationContext;
        this.list = list1;
    }

    @NonNull
    @Override
    public VideoPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_home_video, parent, false);
        return new VideoPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoPostViewHolder holder, final int position) {
        holder.bind(list.get(position));
        holder.getAhv_ivPlay().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                if(!holder.isPlaying())
                    holder.ahv_player.setVisibility(View.VISIBLE);
                    holder.getAhv_rlPostNormal().setVisibility(View.INVISIBLE);
                    holder.play();
*/
                if (holder.isPlaying()) {
                    holder.ahv_player.hideController();
                    holder.ahv_ivPlay.setImageResource(android.R.drawable.ic_media_pause);

                    holder.ahv_ivThumb.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.ahv_ivThumb.setVisibility(View.VISIBLE);
                        }
                    });

                    holder.ahv_player.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.ahv_player.setVisibility(View.INVISIBLE);
                        }
                    });

                    holder.pause();
                } else {
                    holder.ahv_ivPlay.setImageResource(0);

                    holder.ahv_ivThumb.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.ahv_ivThumb.setVisibility(View.INVISIBLE);
                        }
                    });

                    holder.ahv_player.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.ahv_player.setVisibility(View.VISIBLE);
                            holder.ahv_player.showController();
                        }
                    });
                    holder.play();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VideoPostViewHolder holder) {
        // Hide the player, reset the image and play icon back
        final VideoPostViewHolder videoPostViewHolder = (VideoPostViewHolder) holder;
        // videoPostViewHolder.ahv_ivThumb.setImageResource(0);
        videoPostViewHolder.ahv_ivThumb.setVisibility(View.VISIBLE);
        videoPostViewHolder.ahv_ivPlay.setImageResource(android.R.drawable.ic_media_play);
        videoPostViewHolder.ahv_player.setVisibility(View.INVISIBLE);
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewRecycled(@NonNull VideoPostViewHolder holder) {
        VideoPostViewHolder videoPostViewHolder = (VideoPostViewHolder) holder;
        videoPostViewHolder.ahv_ivThumb.setImageResource(0);
        super.onViewRecycled(holder);
    }
}


