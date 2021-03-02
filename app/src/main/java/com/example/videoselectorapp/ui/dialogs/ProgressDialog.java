package com.example.videoselectorapp.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.videoselectorapp.R;

/**
 * Progress Dialog
 */
public class ProgressDialog {

    /**
     * Instantiates a new Progress dialog.
     */
    public ProgressDialog() {
    }

    /**
     * Show progress dialog.
     *
     * @param context the context
     * @return the dialog
     */
    public Dialog show(final Context context) {
        return show(context, null);
    }

    /**
     * Show progress dialog.
     *
     * @param context the context
     * @param message the message
     * @return the dialog
     */
    public Dialog show(final Context context, final int message) {
        return show(context, context.getString(message));
    }

    /**
     * Show progress dialog.
     *
     * @param context the context
     * @param message the message
     * @return the dialog
     */
    public Dialog show(final Context context, final String message) {
        Dialog progressDialog = new Dialog(context, R.style.dialog_theme_progress);
        progressDialog.setContentView(R.layout.dialog_progress);
        TextView tvMessage = progressDialog.findViewById(R.id.tvMessage);
        if (TextUtils.isEmpty(message)) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        }
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }
}
