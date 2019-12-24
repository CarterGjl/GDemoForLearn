package aop.demo.jetpack.android.myapplication.viewmodel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface InterceptSevice {

    @GET("/rest/otstudent/mobile/notforward/url")
    Observable<InterceptData> getInterceptData();
}

