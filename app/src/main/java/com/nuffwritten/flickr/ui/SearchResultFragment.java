package com.nuffwritten.flickr.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nuffwritten.flickr.R;
import com.nuffwritten.flickr.ui.util.SpacingItemDecoration;
import com.nuffwritten.flickr.viewmodel.SearchResultViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by navratan on 2019-09-03
 */

public class SearchResultFragment extends Fragment {

    private SearchResultViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private SearchResultAdapter mAdapter;
    private List<String> resultList;
    private static final int DEFAULT_GRID_ELEMENTS = 2;
    private Button mBtnLoadMore;
    private TextView mNoItemsMsg;

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
        SpacingItemDecoration decoration = new SpacingItemDecoration(5, SpacingItemDecoration.GRID);
        mRecyclerView.addItemDecoration(decoration);
        resultList = new ArrayList<>();
        mAdapter = new SearchResultAdapter(getActivity(), resultList);
        mBtnLoadMore = view.findViewById(R.id.loadMoreBtn);
        mBtnLoadMore.setOnClickListener(getClickListener());
        mNoItemsMsg = view.findViewById(R.id.message);
        resetAdapter(DEFAULT_GRID_ELEMENTS);
    }

    private View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.fetchMore();
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchResultViewModel.class);
        mViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> list) {
                updateUi(list);
            }
        });

    }

    public void updateUi(List<String> items) {
        if (items == null || items.size() == 0) {
            resultList.clear();
            mAdapter.notifyDataSetChanged();
        } else {
            resultList.addAll(items);
            mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), items.size());
        }
        mBtnLoadMore.setVisibility(mAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
        mNoItemsMsg.setVisibility(mAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }

    public void resetAdapter(int numOfItems) {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), numOfItems);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
