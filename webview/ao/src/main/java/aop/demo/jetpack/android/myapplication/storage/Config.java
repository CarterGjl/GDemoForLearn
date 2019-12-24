package aop.demo.jetpack.android.myapplication.storage;

import android.content.Context;
import android.content.SharedPreferences;

import aop.demo.jetpack.android.myapplication.viewmodel.InterceptData;

public class Config {
    private String FILE_NAME = "config";
    private static Config mConfig = null;
    private final SharedPreferences mSharedPreferences;

    private Config(Context context) {
        mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public void saveInterceptData(Object o) {
        String json = Json.getInstance().toJson(o);
        boolean intercept_data = mSharedPreferences.edit().putString("intercept_data", json).commit();
    }

    public InterceptData getIntercepData() {
        String intercept_data = mSharedPreferences.getString("intercept_data", null);
        if (intercept_data == null) {
            return null;
        } else {
            return Json.getInstance().fromJson(intercept_data, InterceptData.class);
        }
    }

    public static Config getInstance(Context context) {
        if (mConfig == null) {
            synchronized (Config.class) {
                if (mConfig == null) {
                    mConfig = new Config(context);
                }
            }
        }
        return mConfig;
    }
}
