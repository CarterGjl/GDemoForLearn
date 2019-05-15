package aop.demo.jetpack.android.exoplayer;

import android.content.Context;

public interface ExoPlayControyInterface {

    void init(Context context);

    void play(String url);

    void stop();

    void seekTo(long position);

}
