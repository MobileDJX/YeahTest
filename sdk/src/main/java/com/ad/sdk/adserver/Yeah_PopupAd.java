package com.ad.sdk.adserver;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ad.sdk.R;
import com.ad.sdk.adserver.Listener.PopupAdListener;
import com.ad.sdk.utils.LoadData;


public class Yeah_PopupAd {
    static PopupWindow pop;
    private static final String TAG = "GIFPopupAds";


    @SuppressLint("SetJavaScriptEnabled")
    public static void load(Context context, PopupAdListener listener) {
        try {


            String HtmlCode = new LoadData().loadHTML_5(context);

            if (HtmlCode.isEmpty()) {
                Log.e("SDK AD :", "No ADS...");
            } else {

                Activity activity = (Activity) context;
                RelativeLayout relativelayout = new RelativeLayout(activity);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT);
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customview = layoutInflater.inflate(R.layout.gif_popup, null);
                ImageView close = (ImageView) customview.findViewById(R.id.img_close_btn);
                WebView webView = (WebView) customview.findViewById(R.id.webview);
                ConstraintLayout layout = (ConstraintLayout) customview.findViewById(R.id.pop_up_lay);


                if (HtmlCode.length() > 4000) {
                    Log.v(TAG, "sb.length = " + HtmlCode.length());
                    int chunkCount = HtmlCode.length() / 4000;     // integer division
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = 4000 * (i + 1);
                        if (max >= HtmlCode.length()) {
                            Log.v(TAG, "chunk " + i + " of " + chunkCount + ":" + HtmlCode.substring(4000 * i));
                        } else {
                            Log.v(TAG, "chunk " + i + " of " + chunkCount + ":" + HtmlCode.substring(4000 * i, max));
                        }
                    }
                }
                webView.setBackgroundColor(0);
                webView.setPadding(0, 0, 0, 0);
                webView.getSettings().setJavaScriptEnabled(true);
                String html = "<!DOCTYPE html><html><style type='text/css'>html,body {margin: 0;padding: 0;width: 100%;height: 100%;}html {display: table;}body {display: table-cell;vertical-align: middle;text-align: center;}</style><body style= \"width=\"100%\";height=\"100%\";initial-scale=\"1.0\"; maximum-scale=\"1.0\"; user-scalable=\"no\";\">" + HtmlCode + "</body></html>";
                webView.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
                webView.setClickable(true);
                webView.setVerticalScrollBarEnabled(false);
                webView.setHorizontalScrollBarEnabled(false);
                pop = new PopupWindow(customview, ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
                pop.showAtLocation(relativelayout, Gravity.CENTER, 0, 0);
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                listener.onYeahAdsShowStart();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onYeahAdsDismissed();
                        pop.dismiss();
                    }
                });

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onYeahAdsDismissed();
                        pop.dismiss();
                    }
                });
                listener.onYeahAdsShowStart();
            }
        } catch (Exception e) {
            Log.d("SDK", "Interstital Image Ad Exception:" + e);
        }

    }

}



