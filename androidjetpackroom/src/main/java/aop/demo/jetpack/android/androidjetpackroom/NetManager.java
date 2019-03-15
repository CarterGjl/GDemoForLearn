package aop.demo.jetpack.android.androidjetpackroom;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {

    private static NetManager mNetManager = null;
    private final Retrofit mRetrofit;

    private NetManager(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ConstantValue.KEY_API_HOST)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    private static NetManager getInstance(){
        if (mNetManager == null){
            synchronized(NetManager.class){
                if(mNetManager == null){
                    mNetManager = new NetManager();
                }
            }
        }
        return mNetManager;
    }


    @NonNull
    public static <T> T create(Class<T> service){
        return getInstance().mRetrofit.create(service);
    }
}
