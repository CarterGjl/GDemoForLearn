package aop.demo.jetpack.android.exoplayer.playmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.Arrays;

import aop.demo.jetpack.android.exoplayer.DemoApplication;

public class ExoPlayManager implements ExoPlayControyInterface, LifecyclerListener {

    private SimpleExoPlayer mSimpleExoPlayer;
    private DefaultControlDispatcher mDefaultControlDispatcher;
    private DefaultDataSourceFactory mDefaultDataSourceFactory;
    private DefaultExtractorsFactory mDefaultExtractorsFactory;
    private Context mContext;
    private Timeline.Window mWindow;
    private Timeline.Period period;
    private long[] adGroupTimesMs;
    private boolean[] playedAdGroups;
    private Runnable updateProgressAction;
    private Handler handler;

    public static ExoPlayManager create(Context context) {
        ExoPlayManager exoPlayManager = new ExoPlayManager();
        exoPlayManager.init(context);
        return exoPlayManager;
    }

    @Override
    public void init(Context context) {
        mContext = context;
        mDefaultControlDispatcher = new DefaultControlDispatcher();
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        mDefaultDataSourceFactory = new DefaultDataSourceFactory(context, "audio/mpeg");
        mDefaultExtractorsFactory = new DefaultExtractorsFactory();
        mWindow = new Timeline.Window();
        period = new Timeline.Period();
        adGroupTimesMs = new long[0];
        playedAdGroups = new boolean[0];
        updateProgressAction = this::updateProgress;
        handler=new Handler();
        updateProgress();
    }

    private static final String TAG = "ExoPlayManager";

    @Override
    public void play(String url) {
        String proxyUrl = DemoApplication.getProxy(mContext).getProxyUrl("http://5.595818" +
                ".com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3", true);
        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource(Uri.parse(proxyUrl),
                mDefaultDataSourceFactory,
                mDefaultExtractorsFactory, null, null);
        mSimpleExoPlayer.prepare(extractorMediaSource);
        mSimpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                Log.d(TAG, "onTimelineChanged: " + timeline.toString());
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                Log.d(TAG, "onTracksChanged: ");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

                Log.d(TAG, "onLoadingChanged: " + isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if (playWhenReady){
                    handler.post(updateProgressAction);
                }
                updatePlayPauseButton();
                Log.d(TAG, "onPlayerStateChanged: playWhenReady:" + playbackState);
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

                Log.e(TAG, "onPlayerError: ", error);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

                Log.d(TAG, "onPositionDiscontinuity: " + reason);
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });


