package com.nuffwritten.flickr.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.SearchView;

/**
 * Created by navratan on 2019-09-03
 */

public abstract class DebounceQueryListener implements SearchView.OnQueryTextListener {

    private Handler handler;
    private static final int MSG_DO_SEARCH = 9898;
    private static final long MSG_DELAY = 750L;

    protected DebounceQueryListener() {
        handler = new Handler(Looper.getMainLooper(), msg -> {
            onQueryTextChanged(msg.obj.toString());
            return true;
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        handler.removeMessages(MSG_DO_SEARCH);
        handler.sendMessageDelayed(getDelayedMessage(s), MSG_DELAY);
        return true;
    }

    private Message getDelayedMessage(String searchText) {
        return handler.obtainMessage(MSG_DO_SEARCH, searchText);
    }

    public abstract void onQueryTextChanged(String text);
}
