package com.ad.sdk.adserver;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.ad.sdk.R;
import com.ad.sdk.adserver.Listener.AdListener;
import com.ad.sdk.adserver.Listener.AdViewListener;
import com.ad.sdk.mtrack.Device_settings;
import com.ad.sdk.mtrack.Utils;
import com.ad.sdk.utils.Cdlog;
import com.ad.sdk.utils.ConnectionDetecter;
import com.ad.sdk.utils.Settings;

public class YeahAdsInitialize extends FrameLayout {


    private String zoneid = null;
    private String ad_width = null;
    private String ad_height = null;

    private String layer_style = null;
    private String align = null;
    private String padding = null;

    private AdViewListener adviewlistener = null;

    private boolean auto_refresh = false;
    private int auto_refresh_time; // Seconds
    private boolean isInternetPresent = false;
    private FrameLayout ad_container = null;
    private LayoutParams ad_container_params = null;

    private AdListener adListen = null;
    private Context adViewContext = null;

    String find_adtype = "0";


    private static AdFetcher mAdFetcher;

    public AdRequestParam adRequestObj = new AdRequestParam();

    Device_settings settings = null;

    private int measuredWidth;
    private int measuredHeight;
    private boolean measured = false;

    private final Handler handler = new Handler(Looper.getMainLooper());
    //private Displayable lastDisplayable;
    public AdListenerDispatch dispatcher;
    private boolean running;
    private boolean receiversRegistered = false;
    private BroadcastReceiver receiver;


    private boolean rvvisible = false;
    //In-Article Video Ads values decleare


    private void setAdListener(AdViewListener listener) {
        Cdlog.d(Cdlog.debugLogTag, "Ad Listener Called..");
        adviewlistener = listener;
    }


    public YeahAdsInitialize(Context context, String publisher_id, AdViewListener listener) {
        super(context);
        setup(context, null);
        setAdListener(listener);
        this.zoneid = publisher_id;
        LoadAd();
    }

    @SuppressLint("Recycle")
    private void setup(Context context, AttributeSet attrs) {
        dispatcher = new AdListenerDispatch(handler);

        mAdFetcher = new AdFetcher(YeahAdsInitialize.this);

        this.adViewContext = context;

        // Store self.context in the settings for errors
        Cdlog.error_context = this.getContext();

        Cdlog.d(Cdlog.publicFunctionsLogTag, Cdlog.getString(R.string.new_adview));

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings = Device_settings.getSettings(context);
        if (prefs.getBoolean("msdk_first_launch", true)) {
            // This is the first launch, store a value to remember
            Cdlog.v(Cdlog.baseLogTag,
                    Cdlog.getString(R.string.first_opensdk_launch));
            prefs.edit().putBoolean("msdk_first_launch", false).apply();
        } else {
            // Found the stored value, this is NOT the first launch
            Cdlog.v(Cdlog.baseLogTag,
                    Cdlog.getString(R.string.not_first_opensdk_launch));
        }

        Cdlog.v("mSDK", "Device Info::" + settings.app_id);

        // Load user variables only if attrs isn't null
        if (attrs != null) {
            @SuppressLint("CustomViewStyleable") TypedArray imgAttr = context.obtainStyledAttributes(attrs, R.styleable.ad_param);
            zoneid = imgAttr.getString(R.styleable.ad_param_zone_id);
            ad_width = imgAttr.getString(R.styleable.ad_param_ad_width);
            ad_height = imgAttr.getString(R.styleable.ad_param_ad_height);
            layer_style = imgAttr.getString(R.styleable.ad_param_layer_style);
            setAlign(imgAttr.getString(R.styleable.ad_param_align));
            padding = imgAttr.getString(R.styleable.ad_param_padding);

            if (imgAttr.getInteger(R.styleable.ad_param_auto_refresh_time, 0) > 0) {
                auto_refresh_time = imgAttr.getInteger(R.styleable.ad_param_auto_refresh_time, 30000);
                setAuto_refresh_time(auto_refresh_time);
                Log.d("Inside True Case", "Auto Refresh Time::" + auto_refresh_time);
            } else {
                Log.d("Inside False Case", "Auto Refresh Time::" + auto_refresh_time);
                setAuto_refresh_time(0);

            }
            LoadAd();
        }

        if (getAd_width() == null)
            setAd_width(Utils.convertToString(settings.display_width));

        if (getAd_height() == null)
            setAd_height(Utils.convertToString(settings.display_height));

        // We don't start the ad requesting here, since the view hasn't been
        // sized yet.

        //Set the autorefreshInterval and also autorefresh
        mAdFetcher.setPeriod(auto_refresh_time);
        mAdFetcher.setAutoRefresh(isAuto_refresh());
        mAdFetcher.setAdContext(context);
    }

    /**
     * Starts the ad request and fetching the ad
     */

    private void start() {
        Cdlog.d(Cdlog.publicFunctionsLogTag, Cdlog.getString(R.string.start));
        mAdFetcher.start();
        running = true;
    }

    /**
     * Stops the ad request and all other related operations
     */

    private void stop() {
        Cdlog.d(Cdlog.publicFunctionsLogTag, Cdlog.getString(R.string.stop));
        mAdFetcher.stop();
        running = false;
    }


