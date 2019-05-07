package aop.demo.jetpack.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposables;

public class RxBroadcastReceiver implements ObservableOnSubscribe<Intent>{

    private final WeakReference<Context> mContextWeakReference;
    private final IntentFilter mFilter;
    private BroadcastReceiver mBroadcastReceiver;
    private ObservableEmitter<? super Intent> mEmitter;

    private static final String TAG = "RxBroadcastReceiver";
    @Override
    public void subscribe(ObservableEmitter<Intent> emitter) throws Exception {

        mEmitter = emitter;
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                abortBroadcast();
                mEmitter.onNext(intent);
            }
        };
        if (mContextWeakReference != null && mContextWeakReference.get() != null) {

            mContextWeakReference.get().registerReceiver(mBroadcastReceiver, mFilter);
            Log.d(TAG, "registerReceiver: ");

        }
        mEmitter.setDisposable(Disposables.fromRunnable(() -> {
            if (mContextWeakReference != null && mContextWeakReference.get() != null) {

                mContextWeakReference.get().unregisterReceiver(mBroadcastReceiver);

            }

        }));
    }


    private RxBroadcastReceiver(@NonNull Context context, @NonNull IntentFilter filter) {

        mContextWeakReference = new WeakReference<>(context.getApplicationContext());
        mFilter = filter;


    }


    public static Observable<Intent> create(Context context, IntentFilter filter) {

        return Observable.defer(() -> {

            RxBroadcastReceiver rxBroadcastReceiver = new RxBroadcastReceiver(context, filter);
            return Observable.create(rxBroadcastReceiver);
        });
    }
}
