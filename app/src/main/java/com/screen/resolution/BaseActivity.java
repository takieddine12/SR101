package com.screen.resolution;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.N)
    private static final int[] ORDERED_DENSITY_DP_N = {
            DisplayMetrics.DENSITY_LOW,
            DisplayMetrics.DENSITY_MEDIUM,
            DisplayMetrics.DENSITY_TV,
            DisplayMetrics.DENSITY_HIGH,
            DisplayMetrics.DENSITY_280,
            DisplayMetrics.DENSITY_XHIGH,
            DisplayMetrics.DENSITY_360,
            DisplayMetrics.DENSITY_400,
            DisplayMetrics.DENSITY_420,
            DisplayMetrics.DENSITY_XXHIGH,
            DisplayMetrics.DENSITY_560,
            DisplayMetrics.DENSITY_XXXHIGH
    };

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private static final int[] ORDERED_DENSITY_DP_N_MR1 = {
            DisplayMetrics.DENSITY_LOW,
            DisplayMetrics.DENSITY_MEDIUM,
            DisplayMetrics.DENSITY_TV,
            DisplayMetrics.DENSITY_HIGH,
            DisplayMetrics.DENSITY_260,
            DisplayMetrics.DENSITY_280,
            DisplayMetrics.DENSITY_XHIGH,
            DisplayMetrics.DENSITY_340,
            DisplayMetrics.DENSITY_360,
            DisplayMetrics.DENSITY_400,
            DisplayMetrics.DENSITY_420,
            DisplayMetrics.DENSITY_XXHIGH,
            DisplayMetrics.DENSITY_560,
            DisplayMetrics.DENSITY_XXXHIGH
    };

    @TargetApi(Build.VERSION_CODES.P)
    private static final int[] ORDERED_DENSITY_DP_P = {
            DisplayMetrics.DENSITY_LOW,
            DisplayMetrics.DENSITY_MEDIUM,
            DisplayMetrics.DENSITY_TV,
            DisplayMetrics.DENSITY_HIGH,
            DisplayMetrics.DENSITY_260,
            DisplayMetrics.DENSITY_280,
            DisplayMetrics.DENSITY_XHIGH,
            DisplayMetrics.DENSITY_340,
            DisplayMetrics.DENSITY_360,
            DisplayMetrics.DENSITY_400,
            DisplayMetrics.DENSITY_420,
            DisplayMetrics.DENSITY_440,
            DisplayMetrics.DENSITY_XXHIGH,
            DisplayMetrics.DENSITY_560,
            DisplayMetrics.DENSITY_XXXHIGH
    };


    @Override
    protected void attachBaseContext(final Context baseContext) {

        Context newContext = baseContext;

        // Screen zoom is supported from API 24+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Resources resources = baseContext.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();


            if (displayMetrics.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
                if(Settings.Global.getString(baseContext.getContentResolver(), "display_size_forced") != null) {
                    float defaultDensity = (DisplayMetrics.DENSITY_DEVICE_STABLE / (float) DisplayMetrics.DENSITY_DEFAULT);
                    float defaultScreenWidthDp = displayMetrics.widthPixels / defaultDensity;
                    configuration.densityDpi = findDensityDpCanFitScreen((int) defaultScreenWidthDp);
                } else {
                    configuration.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
                }
                newContext = baseContext.createConfigurationContext(configuration);
            }
        }
        super.attachBaseContext(newContext);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static int findDensityDpCanFitScreen(final int densityDp) {
        int[] orderedDensityDp;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            orderedDensityDp = ORDERED_DENSITY_DP_P;
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            orderedDensityDp = ORDERED_DENSITY_DP_N_MR1;
        } else {
            orderedDensityDp = ORDERED_DENSITY_DP_N;
        }

        int index = 0;
        while (densityDp >= orderedDensityDp[index]) {
            index++;
        }
        return orderedDensityDp[index];
    }
}
