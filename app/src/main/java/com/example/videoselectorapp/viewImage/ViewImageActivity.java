package com.example.videoselectorapp.viewImage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import androidx.annotation.StringDef;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.videoselectorapp.R;
import com.example.videoselectorapp.base.view.BaseActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/**
 * ViewImageActivity
 **/
public class ViewImageActivity extends BaseActivity {
    /**
     * The constant RECTANGULAR.
     */
    public static final String RECTANGULAR = "RECTANGULAR";
    private static final String EXTRA_IMAGE_TYPE = "extra_image_type";
    private static final String EXTRA_IMAGE_URL = "extra_image_url";

    /**
     * Open activity.
     *
     * @param mContext  the m context
     * @param imageUrl  the image url
     * @param imageType the image type
     */
    public static void openActivity(final Context mContext,
                                    final String imageUrl,
                                    @ImageTypeDef final String imageType) {
        Intent intent = new Intent(mContext, ViewImageActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        intent.putExtra(EXTRA_IMAGE_TYPE, imageType);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_image;
    }

    @Override
    protected void initView() {
        super.initView();
        ImageView ivPhoto = findViewById(R.id.ivPhoto);

        RequestOptions options = new RequestOptions()
                .optionalCenterInside()
                .placeholder(new ColorDrawable(ContextCompat.getColor(this, R.color.color_000000_00_alpha)))
                .dontAnimate();
        Glide.with(this)
                .load(getIntent().getStringExtra(EXTRA_IMAGE_URL))
                .apply(options)
                .transition(withCrossFade())
                .into(ivPhoto);

        findViewById(R.id.ivClose).setOnClickListener(v -> {
            finish();
        });
    }

    /**
     * The interface Image type def.
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({RECTANGULAR})
    private @interface ImageTypeDef {
    }
}

