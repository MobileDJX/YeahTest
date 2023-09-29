package com.ad.sdk.utils;

public abstract class CdlogListener {
    public enum LOG_LEVEL {
        V,
        D,
        I,
        W,
        E
    }

    public abstract void onReceiveMessage(LOG_LEVEL level, String LogTag, String message);

    public abstract void onReceiveMessage(LOG_LEVEL level, String LogTag, String message, Throwable tr);

    public abstract LOG_LEVEL getLogLevel();

}