package aop.demo.jetpack.android.gdemoforlearn.base.inteface;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface IPresenter<V extends IView> extends LifecycleObserver {

    /**
     * 创建view时调用  调用在initView 之后
     * @param view
     */
    void attachView(V view);

    /**
     * view销毁时调用释放资源
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void detachView();

    Context getContext();
}
