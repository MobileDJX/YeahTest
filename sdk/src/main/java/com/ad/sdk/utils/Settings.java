package com.ad.sdk.utils;

public class Settings {


    public final int FETCH_THREAD_COUNT = 4;

    public final int MIN_REFRESH_MILLISECONDS = 15000;

    // STATICS
    private static Settings settings_instance = null;

    public static Settings getSettings() {
        if (settings_instance == null) {
            settings_instance = new Settings();
            Cdlog.v(Cdlog.baseLogTag, "The Msdk " + Cdlog.baseLogTag
                    + " is initializing.");
        }
        return settings_instance;
    }

    private Settings() {

    }

}
