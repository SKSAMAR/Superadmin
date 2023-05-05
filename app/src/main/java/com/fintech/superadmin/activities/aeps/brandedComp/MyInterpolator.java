package com.fintech.superadmin.activities.aeps.brandedComp;

import android.view.animation.Interpolator;

public class MyInterpolator
        implements Interpolator {
    /*  8 */   private double mAmplitude = 1.0D;
    /*  9 */   private double mFrequency = 10.0D;


    public MyInterpolator(double mAmplitude, double mFrequency) {
        /* 13 */
        this.mAmplitude = mAmplitude;
        /* 14 */
        this.mFrequency = mFrequency;
    }


    public float getInterpolation(float time) {
        /* 20 */
        return (float) (-1.0D * Math.pow(Math.E, -time / this.mAmplitude) * Math.cos(this.mFrequency * time) + 1.0D);
    }
}
