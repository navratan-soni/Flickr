package com.nuffwritten.flickr.model;

import java.util.List;

/**
 * Created by navratan on 2019-09-03
 */
public class AdapterItemsModel {
    int pages;
    int currentPage;
    List<String> imageUrls;

    public AdapterItemsModel(int pages, int currentPage, List<String> imageUrls) {
        this.pages = pages;
        this.currentPage = currentPage;
        this.imageUrls = imageUrls;
    }

    public int getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
}
