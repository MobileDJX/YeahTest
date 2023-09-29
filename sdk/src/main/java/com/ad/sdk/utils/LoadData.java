package com.ad.sdk.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.ad.sdk.adserver.AdResponseValue;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LoadData {

    public void saveBasicAdsData(Context context, ArrayList<AdResponseValue> adResponseValues) {

        logoutAction(context);

        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedpreferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(adResponseValues);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("SDK_LOCAL", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.commit();


    }

    public void logoutAction(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedpreferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public void saveInterstitialVideo(Context context, String url, String screenType, String ad_check) {

        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        System.out.println("@@ InterstitialVideo save url" + url);
        SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialVideo", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // prefs in the form of string.
        editor.putString("InterstitialVideo_URL", url);
        editor.putString("screenType", screenType);
        editor.putString("ad_check", ad_check);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();


    }

    public void logoutInterstitialVideoAction(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialVideo", MODE_PRIVATE);
        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    //Save Text Banner
    public void saveTextBanner(Context context, String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("BannerTextUrl", url);
        editor.commit();
    }

    public String loadTextBanner(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        String textUrl = null;
        textUrl = sharedPreferences.getString("BannerTextUrl", "");

        return textUrl;
    }


    //Save Banner Image
    public void saveBannerImage(Context context, String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("BannerImageADTAG", url);
        editor.commit();
    }

    public String loadBannerImage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        String imageUrl = null;
        imageUrl = sharedPreferences.getString("BannerImageADTAG", "");

        return imageUrl;
    }


    //Save HTML Ad
    public void saveHTML(Context context, String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("HTMLAd_TAG", url);
        editor.commit();
    }

    public String loadHTML(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        String htmlUrl = null;
        htmlUrl = sharedPreferences.getString("HTMLAd_TAG", "");

        return htmlUrl;
    }

    //Save HTML_5 Ad
    public void saveHTML_5(Context context, String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("HTML_5_Ad_TAG", url);
        editor.commit();
    }

    public String loadHTML_5(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        String html5Url = null;
        html5Url = sharedPreferences.getString("HTML_5_Ad_TAG", "");

        return html5Url;
    }


    //Save TopBanner Ad
    public void saveTopBanner(Context context, String url, int width, int height) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("topBanner_adWidth", width);
        editor.putInt("topBanner_adHeight", height);
        editor.putString("TopBanner_Ad_TAG", url);
        editor.apply();
    }

    public String loadTopBanner(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        String topBannerUrl = null;
        topBannerUrl = sharedPreferences.getString("TopBanner_Ad_TAG", "");
        return topBannerUrl;
    }

    //Save BottomSlider Ad
    public void saveBottomSlider(Context context, String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BottomSlider_Ad_TAG", url);
        editor.apply();
    }

    public String loadBottomSlider(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        String bottomSliderUrl = null;
        bottomSliderUrl = sharedPreferences.getString("BottomSlider_Ad_TAG", "");
        return bottomSliderUrl;
    }


    //Logout BannerAD
    public void logoutBannerAD(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BannerAds", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    //InterstitialImage
    public void saveInterstitialImage(Context context, String url, String ad_check) {

        logoutInterstitialImageAction(context);

        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        System.out.println("@@ save InterstitialImageurl" + url);
        SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialImage", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();


        // prefs in the form of string.
        editor.putString("InterstitialImage_URL", url);

        editor.apply();


    }

    public void logoutInterstitialImageAction(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialImage", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getInterstitialImage(Context context) {

        String url = null;

        SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialImage", MODE_PRIVATE);
        url = sharedPreferences.getString("InterstitialImage_URL", "");
        System.out.println("@@ InterstitialImage_URL" + url);
        return url;
    }


    public void saveRewardedVideo(Context context, String url, String screenType, String ad_check) {

        logoutRewardedVideoAction(context);

        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        System.out.println("@@ RewardedVideo save url" + url);
        SharedPreferences sharedPreferences = context.getSharedPreferences("RewardedVideo", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();


        // prefs in the form of string.
        editor.putString("RewardedVideo_URL", url);
        editor.putString("screenType", screenType);
        editor.putString("ad_check", ad_check);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();


    }


    public void logoutRewardedVideoAction(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("RewardedVideo", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    //InArticle Video Ads
    public void saveInArticleVideo(Context context, String url, String ad_check) {

        logoutInterstitialVideoAction(context);

        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        System.out.println("@@ InArticleVideo save url" + url);
        SharedPreferences sharedPreferences = context.getSharedPreferences("InArticleVideo", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();


        // prefs in the form of string.
        editor.putString("InArticleVideo_URL", url);
        editor.putString("adCheck", ad_check);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();


    }

    public void logoutInArticleVideoAction(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("InArticleVideo", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    public void logoutClear(Context context) {
        logoutInterstitialImageAction(context);
        logoutInterstitialVideoAction(context);
        logoutRewardedVideoAction(context);
        logoutAction(context);
        logoutBannerAD(context);
        logoutInArticleVideoAction(context);
    }

}
