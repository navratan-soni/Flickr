package com.nuffwritten.flickr.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nuffwritten.flickr.R;

/**
 * Created by navratan on 2019-09-03
 */

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        if (savedInstanceState == null) {
           addFragment();
        }
    }

    public void addFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, SearchResultFragment.newInstance())
                .commitNow();
    }
}
