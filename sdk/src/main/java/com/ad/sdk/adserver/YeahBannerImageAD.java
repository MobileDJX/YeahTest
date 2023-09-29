package com.ad.sdk.adserver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ad.sdk.adserver.Listener.BannerListener;
import com.ad.sdk.utils.LoadData;

public class YeahBannerImageAD {


    @SuppressLint("SetJavaScriptEnabled")
    public static void show(Context adViewContext, BannerListener listener) {


        Activity mActivity = (Activity) adViewContext;

        //Add this wherever your code needs to add the ad
        LinearLayout layout = new LinearLayout(mActivity);

        //Additionally to adjust the position to Bottom
        layout.setGravity(Gravity.BOTTOM);

        // Create a banner ad
        WebView webView = new WebView(mActivity);

        String HtmlCode = new LoadData().loadBannerImage(mActivity);

        if (HtmlCode.isEmpty()) {
            listener.onYeahAdsAdFailed();
        }

        int maxLogSize = 4000;
        for (int j = 0; j <= HtmlCode.length() / maxLogSize; j++) {
            int start = j * maxLogSize;
            int end = (j + 1) * maxLogSize;
            end = Math.min(end, HtmlCode.length());
        }

        webView.setBackgroundColor(0);
        webView.setPadding(0, 0, 0, 0);
        webView.getSettings().setJavaScriptEnabled(true);
        String html = "<!DOCTYPE html><html>" +
                "<style type='text/css'>" +
                "html,body {margin: 0;padding: 0;width: 100%;height: 100%;}" +
                "html {display: table;}" +
                "body {display: table-cell;vertical-align: middle;text-align: center;}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                "</style>" +
                "<body style= \"width=\"100%\";height=\"100%\";initial-scale=\"1.0\"; maximum-scale=\"1.0\"; user-scalable=\"no\";>" + HtmlCode + "</body></html>";

        webView.loadData(html, "text/html", "UTF-8");
        webView.setClickable(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        layout.addView(webView);

        ViewGroup.LayoutParams bannerLayout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        mActivity.addContentView(layout, bannerLayout);


        if (!HtmlCode.isEmpty()) {
            listener.onYeahAdsAdLoaded();
        }


    }


}
