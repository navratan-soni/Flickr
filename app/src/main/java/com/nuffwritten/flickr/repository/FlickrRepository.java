package com.nuffwritten.flickr.repository;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.nuffwritten.flickr.model.PhotoModel;
import com.nuffwritten.flickr.model.SearchResultModel;
import com.nuffwritten.flickr.repository.network.FlickrApi;
import com.nuffwritten.flickr.repository.network.FlickrService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by navratan on 2019-09-03
 */
public class FlickrRepository {

    private static final int PAGE_LIMIT = 20;

    private static volatile FlickrRepository mInstance;

    FlickrApi flickrApi;

    private FlickrRepository() {
        flickrApi = new FlickrService().getFlickrApi();
    }

    public static FlickrRepository getInstance() {
        if (mInstance == null) {
            synchronized (FlickrRepository.class) {
                if (mInstance == null) {
                    mInstance = new FlickrRepository();
                }
            }
        }
        return mInstance;
    }

    public void fetchPhotos(int pageNumebr, String searchTag, final MutableLiveData<List<String>> liveData) {
        Call<SearchResultModel> call =
                 flickrApi.getPhotosFromTag(PAGE_LIMIT, pageNumebr, searchTag);

        call.enqueue(new Callback<SearchResultModel>() {
            @Override
            public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                 if(response.isSuccessful()) {
                     liveData.setValue(getMappedList(response.body()));
                 }
            }
            @Override
            public void onFailure(Call<SearchResultModel> call, Throwable t) {
                     liveData.setValue(null);
            }
        });
    }

    private List<String> getMappedList(@Nullable SearchResultModel resultModel) {
        if(resultModel == null) {
            return null;
        }
        List<String> allImagesUrl = new ArrayList<>();
        for(PhotoModel photo : resultModel.getPhotos().getPhoto()) {
            allImagesUrl.add(photo.getImageUrl());
        }
        return allImagesUrl;
    }
}