    public void LoadAd() {


        Cdlog.i(Cdlog.baseLogTag, "Zone ID::" + zoneid);

        if (this.adViewContext != null) {


            // INTEGRATE AD CONTAINER

            LayoutInflater inflater = (LayoutInflater) this.adViewContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            inflater.inflate(R.layout.ad_container, this, true);

            ConnectionDetecter cd = new ConnectionDetecter(adViewContext);

            ad_container = findViewById(R.id.ad_container);


            Cdlog.e(Cdlog.debugLogTag, "Text View Added.");

            Log.d("Child Count+", "cc " + ad_container.getChildCount());
            isInternetPresent = cd.isConnectingToInternet();

            if (isInternetPresent) {

                // Generate Ad Request Parameters
                //adRequestObj = adRequestObj.GenerateRequestURL(settings, AdView.this);

                // START AD REQUEST TASK
                mAdFetcher.start();
                running = true;

            } else {
                Cdlog.e(Cdlog.debugLogTag, "Internet Connection Failed.");
                dispatcher.internet_connection_failed(YeahAdsInitialize.this, true);
            }

            adviewlistener.onInitializationComplete();
        } else {
            adviewlistener.onInitializationFailure();

            Cdlog.e(Cdlog.debugLogTag, "Ad View Not Initialized");
        }
    }


    public String getZoneid() {
        return zoneid;
    }

    private String getAd_width() {
        return ad_width;
    }

    private void setAd_width(String ad_width) {
        this.ad_width = ad_width;
    }

    private String getAd_height() {
        return ad_height;
    }

    private void setAd_height(String ad_height) {
        this.ad_height = ad_height;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    private boolean isAuto_refresh() {
        return auto_refresh;
    }

    private void setAuto_refresh(boolean auto_refresh) {
        Log.d("Inside Set Auto Refresh", "Auto Refresh::" + auto_refresh);
        this.auto_refresh = auto_refresh;

        if (mAdFetcher != null) {
            mAdFetcher.setAutoRefresh(auto_refresh);
            mAdFetcher.clearDurations();
        }
        if (this.auto_refresh && !running && mAdFetcher != null) {
            start();
        }
    }

    public int getAuto_refresh_time() {
        return auto_refresh_time;
    }

    private void setAuto_refresh_time(int auto_refresh_time) {
        this.auto_refresh_time = Math.max(Settings.getSettings().MIN_REFRESH_MILLISECONDS, auto_refresh_time);

        if (auto_refresh_time > 0) {
            Cdlog.d(Cdlog.baseLogTag, Cdlog.getString(R.string.set_period, auto_refresh_time));
            setAuto_refresh(true);
        } else {
            setAuto_refresh(false);
        }

        Cdlog.d("Auto Refresh Time", "Time :" + this.auto_refresh_time);
        if (mAdFetcher != null)
            mAdFetcher.setPeriod(this.auto_refresh_time);
    }

    /**
     * The view layout
     */

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (!measured || changed) {

            // Convert to dips
            float density = getContext().getResources().getDisplayMetrics().density;
            measuredWidth = (int) ((right - left) / density + 0.5f);
            measuredHeight = (int) ((bottom - top) / density + 0.5f);
            measured = true;

        }

        // Are we coming back from a screen/user presence change?
        if (running) {
            if (!receiversRegistered) {
                setupBroadcast(getContext());
                receiversRegistered = true;
            }

        }
    }

    private boolean requesting_visible = true;

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            // Register a broadcast receiver to pause and refresh when the phone
            // is
            // locked
            if (!receiversRegistered) {
                setupBroadcast(getContext());
                receiversRegistered = true;
            }
            Cdlog.d(Cdlog.baseLogTag, Cdlog.getString(R.string.unhidden));
            if (mAdFetcher != null
                    && (!requesting_visible || running || auto_refresh) && !rvvisible)
                start();
            else {
                // Were' not displaying the adview, the system is
                requesting_visible = false;
            }

        } else {
            // Unregister the receiver to prevent a leak.
            if (receiversRegistered) {
                dismantleBroadcast();
                receiversRegistered = false;
            }
            Cdlog.d(Cdlog.baseLogTag, Cdlog.getString(R.string.hidden));
            if (mAdFetcher != null && running) {
                stop();
            }
        }
    }

    /**
     * To check the Screen off and on visibility
     *
     * @param context app context
     */

    private void setupBroadcast(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    stop();
                    Cdlog.d(Cdlog.baseLogTag,
                            Cdlog.getString(R.string.screen_off_stop));
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    if (auto_refresh)
                        start();

                    start();
                    Cdlog.d(Cdlog.baseLogTag,
                            Cdlog.getString(R.string.screen_on_start));
                }

            }

        };
        context.registerReceiver(receiver, filter);
    }

    private void dismantleBroadcast() {
        getContext().unregisterReceiver(receiver);
    }


    /**
     * Private class to bridge events from mediation to the user
     * AdListener class.
     */
    class AdListenerDispatch implements AdListener {

        Handler handler;

        AdListenerDispatch(Handler h) {
            handler = h;
        }

        @Override
        public void param_required(final YeahAdsInitialize ad, final boolean flag) {
            // TODO Auto-generated method stub
            handler.post(() -> {
                if (adListen != null) {
                    adListen.param_required(ad, flag);
                }
            });
        }

        @Override
        public void internet_connection_failed(final YeahAdsInitialize ad, final boolean flag) {
            // TODO Auto-generated method stub
            handler.post(() -> {
                if (adListen != null) {
                    adListen.internet_connection_failed(ad, flag);
                }
            });
        }

        @Override
        public void load_ad_failed(final YeahAdsInitialize ad, final boolean flag, final String ecode,
                                   final String edesc) {
            // TODO Auto-generated method stub
            handler.post(() -> {
                if (adListen != null) {
                    Cdlog.d(Cdlog.baseLogTag, "adListen Initialized");
                    adListen.load_ad_failed(ad, flag, ecode, edesc);
                }
            });

        }
    }

    private void setAlign(String align) {
        this.align = align;
    }


}