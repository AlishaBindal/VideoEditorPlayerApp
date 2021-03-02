package com.example.videoselectorapp.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoselectorapp.R;
import com.example.videoselectorapp.base.view.BaseActivity;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.FetchMediaResponse;
import com.example.videoselectorapp.data.entity.fetchMediaResponse.Item;
import com.example.videoselectorapp.multipicker.MultiPickerActivity;
import com.example.videoselectorapp.videoView.TrimmerActivity;
import com.example.videoselectorapp.videoView.ViewVideoActivity;
import com.example.videoselectorapp.viewImage.ViewImageActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.kbeanie.multipicker.api.entity.ChosenVideo;

import java.io.File;
import java.util.ArrayList;

import static com.example.videoselectorapp.viewImage.ViewImageActivity.RECTANGULAR;

/**
 * HomeActivity
 */
public class HomeActivity extends BaseActivity implements HomeView, MediaListAdapter.MediaClickAdapterCallback {
    private static final int REQUEST_VIDEO_TRIMMER = 111;

    public static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
    private ShimmerFrameLayout shimmerFrameLayout;
    private HomePresenter presenter;
    private RecyclerView rvMediaList;
    private String imageThumb = "";

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        presenter = new HomePresenterImpl(getDeviceName(), this);
        presenter.onAttach(this);

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        setUpRecyclerView();
        presenter.fetchMediaListFromServer();
        findViewById(R.id.fabRecordWithCamera).setOnClickListener(view -> {
            onClickCamera();
        });
        findViewById(R.id.fabAddFromGallery).setOnClickListener(view -> {
            onClickGallery();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchMediaListFromServer();
    }

    private void setUpRecyclerView() {
        rvMediaList = findViewById(R.id.rvMediaList);
        rvMediaList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
    }


    private void onClickCamera() {
        MultiPickerActivity.openActivity(this, MultiPickerActivity.TYPE_VIDEO_DIRECTLY, true, 1);
    }


    private void onClickGallery() {
        MultiPickerActivity.openActivity(this, MultiPickerActivity.TYPE_VIDEO, false, 1);
    }

    @Override
    public void showShimmerLoading() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideShimmerLoading() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onFetchMediaApiSuccess(FetchMediaResponse mediaResponse) {
        if (mediaResponse.getData().getItems() != null && !mediaResponse.getData().getItems().isEmpty()) {
            MediaListAdapter mediaListAdapter = new MediaListAdapter(mediaResponse.getData().getItems(), this);
            rvMediaList.setAdapter(mediaListAdapter);
        }
    }

    @Override
    public void onUploadMedia(File videoFile) {
        ViewVideoActivity.openActivity(this, videoFile.getPath());
    }

    @Override
    public void onClickMediaItem(Item mediaItemData) {
        if (mediaItemData.getIs().equalsIgnoreCase(FetchMediaResponse.IS_IMAGE)) {
            ViewImageActivity.openActivity(this, mediaItemData.getUrl(), RECTANGULAR);
        } else {
            ViewVideoActivity.openActivity(this, mediaItemData.getUrl());
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == MultiPickerActivity.REQ_CODE_CHOOSE_VIDEO) {
            assert data != null;
            ArrayList<ChosenVideo> videoArrayList = data.getParcelableArrayListExtra(MultiPickerActivity.EXTRA_CHOSEN_VIDEOS);
            imageThumb = setVideoPreview(videoArrayList.get(0));
            trimVideo(videoArrayList.get(0).getOriginalPath());
        }

        if (requestCode == REQUEST_VIDEO_TRIMMER) {
            assert data != null;
            if (data.hasExtra(EXTRA_VIDEO_PATH)) {
                Log.v("uri", data.getStringExtra(EXTRA_VIDEO_PATH));
                String videoPath = data.getStringExtra(EXTRA_VIDEO_PATH);
                File videoFile = new File(videoPath);
                presenter.uploadMediaToServer(videoFile, imageThumb);
            }
        }
    }

    private void trimVideo(String originalPath) {
        Intent intent = new Intent(this, TrimmerActivity.class);
        intent.putExtra(EXTRA_VIDEO_PATH, originalPath);
        startActivityForResult(intent, REQUEST_VIDEO_TRIMMER);
    }


    private String setVideoPreview(final ChosenVideo chosenVideo) {
        return chosenVideo.getOriginalPath();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}