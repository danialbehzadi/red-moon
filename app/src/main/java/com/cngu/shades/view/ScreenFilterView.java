package com.cngu.shades.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.cngu.shades.R;
import com.cngu.shades.presenter.ScreenFilterPresenter;

public class ScreenFilterView extends WindowView {
    private static final String TAG = "ScreenFilterView";
    private static final boolean DEBUG = true;

    private static final float MIN_DIM   = 0f;
    private static final float MAX_DIM   = 100f;
    private static final float MIN_ALPHA = 0f;
    private static final float MAX_ALPHA = 0.75f;

    private Context mContext;
    private ScreenFilterPresenter mPresenter;
    private ImageView mScreenFilter;

    public ScreenFilterView(Context context) {
        super(context);
        mContext = context;

        mScreenFilter = (ImageView) findViewById(R.id.screen_filter_imageview);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.view_screen_filter;
    }

    /**
     * Sets the dim level of the screen filter.
     *
     * @param dimLevel value between 0 and 100, inclusive, where 0 is fully transparent, and 100 is
     *                 the maximum allowed dim level determined by the system, but is guaranteed to
     *                 never be fully opaque.
     */
    public void setFilterDimLevel(int dimLevel) {
        float alpha = mapToRange((float) dimLevel, MIN_DIM, MAX_DIM, MIN_ALPHA, MAX_ALPHA);

        if (mScreenFilter != null) {
            mScreenFilter.setAlpha(alpha);
        }
        if (DEBUG) {
            Log.i(TAG, String.format("Set filter alpha to: %.2f", alpha));
        }
    }

    /**
     * Sets the color tint of the screen filter.
     *
     * @param color RGB color represented by a 32-bit int; the format is the same as the one defined
     *              in {@link android.graphics.Color}, but the alpha byte is ignored.
     */
    public void setFilterRgbColor(int color) {
        int rgbColor = stripAlpha(color);

        if (mScreenFilter != null) {
            mScreenFilter.setBackgroundColor(rgbColor);
        }

        if (DEBUG) Log.i(TAG, String.format("Set filter RGB to 0x%s", Integer.toHexString(rgbColor)));
    }

    public void registerPresenter(ScreenFilterPresenter presenter) {
        if (presenter == null) {
            throw new IllegalArgumentException("presenter cannot be null");
        }
        mPresenter = presenter;

        if (DEBUG) Log.i(TAG, "Registered Presenter");
    }

    private int stripAlpha(int color) {
        return color | 0xFF000000;
    }

    private float mapToRange(float value, float minInput, float maxInput,
                             float minOutput, float maxOutput) {
        return (value - minInput) * ((maxOutput - minOutput) / (maxInput - minInput)) + minOutput;
    }
}
