package com.ad.sdk.adserver;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.ad.sdk.R;
import com.ad.sdk.adserver.Listener.InterstitialImageAdListener;
import com.ad.sdk.utils.LoadData;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class YeahInterstitialImage {
    static PopupWindow pop;
    private static final String TAG = "Interstitial_image";


    @SuppressLint("SetJavaScriptEnabled")
    public static void show(Context context, InterstitialImageAdListener interstitialImageAdListener) {
        try {

            SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialImage", MODE_PRIVATE);
            String ad_check = sharedPreferences.getString("ad_check", "1");

            if (ad_check.equalsIgnoreCase("0")) {
                Log.e("Ad STATUS :", "Not Showing");
            } else {

                Activity activity = (Activity) context;
                RelativeLayout relativelayout = new RelativeLayout(activity);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT);
                //requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customview = layoutInflater.inflate(R.layout.popup, null);
                ImageView close = (ImageView) customview.findViewById(R.id.img_close_btn);
                WebView webView = (WebView) customview.findViewById(R.id.webview);

                String HtmlCode = new LoadData().getInterstitialImage(context);

                Log.e("Int_HTML_CODE", "" + HtmlCode);


                if (HtmlCode.length() > 0) {
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
                    Log.d("mSDK Debug", "HTML CODE:" + HtmlCode);

                    interstitialImageAdListener.onInterstitialAdLoaded();

                    webView.setBackgroundColor(0);
                    webView.setPadding(0, 0, 0, 0);
                    webView.getSettings().setJavaScriptEnabled(true);
                    String html = "<!DOCTYPE html><html><style type='text/css'>html,body {margin: 0;padding: 0;width: 100%;height: 100%;}html {display: table;}body {display: table-cell;vertical-align: middle;text-align: center;}</style><body style= \"width=\"100%\";height=\"100%\";initial-scale=\"1.0\"; maximum-scale=\"1.0\"; user-scalable=\"no\";\">" + HtmlCode + "</body></html>";
                    webView.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
                    webView.setClickable(true);
                    webView.setVerticalScrollBarEnabled(false);
                    webView.setHorizontalScrollBarEnabled(false);

                    webView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            interstitialImageAdListener.onInterstitialAdClicked();

                        }
                    });

                    interstitialImageAdListener.onInterstitialAdShown();

                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    pop = new PopupWindow(customview, ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
                    pop.showAtLocation(relativelayout, Gravity.CENTER, 0, 0);
                    interstitialImageAdListener.onInterstitialAdShown();
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            interstitialImageAdListener.onInterstitialAdDismissed();
                            pop.dismiss();
                        }
                    });


                    interstitialImageAdListener.onInterstitialAdLoaded();
                }

            }
        } catch (Exception e) {
            Log.d("SDK", "Interstital Image Ad Exception:" + e);
        }


    }

}
