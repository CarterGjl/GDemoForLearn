package aop.demo.jetpack.android.gdemoforlearn.base.inteface;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

public interface IView extends LifecycleOwner {


    Context getContext();

    /**
     * 网络请求错误,弹框提示
     * @param msg
     * @param code
     */
    void showError(String msg, String code);

    /**
     * 显示Dialog
     */
    void showHUD(String msg);

    /**
     * 关闭Dialog
     */
    void dismissHUD();
}
