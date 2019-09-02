package com.nuffwritten.flickr.repository.network;

import com.nuffwritten.flickr.model.SearchResultModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by navratan on 2019-09-03
 */

public interface FlickrApi {

    @GET("/services/rest/?method=flickr.photos.search")
    public Call<SearchResultModel> getPhotosFromTag(@Query("per_page") int perPage,
                                                    @Query("page") int page,
                                                    @Query("text") String text);

}
