package aop.demo.jetpack.android.gdemoforlearn.base;

import android.content.Context;

import androidx.annotation.CallSuper;
import aop.demo.jetpack.android.gdemoforlearn.base.inteface.IModel;
import aop.demo.jetpack.android.gdemoforlearn.base.inteface.IView;

public abstract class BasePresenter<V extends IView,M extends IModel> {

    protected V mView;
    protected M mModel;

    //管理事件流订阅的生命周期CompositeDisposable
//    private CompositeDisposable compositeDisposable;

    @CallSuper
    public void attachView(V view) {
        this.mView = view;
        if (mModel == null) {
            mModel = createModel();
        }
    }

    @CallSuper
    public void detachView() {
        if (mModel != null) {
//            clearPool();
        }
        mModel = null;
        mView = null;
    }
    public Context getContext() {
        return mView.getContext();
    }

    /**
     * rxjava管理订阅者
     */
//    protected void addDisposable(Disposable disposable) {
//        if (compositeDisposable == null) {
//            compositeDisposable = new CompositeDisposable();
//        }
//        compositeDisposable.add(disposable);
//    }


    /**
     * 取消订阅关系
     * @return
     */
//    public void clearPool() {
//        if (compositeDisposable != null) {
//            compositeDisposable.clear();
//            compositeDisposable = null;
//        }
//    }
    protected abstract M createModel();
}
