package com.example.pepe.tipcalc;

import android.app.Application;

/**
 * Created by Pepe on 9/29/2016.
 */

public class TipCalcClass extends Application {
    private final static String ABOUT_URL = "https://www.edx.org/";

    public static String getAboutUrl() {
        return ABOUT_URL;
    }
}
