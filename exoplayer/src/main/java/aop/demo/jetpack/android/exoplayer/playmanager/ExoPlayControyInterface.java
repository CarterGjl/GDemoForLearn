package aop.demo.jetpack.android.exoplayer.playmanager;

import android.content.Context;

public interface ExoPlayControyInterface {

    void init(Context context);

    void play(String url);

    void stop();

    void seekTo(long position);

}
