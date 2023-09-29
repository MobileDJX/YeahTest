package com.ad.sdk.adserver;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ad.sdk.utils.Cdlog;
import com.ad.sdk.utils.LoadData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class AdResponse {


    ArrayList<AdResponseValue> adResponseValues;

    private String adtag = null;
    private String ad_type = null;
    private String imp_url = null;
    private String click_url = null;
    public String status = "error";


    String interstitialVideoTag = null;
    String interstitialImageTag = null;
    String rewardedVideoTag = null;
    String inArticleVideoTag = null;

    //Rewarded Video
    private JSONObject rewardedres;


    private volatile boolean parsingComplete = true;

    private volatile boolean failed = false;
    private String error_code = null;
    private String error_desc = null;


    public void readAndParseJSON(String result, Context context) {

        try {
            if (result != null) {
                adResponseValues = new ArrayList<AdResponseValue>();

                JSONObject reader = new JSONObject(result);
                String ad_response = reader.getString(Config.TAG_AD_RESPONSE);

                ad_response = ad_response.trim();
                new LoadData().logoutClear(context);

                if (ad_response.equalsIgnoreCase("success")) {
                    JSONArray ads = reader.getJSONArray(Config.TAG_ADS);

                    for (int i = 0; i < ads.length(); i++) {
                        try {
                            JSONObject jsonObj = ads.getJSONObject(i);

                            String sub_status = jsonObj.getString(Config.TAG_AD_RESPONSE);

                            if (sub_status.equalsIgnoreCase("success")) {


                                status = "success";
                                ad_type = jsonObj.getString(Config.TAG_AD_TYPE);
                                Log.d("dJAXM", "ad_type : " + ad_type);

                                //Banner Text Ad
                                if (ad_type.equalsIgnoreCase("REDIRECT_ADS")) {
                                    Log.d("Banner Text :", "textBanner AD");
                                    String bannerText = jsonObj.getString("ad_tag");
                                    new LoadData().saveTextBanner(context, bannerText);
                                }

                                //Banner Image Ad
                                else if (ad_type.equalsIgnoreCase("Banner")) {
                                    Log.d("Banner Image :", "Image Banner AD");
                                    String bannerImage = null;
                                    if (jsonObj.getString("ad_check").equalsIgnoreCase("1")) {
                                        bannerImage = jsonObj.getString("ad_tag");
                                        Log.e("BannerImageAD Status", "AD SHOWN..");
                                    } else {
                                        Log.e("BannerImageAD Status", "AD NOT SHOWN..");
                                    }
                                    new LoadData().saveBannerImage(context, bannerImage);
                                }


                                //TopBanner
                                else if (ad_type.equalsIgnoreCase("Top Banner")) {
                                    Log.d("TopBanner Ad:", "TOP_BANNER AD");
                                    int width = Integer.parseInt(jsonObj.getString("width"));
                                    int height = Integer.parseInt(jsonObj.getString("height"));

                                    String topBannerURL = null;
                                    if (jsonObj.getString("ad_check").equalsIgnoreCase("1")) {
                                        topBannerURL = jsonObj.getString("ad_tag");
                                        Log.e("TopBannerAD Status", "AD SHOWN..");

                                    } else {
                                        Log.e("TopBannerAD Status", "AD NOT SHOWN..");
                                    }

                                    new LoadData().saveTopBanner(context, topBannerURL, width, height);
                                }


                                //BottomSlider
                                else if (ad_type.equalsIgnoreCase("Bottom Slider")) {
                                    Log.d("BottomSlider Ad:", "BOTTOM_SLIDER AD");

                                    String bottomSliderURL = null;
                                    if (jsonObj.getString("ad_check").equalsIgnoreCase("1")) {
                                        bottomSliderURL = jsonObj.getString("ad_tag");
                                        Log.e("BottomSliderAD Status", "AD SHOWN..");
                                    } else {
                                        Log.e("BottomSliderAD Status", "AD NOT SHOWN..");
                                    }


                                    new LoadData().saveBottomSlider(context, bottomSliderURL);
                                }


                                //HTML Ads
                                else if (ad_type.equalsIgnoreCase("HTML")) {
                                    Log.d("Html Ad:", "HTML AD");
                                    String htmlURL = null;
                                    if (jsonObj.getString("ad_check").equalsIgnoreCase("1")) {
                                        htmlURL = jsonObj.getString("ad_tag");
                                        Log.e("HtmlAD Status", "AD SHOWN..");
                                    } else {
                                        Log.e("HtmlAD Status", "AD NOT SHOWN..");
                                    }
                                    new LoadData().saveHTML(context, htmlURL);
                                }

                                //HTML_5 Ads
                                else if (ad_type.equalsIgnoreCase("HTML5")) {
                                    Log.d("Html5 Ad:", "HTML5 AD");
                                    String html5URL = null;
                                    if (jsonObj.getString("ad_check").equalsIgnoreCase("1")) {
                                        html5URL = jsonObj.getString("ad_tag");
                                        Log.e("Html5AD Status", "AD SHOWN..");
                                    } else {
                                        Log.e("Html5AD Status", "AD NOT SHOWN..");
                                    }
                                    new LoadData().saveHTML_5(context, html5URL);
                                }


                                //Interstitial Image
                                else if (ad_type.equalsIgnoreCase("Interstitial")) {

                                    String ad_check = jsonObj.getString("ad_check");
                                    interstitialImageTag = jsonObj.getString("ad_tag");

                                    Log.d("SDK", "interstitialImageTag" + interstitialImageTag);

                                    new LoadData().saveInterstitialImage(context, interstitialImageTag, ad_check);

                                }


                                //Rewarded Video Ads
                                else if (ad_type.equalsIgnoreCase("REWARDED_VID")) {
                                    String screenType = jsonObj.getString("layout");
                                    String ad_check = jsonObj.getString("ad_check");


                                    if (ad_check.equalsIgnoreCase("1")) {
                                        Log.e("Rewarded_Video Status", "AD SHOWN..");
                                        rewardedVideoTag = jsonObj.getString("ad_tag");
                                        Log.d("rewardedres", "rewardedres : " + rewardedres);
                                    } else {
                                        Log.e("Rewarded_Video Status", "AD NOT SHOWN..");
                                    }

                                    JSONObject ad_values = jsonObj.getJSONObject("ad_values");
                                    JSONArray rewards = ad_values.getJSONArray("rewards");
                                    Log.e("reward", rewards.toString());


                                    String amount = null;
                                    JSONObject amount_object = rewards.getJSONObject(0);
                                    amount = amount_object.getString("amount");

                                    Log.e("amount", amount);

                                    SharedPreferences sharedPreferences = context.getSharedPreferences("reward_amount", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("amount", amount);
                                    editor.apply();

                                    new LoadData().saveRewardedVideo(context, rewardedVideoTag, screenType, ad_check);

                                }

                                //InArticle Video Ads
                                else if (ad_type.equalsIgnoreCase("INARTICLE_VIDEO_ADS")) {
                                    String ad_check = jsonObj.getString("ad_check");


                                    if (ad_check.equalsIgnoreCase("1")) {
                                        inArticleVideoTag = jsonObj.getString("ad_tag");
                                        Log.d("InarticleAds : ", inArticleVideoTag);
                                        Log.e("InArticleVideo Status", "AD SHOWN..");
                                    } else {
                                        Log.e("InArticleVideo Status", "AD NOT SHOWN..");
                                    }

                                    new LoadData().saveInArticleVideo(context, inArticleVideoTag, ad_check);
                                }


                                //Video Ads
                                //Interstitial Video Ads
                                else if (ad_type.equalsIgnoreCase("INTERSTITIAL_VID")) {

                                    String ad_check = jsonObj.getString("ad_check");
                                    String screenType = jsonObj.getString("layout");


                                    if (ad_check.equalsIgnoreCase("1")) {
                                        interstitialVideoTag = jsonObj.getString("ad_tag");
                                        Log.d("inarticleAds : ", interstitialVideoTag);
                                        Log.e("InArticleVideo Status", "AD SHOWN..");
                                    } else {
                                        Log.e("InArticleVideo Status", "AD NOT SHOWN..");
                                    }

                                    new LoadData().saveInterstitialVideo(context, interstitialVideoTag, screenType, ad_check);

                                } else {
                                    imp_url = URLDecoder.decode(jsonObj.getString(Config.TAG_BEACON_URL), "UTF-8");

                                    click_url = URLDecoder.decode(jsonObj.getString(Config.TAG_CLICK_URL), "UTF-8");

                                    adtag = URLDecoder.decode(jsonObj.getString(Config.TAG_ADTAG), "UTF-8");

                                    AdResponseValue adv = new AdResponseValue();
                                    adv.setZone_id("0");
                                    adv.setImp_url(imp_url);
                                    adv.setClick_url(click_url);
                                    adv.setAd_tag(adtag);
                                    adResponseValues.add(adv);
                                }

                            } else {

                                failed = true;
                                JSONObject error = jsonObj.getJSONObject(Config.TAG_ERROR);
                                error_code = error.getString(Config.TAG_ERR_CODE);
                                error_desc = error.getString(Config.TAG_ERR_DESC);
                            }


                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (adResponseValues.size() > 0) {
                        new LoadData().saveBasicAdsData(context, adResponseValues);
                    }


                } else {

                    // Handle Error Flow
                    Cdlog.d("mSDK Debug", ad_response);
                    failed = true;
                    JSONObject error = reader.getJSONObject(Config.TAG_ERROR);
                    error_code = error.getString(Config.TAG_ERR_CODE);
                    error_desc = error.getString(Config.TAG_ERR_DESC);

                }
                parsingComplete = false;
            } else {
                System.out.print("Caught NullPointerException: Null result return from given URL!!");
            }
        } catch (JSONException e) {
            Log.e("mSDK Debug", "unexpected JSON exception", e);
            e.printStackTrace();
        }
    }
}
