package aop.demo.jetpack.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Runtime {


    private MainActivity mActivityHashMap;
    private static Runtime mRuntime = null;


    public MainActivity getActivityHashMap() {

        return mActivityHashMap;
    }

    public void setActivityHashMap(MainActivity activityHashMap) {
        mActivityHashMap = activityHashMap;
    }

    private Runtime() {
    }

    public static Runtime getInstance() {
        if (mRuntime == null) {
            synchronized (Runtime.class) {
                if (mRuntime == null) {
                    mRuntime = new Runtime();
                }
            }
        }
        return mRuntime;
    }
}
