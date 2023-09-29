package com.ad.sdk.adserver;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.ad.sdk.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public class RewardedLoadActivity extends AppCompatActivity implements Player.Listener {
    private StyledPlayerView playerView;
    private ExoPlayer player;
    private ImaAdsLoader adsLoader;

    int amount;

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.adactivity_main);
        MultiDex.install(this);
        playerView = findViewById(R.id.player_view);
        playerView.setControllerAutoShow(false);
        count = 0;
        if (YeahRewardedVideo.rewardedListener != null) {
            YeahRewardedVideo.rewardedListener.onYeahAdsAdLoaded();
        }

        // Create an AdsLoader.
        adsLoader = new ImaAdsLoader.Builder(this).build();

        ((ImageView) findViewById(R.id.img_close_btn)).setVisibility(View.GONE);

        ((ImageView) findViewById(R.id.img_close_btn)).setOnClickListener(view -> {
            playerView.onPause();
            adsLoader.setPlayer(null);
            playerView.setPlayer(null);

            finish();
        });


        SharedPreferences sharedPreferences = getSharedPreferences("reward_amount", MODE_PRIVATE);
        String string_amount = sharedPreferences.getString("amount", "noamount");

        if (string_amount.equalsIgnoreCase("")) {
            amount = 0;
        } else {
            amount = Integer.parseInt(string_amount);
        }


        Log.e("Add Amount", String.valueOf(amount));
    }

    private void releasePlayer() {
        adsLoader.setPlayer(null);
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    private void initializePlayer() {
        // Set up the factory for media sources, passing the ads loader and ad view providers.
        DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(this);

        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(dataSourceFactory).setAdsLoaderProvider(unusedAdTagUri -> adsLoader).setAdViewProvider(playerView);

        // Create an ExoPlayer and set it as the player for content and ads.
        player = new ExoPlayer.Builder(this).setMediaSourceFactory(mediaSourceFactory).build();
        playerView.setPlayer(player);
        adsLoader.setPlayer(player);

        SharedPreferences sharedPreferences = getSharedPreferences("RewardedVideo", MODE_PRIVATE);
        String ad_url = sharedPreferences.getString("RewardedVideo_URL", "");
        String screenTpye = sharedPreferences.getString("screenType", "");
        if (screenTpye.equals("portrait")) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        System.out.println("@@ RewardedVideo_URL ad_url " + ad_url);

        Uri contentUri = Uri.parse("test");
        Uri adTagUri = Uri.parse(ad_url);
        MediaItem mediaItem = new MediaItem.Builder().setUri(contentUri).setAdsConfiguration(new MediaItem.AdsConfiguration.Builder(adTagUri).build()).build();

        // Prepare the content and ad to be played with the SimpleExoPlayer.
        player.setMediaItem(mediaItem);
        player.prepare();

        // Set PlayWhenReady. If true, content and ads will autoplay.
        player.setPlayWhenReady(true);
        player.addListener(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        //
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
                YeahRewardedVideo.rewardedshowListener.onYeahAdsShowComplete();

            }
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsLoader.release();
        YeahRewardedVideo.rewardedshowListener.onYeahAdsShowComplete();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        if (!isPlaying) {
            if (YeahRewardedVideo.rewardedshowListener != null) {
                YeahRewardedVideo.rewardedshowListener.Rewarded("Reward Points", amount);
            }
            ((ImageView) findViewById(R.id.img_close_btn)).setVisibility(View.VISIBLE);
        } else {

            if (count == 0) {
                YeahRewardedVideo.rewardedshowListener.onYeahAdsShowStart();
                count++;
            }
        }
    }
}