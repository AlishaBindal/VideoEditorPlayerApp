package com.example.videoselectorapp.base.view;

/**
 * FragmentToActivityInteractionListener
 */
public interface FragmentToActivityInteractionListener extends BaseView {
    /**
     * Clear back stack.
     */
    void clearBackStack();

    /**
     * Back press.
     */
    void backPress();

    /**
     * Clear back stack.
     *
     * @param backStackTag the back stack tag
     */
    void clearBackStack(String backStackTag);

    /**
     * Replace or add fragment.
     *
     * @param tag    the tag
     * @param isAdd  the is add
     * @param object the object
     */
    void showFragment(String tag, boolean isAdd, Object... object);

    /**
     * Sets result ok and close activity.
     */
    void setResultOkAndCloseActivity();
}
