package com.nuffwritten.flickr.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nuffwritten.flickr.R;
import com.nuffwritten.flickr.model.AdapterItemsModel;
import com.nuffwritten.flickr.ui.util.CustomDialogUtil;
import com.nuffwritten.flickr.ui.util.SpacingItemDecoration;
import com.nuffwritten.flickr.util.DebounceQueryListener;
import com.nuffwritten.flickr.util.NumberChangedListener;
import com.nuffwritten.flickr.viewmodel.SearchResultViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by navratan on 2019-09-03
 */

public class SearchResultFragment extends Fragment implements NumberChangedListener {

    private SearchResultViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private SearchResultAdapter mAdapter;
    private List<String> resultList;
    private static final int DEFAULT_GRID_ELEMENTS = 2;
    private Button mBtnLoadMore;
    private TextView mNoItemsMsg;
    private SearchView searchView;
    private TextView mCurrentColumnTextView;
    private ProgressBar mProgressBar;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        SpacingItemDecoration decoration = new SpacingItemDecoration(10, SpacingItemDecoration.GRID);
        mRecyclerView.addItemDecoration(decoration);
        resultList = new ArrayList<>();
        mAdapter = new SearchResultAdapter(getActivity(), resultList);
        mBtnLoadMore = view.findViewById(R.id.loadMoreBtn);
        mBtnLoadMore.setOnClickListener(v -> fetchMoreData());
        mNoItemsMsg = view.findViewById(R.id.message);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(getQuesryListener());
        mCurrentColumnTextView = view.findViewById(R.id.current_column);
        mCurrentColumnTextView.setText(String.valueOf(DEFAULT_GRID_ELEMENTS));
        mCurrentColumnTextView.setOnClickListener(v -> showNumberChangeDialog());
        mProgressBar = view.findViewById(R.id.progress_circular);
        resetAdapter(DEFAULT_GRID_ELEMENTS);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchResultViewModel.class);
        mViewModel.getLiveData().observe(getViewLifecycleOwner(), itemsModel -> updateUi(itemsModel));
    }

    public void updateUi(AdapterItemsModel itemsModel) {
        mProgressBar.setVisibility(View.GONE);
        List<String> items = itemsModel.getImageUrls();
        if (items == null || items.size() == 0) {
            resultList.clear();
            mAdapter.notifyDataSetChanged();
        } else {
            if(itemsModel.getCurrentPage() == 1) {
                resultList.clear();
                resultList.addAll(items);
                mAdapter.notifyDataSetChanged();
            } else {
                resultList.addAll(items);
                mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), items.size());
            }
        }
        if(mAdapter.getItemCount() > 0) {
            mNoItemsMsg.setVisibility(View.GONE);
            mBtnLoadMore.setVisibility(itemsModel.getCurrentPage() < itemsModel.getPages() ? View.VISIBLE : View.GONE);
        } else {
            mNoItemsMsg.setVisibility(View.VISIBLE);
            mBtnLoadMore.setVisibility(View.GONE);
        }
    }

    public void resetAdapter(int numOfItems) {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), numOfItems);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public DebounceQueryListener getQuesryListener() {
        return new DebounceQueryListener() {
            @Override
            public void onQueryTextChanged(String searchText) {
                fetchData(searchText);
            }
        };
    }

    public void fetchData(String searchText) {
        if(TextUtils.isEmpty(searchText)) {
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mNoItemsMsg.setVisibility(View.GONE);
        mBtnLoadMore.setVisibility(View.GONE);
        mViewModel.fetch(searchText);
    }

    public void fetchMoreData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mBtnLoadMore.setVisibility(View.GONE);
        mViewModel.fetchMore();
    }

    public void showNumberChangeDialog() {
        CustomDialogUtil.showNumberChangedDialog(getActivity(), Integer.valueOf(mCurrentColumnTextView.getText().toString()), this);
    }

    @Override
    public void onNumberChaged(int items) {
        resetAdapter(items);
        mCurrentColumnTextView.setText(String.valueOf(items));
    }
}
