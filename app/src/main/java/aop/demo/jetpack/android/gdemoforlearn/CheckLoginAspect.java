package aop.demo.jetpack.android.gdemoforlearn;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CheckLoginAspect {
    private static final String TAG = "CheckLoginAspect";
//    @Pointcut(value = "execution(@aop.demo.jetpack.android.myapplication.CheckLogin * *(..))")
    @Pointcut("execution( * aop.demo.jetpack.android.gdemoforlearn.CheckLogin * *（..))")
    public void methodAnnotatedWithCheckLogin(){

    }

    @Around(value = "methodAnnotatedWithCheckLogin()")
    public void checkLogin(final ProceedingJoinPoint joinPoint) throws Throwable{
        Log.d(TAG, "checkLogin: ");
        joinPoint.proceed();
    }
}
