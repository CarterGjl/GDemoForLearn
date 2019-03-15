package aop.demo.jetpack.android.gdemoforlearn.base.inteface;

public interface MvpCallBack<V extends IView,P extends IPresenter<V>> {

    //创建Presenter  调用在init中
    P createPresenter();

    //创建View
    V createView();

    void setPresenter(P presenter);

    P getPresenter();

    void setMvpView(V view);

    V getMvpView();
}
