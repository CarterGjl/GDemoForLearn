package aop.demo.jetpack.android.gdemoforlearn.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import aop.demo.jetpack.android.gdemoforlearn.base.inteface.IPresenter;
import aop.demo.jetpack.android.gdemoforlearn.base.inteface.IView;
import aop.demo.jetpack.android.gdemoforlearn.base.inteface.MvpCallBack;

public abstract class BaseVpActivity<V extends IView,P extends IPresenter<V>> extends AppCompatActivity implements MvpCallBack<V,P>,IView {


    private BaseVpActivity<V, P> mActivity;
    private V mView;
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        initActionBar();
        mActivity = this;
        onViewCreated();
        initTitle();
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        setPresenter(null);
        setMvpView(null);

    }

    protected abstract void initActionBar();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract void initTitle();

    protected void onViewCreated(){
        mView = createView();
        if (getPresenter() == null) {

            mPresenter = createPresenter();
            getLifecycle().addObserver(mPresenter);
        }
        mPresenter = getPresenter();
        mPresenter.attachView(getMvpView());
    }

    @LayoutRes
    protected abstract int getLayoutID();

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String msg, String code) {

    }

    @Override
    public void showHUD(String msg) {

    }

    @Override
    public void dismissHUD() {

    }





    @Override
    public void setPresenter(P presenter) {

    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setMvpView(V view) {

    }

    @Override
    public V getMvpView() {
        return mView;
    }
}
