package com.nuffwritten.flickr.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;

import com.nuffwritten.flickr.model.AdapterItemsModel;
import com.nuffwritten.flickr.repository.FlickrRepository;

import java.util.List;

/**
 * Created by navratan on 2019-09-03
 */

public class SearchResultViewModel extends ViewModel {

    private int pageNumber = 1;
    private FlickrRepository repository;
    private String previousSearchedString = "";
    private MutableLiveData<AdapterItemsModel> liveData;

    public SearchResultViewModel() {
        repository = FlickrRepository.getInstance();
        liveData = new MutableLiveData<>();
    }

    public void fetch(String currentSearchString) {

        if(previousSearchedString.equals(currentSearchString)) {
            pageNumber++;
        } else {
            pageNumber = 1;
            previousSearchedString = currentSearchString;
        }
        repository.fetchPhotos(pageNumber, currentSearchString, liveData);
    }

    public void fetchMore() {
        fetch(previousSearchedString);
    }

    public MutableLiveData<AdapterItemsModel> getLiveData() {
        return liveData;
    }

}
