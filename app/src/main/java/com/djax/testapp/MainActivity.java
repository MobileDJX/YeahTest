package com.djax.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YeahAdsManager.init(MainActivity.this);

        YeahAdsManager.showBanner();
//        YeahAdsManager.showInterstitialYeahAds();

//        YeahAdsManager.showRewardedYeahAds();

        YeahAdsManager.showIntImage();
    }
}