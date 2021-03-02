package com.example.videoselectorapp.base.presenter;


import com.example.videoselectorapp.base.view.BaseView;

/**
 * Base Presenter
 */
public interface BasePresenter {

    /**
     * On attach.
     *
     * @param view the view
     */
    void onAttach(BaseView view);

    /**
     * On detach.
     */
    void onDetach();

    /**
     * Delete local db.
     */
    void deleteLocalDb();
}
