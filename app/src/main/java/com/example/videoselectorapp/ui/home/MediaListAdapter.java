package com.example.videoselectorapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videoselectorapp.R;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.FetchMediaResponse;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.Item;

import java.util.ArrayList;

/**
 * MediaListAdapter
 */
public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.ViewHolder> {
    private final ArrayList<Item> mediaList;
    private final MediaClickAdapterCallback mListener;

    /**
     * Instantiates a new Adapter
     *
     * @param list the list
     */
    public MediaListAdapter(final ArrayList<Item> list, final MediaClickAdapterCallback callback) {
        this.mediaList = list;
        mListener = callback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_media, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int pos) {
        final int position = holder.getAdapterPosition();
        Item mediaItemData = mediaList.get(position);
        Context context = holder.itemView.getContext();
        if (mediaItemData.getIs().equalsIgnoreCase(FetchMediaResponse.IS_IMAGE)) {
            holder.ivPlayVideo.setVisibility(View.GONE);
            Glide.with(context).load(mediaItemData.getUrl()).into(holder.ivThumb);
        } else {
            holder.ivPlayVideo.setVisibility(View.VISIBLE);
            Glide.with(context).load(mediaItemData.getThumb().getUrl()).into(holder.ivThumb);
        }
        holder.tvContentType.setText(mediaItemData.getContentType());
        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onClickMediaItem(mediaItemData);
            }
        });
    }

    @Override
    public int getItemCount() {
        assert mediaList != null;
        return mediaList.size();
    }

    /**
     * The type View holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivThumb, ivPlayVideo;
        private final TextView tvContentType;

        /**
         * Instantiates a new View holder.
         *
         * @param v the v
         */
        ViewHolder(final View v) {
            super(v);
            ivThumb = v.findViewById(R.id.ivThumb);
            ivPlayVideo = v.findViewById(R.id.ivPlayVideo);
            tvContentType = v.findViewById(R.id.tvContentType);
        }
    }

    /**
     * MediaClickAdapterCallback
     */
    public interface MediaClickAdapterCallback {
        /**
         * onClickMediaItem
         *
         * @param mediaItemData mediaItemData
         */
        void onClickMediaItem(Item mediaItemData);
    }

}
