package com.example.yangdianwen.retrofithttp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yangdianwen on 16-6-24.
 */
public interface GetService {
    //http://gank.io/api/data/Android/10/1
    @GET("api/data/Android/10/1")
    Call<ResponseBody> getJson();


}
