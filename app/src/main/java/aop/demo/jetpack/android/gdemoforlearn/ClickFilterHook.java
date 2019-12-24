package aop.demo.jetpack.android.gdemoforlearn;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ClickFilterHook {

    private long lastClick;
    private static final Long FILTER_TIMEM = 1000L;
    @Around(value = "execution(* android.view.View.OnClickListener.onClick(..))")
    public void clickFilterHook(ProceedingJoinPoint joinPoint){
        if (System.currentTimeMillis() - lastClick>=FILTER_TIMEM){
            lastClick = System.currentTimeMillis();
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                Log.e("ClickFilterHook", "重复点击,已过滤");
            }
        }
    }
}
