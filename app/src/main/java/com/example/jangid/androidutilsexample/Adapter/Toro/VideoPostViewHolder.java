package com.example.jangid.androidutilsexample.Adapter.Toro;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.jangid.androidutilsexample.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.exoplayer.Playable;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

public class VideoPostViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {
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


    @Nullable
    private ExoPlayerViewHelper helper;

    @Nullable
    public ExoPlayerViewHelper getHelper() {
        return helper;
    }


    public VideoPostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @NonNull
    @Override
    public View getPlayerView() {
        return ahv_player;
    }

    @NonNull
    @Override
    public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
    }


    @Override
    public void initialize(@NonNull Container container, @NonNull PlaybackInfo playbackInfo) {
        if (helper == null) {
            helper = new ExoPlayerViewHelper(this, mediaUri);
        }
        helper.initialize(container, playbackInfo);
        ahv_player.setHideControllerEx(true);
        updatePlay(false);

        final View playBtn = ahv_player.getController().getPlayButton();
        final View pauseBtn = ahv_player.getController().getPauseButton();
        final ExoPlayerViewHelper exoPlayerViewHelper = getHelper();
        if (exoPlayerViewHelper != null) {
            exoPlayerViewHelper.addEventListener(new Playable.DefaultEventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                    super.onTimelineChanged(timeline, manifest, reason);
                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                    super.onTracksChanged(trackGroups, trackSelections);
                }

                @Override
                public void onLoadingChanged(boolean isLoading) {
                    super.onLoadingChanged(isLoading);
                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    super.onPlayerStateChanged(playWhenReady, playbackState);

                    if (playWhenReady && playbackState == 2) {
                        progressbar.setVisibility(View.VISIBLE);
                    } else {
                        progressbar.setVisibility(View.INVISIBLE);
                    }

                    if (playbackState == 4) {
                        final View pause = ahv_player.getController().getPauseButton();
                        if (pause != null && pause.getVisibility() == View.VISIBLE) {
                            pause.post(new Runnable() {
                                @Override
                                public void run() {
                                    pause.performClick();
                                }
                            });
                        }
                        Player player = ahv_player.getPlayer();
                        if (player != null) {
                            if (player.isCurrentWindowSeekable()) {
                                player.seekTo(0L);
                            }
                        }

                        // Resetting to Image
                        ahv_player.hideController();
                        ahv_ivPlay.setImageResource(R.drawable.images);
                        ahv_ivThumb.setVisibility(View.VISIBLE);
                        ahv_player.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {
                    super.onRepeatModeChanged(repeatMode);
                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                    super.onShuffleModeEnabledChanged(shuffleModeEnabled);
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    super.onPlayerError(error);
                }

                @Override
                public void onPositionDiscontinuity(int reason) {
                    super.onPositionDiscontinuity(reason);
                    }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                    super.onPlaybackParametersChanged(playbackParameters);
                }

                @Override
                public void onSeekProcessed() {
                    super.onSeekProcessed();
                }

                @Override
                public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                    super.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio);
                }

                @Override
                public void onRenderedFirstFrame() {
                    super.onRenderedFirstFrame();
                }

                @Override
                public void onCues(List<Cue> cues) {
                    super.onCues(cues);
                }

                @Override
                public void onMetadata(Metadata metadata) {
                    super.onMetadata(metadata);

                }
            });
        }
    }

    @Override
    public void play() {
        if (helper != null) helper.play();
    }

    @Override
    public void pause() {
        if (helper != null) helper.pause();
    }

    @Override
    public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override
    public void release() {
        if (helper != null) {
            helper.release();
            helper = null;
        }
    }

    @Override
    public boolean wantsToPlay() {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
    }

    @Override
    public int getPlayerOrder() {
        return getAdapterPosition();
    }

    @Override
    public String toString() {
        return "ExoPlayer{" + hashCode() + " " + getAdapterPosition() + "}";
    }

    void bind(String media) {
        this.mediaUri = Uri.parse(media);
    }

    private void autoPlayOff() {
        // Auto Play off
        try {
            pause();

            final Player player1 = ahv_player.getPlayer();
            if (player1 != null) {
                while (player1.getRepeatMode() != Player.REPEAT_MODE_OFF) {
                    final ImageView imageView = ahv_player.getController().getRepeatToggleButton();
                    if (imageView != null) {
                        imageView.performClick();
                    }
                }
            }

            final View pause = ahv_player.getController().getPauseButton();
            if (pause != null /*&& pause.getVisibility() == View.VISIBLE*/) {
                pause.post(new Runnable() {
                    @Override
                    public void run() {
                        pause.performClick();
                    }
                });
            }

            final View previous = ahv_player.getController().getPreviousButton();
            if (previous != null) {
                previous.post(new Runnable() {
                    @Override
                    public void run() {
                        previous.performClick();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePlay(final boolean playing) {
        if (ahv_player != null) {
            ahv_player.post(new Runnable() {
                @Override
                public void run() {
                    final View playBtn = ahv_player.getController().getPlayButton();
                    final View pauseBtn = ahv_player.getController().getPauseButton();
                    if (playing) {
                        play();
                        playBtn.post(new Runnable() {
                            @Override
                            public void run() {
                                playBtn.setVisibility(View.GONE);
                            }
                        });
                        pauseBtn.post(new Runnable() {
                            @Override
                            public void run() {
                                pauseBtn.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        pause();
                        playBtn.post(new Runnable() {
                            @Override
                            public void run() {
                                playBtn.setVisibility(View.VISIBLE);
                            }
                        });
                        pauseBtn.post(new Runnable() {
                            @Override
                            public void run() {
                                pauseBtn.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            });
        }
    }


    private PlayerControlView getController(PlayerView playerView) {
        try {
            Field f = playerView.getClass().getDeclaredField("controller"); //NoSuchFieldException
            f.setAccessible(true);
            PlayerControlView controlView = (PlayerControlView) f.get(playerView); //IllegalAccessException
            return controlView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
