package com.nuffwritten.flickr.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.nuffwritten.flickr.repository.FlickrRepository;

import java.util.List;

/**
 * Created by navratan on 2019-09-03
 */

public class SearchResultViewModel extends ViewModel {

    private int pageNumber = 0;
    private FlickrRepository repository;
    private String previousSearchedString = "";
    private MutableLiveData<List<String>> liveData;

    public SearchResultViewModel() {
        repository = FlickrRepository.getInstance();
        liveData = new MutableLiveData<>();
    }

    public void fetch(String currentSearchString) {

        if(TextUtils.isEmpty(currentSearchString))
            return;

        if(previousSearchedString.equals(currentSearchString)) {
            pageNumber++;
        } else {
            pageNumber = 0;
            previousSearchedString = currentSearchString;
        }
        repository.fetchPhotos(pageNumber, currentSearchString, liveData);
    }

    public void fetchMore() {
        fetch(previousSearchedString);
    }

    public MutableLiveData<List<String>> getLiveData() {
        return liveData;
    }

}
