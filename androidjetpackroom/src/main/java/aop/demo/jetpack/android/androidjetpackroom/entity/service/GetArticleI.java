package aop.demo.jetpack.android.androidjetpackroom.entity.service;

import aop.demo.jetpack.android.androidjetpackroom.entity.data.Result;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetArticleI {

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<Result<Object>> getArticle(@Path("id") int id, @Path("page") int page);

    @GET("wxarticle/chapters/json")
    Observable<Result<Object>> getWx();
}
