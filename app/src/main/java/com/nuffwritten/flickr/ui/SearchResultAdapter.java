package com.nuffwritten.flickr.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.nuffwritten.flickr.R;

import java.util.List;

/**
 * Created by navratan on 2019-09-03
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ItemsViewHolder> {

    private Context mCtx;
    private List<String> imagesList;

    public SearchResultAdapter(Context context, List<String> imagesList) {
        this.mCtx = context;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.image_item, viewGroup, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int pos) {
        Glide.with(mCtx)
                .load(imagesList.get(pos))
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(0.1f)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    static class ItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
