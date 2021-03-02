package com.example.videoselectorapp.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.videoselectorapp.R;


/**
 * Custom Alert Dialog
 */
public class CustomAlertDialog {

    /**
     * Instantiates a new Custom alert dialog.
     *
     * @param builder the builder
     */
    public CustomAlertDialog(final Builder builder) {
    }

    /**
     * Dp to px int.
     *
     * @param context the context
     * @param dp      the dp
     * @return the int
     */
    private static int dpToPx(final Context context, final int dp) {
        return Math.round(dp * getPixelScaleFactor(context));
    }

    /**
     * Gets pixel scale factor.
     *
     * @param context the context
     * @return the pixel scale factor
     */
    private static float getPixelScaleFactor(final Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT;
    }

    /**
     * The interface Custom dialog interface.
     */
    public interface CustomDialogInterface {
        /**
         * The interface On click listener.
         */
        interface OnClickListener {
            /**
             * On click.
             */
            void onClick();
        }

        /**
         * The interface On dismiss listener.
         */
        interface OnDismissListener {
            /**
             * On dismiss.
             */
            void onDismiss();
        }

        /**
         * The interface On cancel listener.
         */
        interface OnCancelListener {
            /**
             * On cancel.
             */
            void onCancel();
        }
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        private final Context mContext;
        private CharSequence mTitle;
        private CharSequence mMessage;
        private CharSequence mPositiveButtonText;
        private CustomDialogInterface.OnClickListener mPositiveButtonListener;
        private CharSequence mNegativeButtonText;
        private CustomDialogInterface.OnClickListener mNegativeButtonListener;
        private boolean mCancelable;
        private boolean isCrossButtonVisible;
        private CustomDialogInterface.OnCancelListener mOnCancelListener;
        private CustomDialogInterface.OnDismissListener mOnDismissListener;
        private Dialog dialog;
        private View[] mHideViews;

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         */
        public Builder(@NonNull final Context context) {
            mContext = context;
            mCancelable = true;
        }

        /**
         * Set the title using the given resource id.
         *
         * @param titleId the title id
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@StringRes final int titleId) {
            mTitle = mContext.getText(titleId);
            return this;
        }

        /**
         * Set the title displayed in the {@link Dialog}.
         *
         * @param title the title
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@Nullable final CharSequence title) {
            mTitle = title;
            return this;
        }

        /**
         * Set the message to display using the given resource id.
         *
         * @param messageId the message id
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMessage(@StringRes final int messageId) {
            mMessage = mContext.getText(messageId);
            return this;
        }

        /**
         * Set the message to display.
         *
         * @param message the message
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMessage(@Nullable final CharSequence message) {
            mMessage = message;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         *
         * @param textId   The resource id of the text to display in the positive button
         * @param listener The {@link DialogInterface.OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButton(@StringRes final int textId, final CustomDialogInterface.OnClickListener listener) {
            mPositiveButtonText = mContext.getText(textId);
            mPositiveButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog is pressed.
         *
         * @param text     The text to display in the positive button
         * @param listener The {@link DialogInterface.OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButton(final CharSequence text, final CustomDialogInterface.OnClickListener listener) {
            mPositiveButtonText = text;
            mPositiveButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         *
         * @param textId   The resource id of the text to display in the negative button
         * @param listener The {@link DialogInterface.OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButton(@StringRes final int textId, final CustomDialogInterface.OnClickListener listener) {
            mNegativeButtonText = mContext.getText(textId);
            mNegativeButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog is pressed.
         *
         * @param text     The text to display in the negative button
         * @param listener The {@link DialogInterface.OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButton(final CharSequence text, final CustomDialogInterface.OnClickListener listener) {
            mNegativeButtonText = text;
            mNegativeButtonListener = listener;
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @param cancelable the cancelable
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(final boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        /**
         * Sets cross button visibility.
         *
         * @param crossButtonVisible the cross button visible
         * @return the cross button visibility
         */
        public Builder setCrossButtonVisibility(final boolean crossButtonVisible) {
            isCrossButtonVisible = crossButtonVisible;
            return this;
        }

        /**
         * Hide views builder.
         *
         * @param hideViews the hide views
         * @return the builder
         */
        public Builder hideViews(final View... hideViews) {
            mHideViews = hideViews;
            return this;
        }


        /**
         * Sets the callback that will be called if the dialog is canceled.
         * <p>
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * setOnDismissListener}*****.</p>
         *
         * @param onCancelListener the on cancel listener
         * @return This Builder object to allow for chaining of calls to set methods
         * @see #setCancelable(boolean) #setCancelable(boolean)#setCancelable(boolean)#setCancelable(boolean)
         */
        public Builder setOnCancelListener(final CustomDialogInterface.OnCancelListener onCancelListener) {
            mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @param onDismissListener the on dismiss listener
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(final CustomDialogInterface.OnDismissListener onDismissListener) {
            mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         *
         * @return the alert dialog
         */
        private Dialog create() {
            dialog = new Dialog(mContext, R.style.dialog_theme_custom_alert);
            dialog.setContentView(R.layout.dialog_custom_alert);
            final TextView tvTitle = dialog.findViewById(R.id.tvTitle);
            final TextView tvMessage = dialog.findViewById(R.id.tvMessage);
            final TextView btnNegative = dialog.findViewById(R.id.btnNegative);
            final TextView btnPositive = dialog.findViewById(R.id.btnPositive);

            final View viewLine = dialog.findViewById(R.id.viewLine);

            //set title
            if (mTitle != null) {
                tvTitle.setText(mTitle);
            } else {
                tvTitle.setVisibility(View.GONE);
            }
            if (mMessage != null) {
                tvMessage.setText(mMessage);
            }
            //set positive button
            if (mPositiveButtonText != null) {
                btnPositive.setText(mPositiveButtonText);
                btnPositive.setOnClickListener(v -> {
                    dialog.dismiss();
                    if (mPositiveButtonListener != null) {
                        mPositiveButtonListener.onClick();
                    }
                });
            } else {
                btnPositive.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
            }
            //set negative button
            if (mNegativeButtonText != null) {
                btnNegative.setText(mNegativeButtonText);
                btnNegative.setOnClickListener(v -> {
                    dialog.dismiss();
                    if (mNegativeButtonListener != null) {
                        mNegativeButtonListener.onClick();
                    }
                });
            } else {
                btnNegative.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
            }
            //set Cancelable
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCancelable);
            //set cancel listener
            if (mOnCancelListener != null) {
                dialog.setOnCancelListener(mDialog -> {
                    if (mOnCancelListener != null) {
                        mOnCancelListener.onCancel();
                    }
                });
            }

            dialog.setOnShowListener(mDialog -> {
                if (mHideViews != null) {
                    for (final View mView : mHideViews) {
                        mView.setVisibility(View.INVISIBLE);
                    }
                }
            });

            dialog.setOnDismissListener(mDialog -> {
                if (mHideViews != null) {
                    for (final View mView : mHideViews) {
                        mView.setVisibility(View.VISIBLE);
                    }
                }
                if (mOnDismissListener != null) {
                    mOnDismissListener.onDismiss();
                }
            });


            return dialog;
        }

        /**
         * Creates an {@link Dialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         *
         * @return the alert dialog
         */
        public Dialog show() {
            final Dialog mDialog = create();
            mDialog.show();
            return mDialog;
        }

        /**
         * Build custom alert dialog.
         *
         * @return the custom alert dialog
         */
        public CustomAlertDialog build() {
            return new CustomAlertDialog(this);
        }
    }
}

