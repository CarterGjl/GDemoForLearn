package aop.demo.jetpack.android.exoplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSinkFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

import aop.demo.jetpack.android.exoplayer.playmanager.ExoPlayManager;

public class MainActivity extends AppCompatActivity {

    private TextView mTvCurPro;
    private SeekBar mPb;


    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTvCurPro = findViewById(R.id.tv_cur_pro);
        mPb = findViewById(R.id.pb);

        DefaultControlDispatcher defaultControlDispatcher = new DefaultControlDispatcher();
        SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());

      /*  DefaultDataSourceFactory upstreamFactory = new DefaultDataSourceFactory(this, "audio/mpeg");

        File mediaCacheFile = CachesUtil.getMediaCacheFile(
                CachesUtil.VIDEO
        );
        SimpleCache simpleCache = new SimpleCache(mediaCacheFile, new NoOpCacheEvictor());
        CacheDataSinkFactory cacheDataSinkFactory = new CacheDataSinkFactory(simpleCache, Long.MAX_VALUE);
        CacheDataSourceFactory cacheDataSourceFactory = new CacheDataSourceFactory(simpleCache, upstreamFactory, new FileDataSourceFactory(),
                cacheDataSinkFactory,
                CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
                null);
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();

//        concatenatingMediaSource.addMediaSource();



        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(Uri.parse("http://5.595818" +
                        ".com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3"));*/

//        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(this, "audio/mpeg");
//        DefaultExtractorsFactory defaultExtractorsFactory = new DefaultExtractorsFactory();
//
//        String proxyUrl = DemoApplication.getProxy(this).getProxyUrl("http://5.595818" +
//                ".com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3", true);
//        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource(Uri.parse(proxyUrl), defaultDataSourceFactory,
//                defaultExtractorsFactory, null, null);
//        simpleExoPlayer.prepare(extractorMediaSource);
//        simpleExoPlayer.addListener(new Player.EventListener() {
//            @Override
//            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
//                Log.d(TAG, "onTimelineChanged: "+timeline.toString());
//            }
//
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//            }
//
//            @Override
//            public void onLoadingChanged(boolean isLoading) {
//
//            }
//
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//            }
//
//            @Override
//            public void onRepeatModeChanged(int repeatMode) {
//
//            }
//
//            @Override
//            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
//
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//
//            }
//
//            @Override
//            public void onPositionDiscontinuity(int reason) {
//
//                Log.d(TAG, "onPositionDiscontinuity: "+reason);
//            }
//
//            @Override
//            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//
//            }
//
//            @Override
//            public void onSeekProcessed() {
//
//            }
//        });


        ExoPlayManager exoPlayManager = ExoPlayManager.create(this);
        exoPlayManager.play("");
        exoPlayManager.setOnProgressChangeListener(new ExoPlayManager.OnProgressChangeListener() {
            @Override
            public void progress(long position, long duration) {
                mPb.setMax((int) duration);
                mPb.setProgress((int) position);
            }

            @Override
            public void onPlayStateChange(boolean playing) {

            }
        });
        mPb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                exoPlayManager.seekTo(seekBar.getProgress());
            }
        });

//        simpleExoPlayer.setPlayWhenReady(true);
        try {
            DownloadService.start(this, DemoDownloadService.class);
        } catch (IllegalStateException e) {
            DownloadService.startForeground(this, DemoDownloadService.class);
        }
    }
}
