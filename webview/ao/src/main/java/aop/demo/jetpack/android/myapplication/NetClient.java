package aop.demo.jetpack.android.myapplication;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetClient {

    private static NetClient mNetclient = null;
    private final Retrofit mBuild;

    private NetClient(){

        HttpLoggingInterceptor interceptor =
                new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BASIC)
                        .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor).build();
        mBuild = new Retrofit
                .Builder()
//                .baseUrl("http://192.168.80.186:10055")
                .baseUrl(BuildConfig.API_HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    private static NetClient getInstance(){
        if (mNetclient == null){
            synchronized(NetClient.class){
                if(mNetclient == null){
                    mNetclient = new NetClient();
                }
            }
        }
        return mNetclient;
    }


    public  static  <T> T create(Class<T> tClass){
        return (NetClient.getInstance()).mBuild.create(tClass);
    }
}
