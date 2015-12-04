package com.giffedup.api.service;


import com.giffedup.api.models.ApiResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by vinay-r on 8/10/15.
 */
public interface GifService {

    @GET("/v1/gifs/trending")
    void getTrendingGIFs(Callback<ApiResponse> callback);

    @GET("/v1/gifs/search")
    void searchGIFs(@Query("q") String query, Callback<ApiResponse> callback);

    @GET("/v1/gifs/search")
    void searchGIFs(@Query("q") String query, @Query("offset")int offset, Callback<ApiResponse> callback);

    @GET("/v1/stickers/trending")
    void getTrendingStickers(Callback<ApiResponse> callback);

    @GET("/v1/stickers/search")
    void searchStickers(@Query("q") String query, Callback<ApiResponse> callback);

    void searchStickers(@Query("q") String query, @Query("offset") int offset, Callback<ApiResponse> callback);

}
