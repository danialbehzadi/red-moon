package com.jmstudios.redmoon.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import com.jmstudios.redmoon.R;

public class IntensitySeekBarPreference extends Preference {
    public static final int DEFAULT_VALUE = 50;

    private SeekBar mIntensityLevelSeekBar;
    private int mIntensityLevel;

    public IntensitySeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayoutResource(R.layout.preference_intensity_seekbar);
    }
    
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInteger(index, DEFAULT_VALUE);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            mIntensityLevel = getPersistedInt(DEFAULT_VALUE);
        } else {
            mIntensityLevel = (Integer) defaultValue;
            persistInt(mIntensityLevel);
        }
    }

    @Override
    protected void onBindView(@NonNull View view) {
        super.onBindView(view);

        mIntensityLevelSeekBar = (SeekBar) view.findViewById(R.id.intensity_level_seekbar);
        initLayout();
    }

    private void initLayout() {
        mIntensityLevelSeekBar.setProgress(mIntensityLevel);

        mIntensityLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mIntensityLevel = progress;
                persistInt(mIntensityLevel);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