        mSimpleExoPlayer.setPlayWhenReady(true);
    }

    private boolean isPlaying() {
        return mSimpleExoPlayer != null
                && mSimpleExoPlayer.getPlaybackState() != Player.STATE_ENDED
                && mSimpleExoPlayer.getPlaybackState() != Player.STATE_IDLE
                && mSimpleExoPlayer.getPlayWhenReady();
    }

    private void updatePlayPauseButton() {
        boolean requestPlayPauseFocus = false;
        boolean playing = isPlaying();
        
        if (mOnProgressChangeListener != null) {
            mOnProgressChangeListener.onPlayStateChange(playing);
        }
    }

    @Override
    public void stop() {
        mDefaultControlDispatcher.dispatchSetPlayWhenReady(mSimpleExoPlayer, false);
    }

    @Override
    public void seekTo(long position) {
        seekToTimeBarPosition(position);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

        mSimpleExoPlayer.release();
    }

    @Override
    public void onPause() {
        mDefaultControlDispatcher.dispatchSetPlayWhenReady(mSimpleExoPlayer, false);
    }

    private void seekToTimeBarPosition(long positionMs) {
        int windowIndex;
        Timeline timeline = mSimpleExoPlayer.getCurrentTimeline();
        if (!timeline.isEmpty()) {
            int windowCount = timeline.getWindowCount();
            windowIndex = 0;
            while (true) {
                long windowDurationMs = timeline.getWindow(windowIndex, mWindow).getDurationMs();
                if (positionMs < windowDurationMs) {
                    break;
                } else if (windowIndex == windowCount - 1) {
                    // Seeking past the end of the last window should seek to the end of the timeline.
                    positionMs = windowDurationMs;
                    break;
                }
                positionMs -= windowDurationMs;
                windowIndex++;
            }
        } else {
            windowIndex = mSimpleExoPlayer.getCurrentWindowIndex();
        }
        seekTo(windowIndex, positionMs);
    }
    private void seekTo(int windowIndex, long positionMs) {
        boolean dispatched = mDefaultControlDispatcher.dispatchSeekTo(mSimpleExoPlayer, windowIndex, positionMs);
        if (!dispatched) {
            // The seek wasn't dispatched. If the progress bar was dragged by the user to perform the
            // seek then it'll now be in the wrong position. Trigger a progress update to snap it back.
            updateProgress();
        }
    }

    private void updateProgress() {
//        if (!isVisible() || !isAttachedToWindow) {
//            return;
//        }

        long position = 0;
        long bufferedPosition = 0;
        long duration = 0;
        if (mSimpleExoPlayer != null) {
            long currentWindowTimeBarOffsetMs = 0;
            long durationUs = 0;
            int adGroupCount = 0;
            Timeline timeline = mSimpleExoPlayer.getCurrentTimeline();
            if (!timeline.isEmpty()) {
                int currentWindowIndex = mSimpleExoPlayer.getCurrentWindowIndex();
                int firstWindowIndex =  currentWindowIndex;
                int lastWindowIndex = currentWindowIndex;
                for (int i = firstWindowIndex; i <= lastWindowIndex; i++) {
                    if (i == currentWindowIndex) {
                        currentWindowTimeBarOffsetMs = C.usToMs(durationUs);
                    }
                    timeline.getWindow(i, mWindow);
                    if (mWindow.durationUs == C.TIME_UNSET) {
//                        Assertions.checkState(!multiWindowTimeBar);
                        break;
                    }
                    for (int j = mWindow.firstPeriodIndex; j <= mWindow.lastPeriodIndex; j++) {
                        timeline.getPeriod(j, period);
                        int periodAdGroupCount = period.getAdGroupCount();
                        for (int adGroupIndex = 0; adGroupIndex < periodAdGroupCount; adGroupIndex++) {
                            long adGroupTimeInPeriodUs = period.getAdGroupTimeUs(adGroupIndex);
                            if (adGroupTimeInPeriodUs == C.TIME_END_OF_SOURCE) {
                                if (period.durationUs == C.TIME_UNSET) {
                                    // Don't show ad markers for postrolls in periods with unknown duration.
                                    continue;
                                }
                                adGroupTimeInPeriodUs = period.durationUs;
                            }
                            long adGroupTimeInWindowUs = adGroupTimeInPeriodUs + period.getPositionInWindowUs();
                            if (adGroupTimeInWindowUs >= 0 && adGroupTimeInWindowUs <= mWindow.durationUs) {
                                if (adGroupCount == adGroupTimesMs.length) {
                                    int newLength = adGroupTimesMs.length == 0 ? 1 : adGroupTimesMs.length * 2;
                                    adGroupTimesMs = Arrays.copyOf(adGroupTimesMs, newLength);
                                    playedAdGroups = Arrays.copyOf(playedAdGroups, newLength);
                                }
                                adGroupTimesMs[adGroupCount] = C.usToMs(durationUs + adGroupTimeInWindowUs);
                                playedAdGroups[adGroupCount] = period.hasPlayedAdGroup(adGroupIndex);
                                adGroupCount++;
                            }
                        }
                    }
                    durationUs += mWindow.durationUs;
                }
            }
            duration = C.usToMs(durationUs);
            position = currentWindowTimeBarOffsetMs + mSimpleExoPlayer.getContentPosition();
            bufferedPosition = currentWindowTimeBarOffsetMs + mSimpleExoPlayer.getContentBufferedPosition();

            Log.d(TAG, "updateProgress: "+position);
            if (mOnProgressChangeListener != null) {
                mOnProgressChangeListener.progress(position, duration);
            }
//            if (timeBar != null) {
//                int extraAdGroupCount = extraAdGroupTimesMs.length;
//                int totalAdGroupCount = adGroupCount + extraAdGroupCount;
//                if (totalAdGroupCount > adGroupTimesMs.length) {
//                    adGroupTimesMs = Arrays.copyOf(adGroupTimesMs, totalAdGroupCount);
//                    playedAdGroups = Arrays.copyOf(playedAdGroups, totalAdGroupCount);
//                }
//                System.arraycopy(extraAdGroupTimesMs, 0, adGroupTimesMs, adGroupCount, extraAdGroupCount);
//                System.arraycopy(extraPlayedAdGroups, 0, playedAdGroups, adGroupCount, extraAdGroupCount);
//                timeBar.setAdGroupTimesMs(adGroupTimesMs, playedAdGroups, totalAdGroupCount);
//            }
        }
//        if (durationView != null) {
//            durationView.setText(Util.getStringForTime(formatBuilder, formatter, duration));
//        }
//        if (positionView != null && !scrubbing) {
//            positionView.setText(Util.getStringForTime(formatBuilder, formatter, position));
//        }
//        if (timeBar != null) {
//            timeBar.setPosition(position);
//            timeBar.setBufferedPosition(bufferedPosition);
//            timeBar.setDuration(duration);
//        }

        // Cancel any pending updates and schedule a new one if necessary.
        handler.removeCallbacks(updateProgressAction);
        int playbackState = mSimpleExoPlayer == null ? Player.STATE_IDLE : mSimpleExoPlayer.getPlaybackState();
        if (playbackState != Player.STATE_IDLE && playbackState != Player.STATE_ENDED) {
            long delayMs;
            if (mSimpleExoPlayer.getPlayWhenReady() && playbackState == Player.STATE_READY) {
                float playbackSpeed = mSimpleExoPlayer.getPlaybackParameters().speed;
                if (playbackSpeed <= 0.1f) {
                    delayMs = 1000;
                } else if (playbackSpeed <= 5f) {
                    long mediaTimeUpdatePeriodMs = 1000 / Math.max(1, Math.round(1 / playbackSpeed));
                    long mediaTimeDelayMs = mediaTimeUpdatePeriodMs - (position % mediaTimeUpdatePeriodMs);
                    if (mediaTimeDelayMs < (mediaTimeUpdatePeriodMs / 5)) {
                        mediaTimeDelayMs += mediaTimeUpdatePeriodMs;
                    }
                    delayMs =
                            playbackSpeed == 1 ? mediaTimeDelayMs : (long) (mediaTimeDelayMs / playbackSpeed);
                } else {
                    delayMs = 200;
                }
            } else {
                delayMs = 1000;
            }
            handler.postDelayed(updateProgressAction, delayMs);
        }
    }

    public void setOnProgressChangeListener(@Nullable OnProgressChangeListener onProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener;
    }

    private OnProgressChangeListener mOnProgressChangeListener;
    public interface OnProgressChangeListener{

        void progress(long position, long duration);

        void onPlayStateChange(boolean playing);
    }
}
