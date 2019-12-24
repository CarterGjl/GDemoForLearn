package aop.demo.jetpack.android.myapplication.storage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

public class Json implements IJson {

    private Gson mGson = new Gson();

    private static Json mJson = null;
    private Json(){}
    public static Json getInstance(){
        synchronized(Json.class){
            if(mJson == null){
                mJson = new Json();
            }
        }
        return mJson;
    }
    @Override
    public <T> T fromJson(JsonElement json, Class<T> classOfT) {
        return mGson.fromJson(json,classOfT);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {

        return mGson.fromJson(json,classOfT);
    }

    @Override
    public <T> T fromJson(String json, Type typeOfT) {
        return mGson.fromJson(json,typeOfT);
    }

    @Override
    public <T> String toJson(T t) {
        return mGson.toJson(t);
    }
}
