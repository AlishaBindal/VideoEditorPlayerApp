package com.example.videoselectorapp.data.network;

import com.example.videoselectorapp.data.entity.CommonResponse;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.FetchMediaResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * The API interface for your application
 */
public interface ApiInterface {

    /**
     * get media list
     *
     * @return api call
     */
    @GET("media/")
    Call<FetchMediaResponse> getMediaList(@Query("profileId") String profileId);

    /**
     * upload video image file
     *
     * @return api call
     */
    @Multipart
    @POST("media/upload")
    Call<CommonResponse> uploadMedia(@HeaderMap Map<String, String> headermap, @PartMap HashMap<String, RequestBody> partMap);


}
