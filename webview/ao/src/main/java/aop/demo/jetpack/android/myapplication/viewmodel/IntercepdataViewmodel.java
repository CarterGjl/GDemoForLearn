package aop.demo.jetpack.android.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import aop.demo.jetpack.android.myapplication.Global;
import aop.demo.jetpack.android.myapplication.NetClient;
import aop.demo.jetpack.android.myapplication.storage.Config;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class IntercepdataViewmodel extends ViewModel implements LifecycleObserver {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<InterceptData> interceptLivedata = new MutableLiveData<>();

    private static final String TAG = "IntercepdataViewmodel";

    public MutableLiveData<InterceptData> data() {

        return interceptLivedata;
    }

    public MutableLiveData<InterceptData> getInterceptLivedata() {

        if (Global.isFirstUse){
            Disposable subscribe = NetClient
                    .create(InterceptSevice.class)
                    .getInterceptData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<InterceptData>() {
                        @Override
                        public void accept(InterceptData interceptData) throws Exception {

                            Log.d(TAG, "accept: " + interceptData);
                            if (interceptData == null|| interceptData.getData()==null){
                                return;
                            }
                            interceptLivedata.setValue(interceptData);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            Log.e(TAG, "accept: ", throwable);
                        }
                    });
            mCompositeDisposable.add(subscribe);
            Global.isFirstUse = false;
        }

        return interceptLivedata;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mCompositeDisposable.clear();
    }
}
