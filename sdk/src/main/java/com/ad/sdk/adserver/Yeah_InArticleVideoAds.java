package com.ad.sdk.adserver;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

public class Yeah_InArticleVideoAds extends AppCompatActivity implements Player.Listener {

    private ExoPlayer player;
    private ImaAdsLoader adsLoader;

    StyledPlayerView playerView;

    public Yeah_InArticleVideoAds() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("InArticleVideo", MODE_PRIVATE);
        String ad_check = sharedPreferences.getString("adCheck", "0");

        if (ad_check.equalsIgnoreCase("1")) {
            loadRadiosAds(playerView, getApplicationContext());
        } else {
            Log.e("Ad Shown Status :", "Targeting Not Match");
        }
    }

    public void loadRadiosAds(StyledPlayerView playerView, Context context) {
        MultiDex.install(context);
        playerView.setControllerAutoShow(false);
        // Create an AdsLoader.
        adsLoader = new ImaAdsLoader.Builder(context).build();
        initializePlayer(context, playerView);
    }


    public void initializePlayer(Context context, StyledPlayerView playerView) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("InArticleVideo", MODE_PRIVATE);
        String ad_url = sharedPreferences.getString("InArticleVideo_URL", "");

        this.playerView = playerView;
        // Set up the factory for media sources, passing the ads loader and ad view providers.
        DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(context);

        MediaSourceFactory mediaSourceFactory =
                new DefaultMediaSourceFactory(dataSourceFactory)
                        .setAdsLoaderProvider(unusedAdTagUri -> adsLoader)
                        .setAdViewProvider(playerView);

        // Create an ExoPlayer and set it as the player for content and ads.
        player = new ExoPlayer.Builder(context).setMediaSourceFactory(mediaSourceFactory).build();
        playerView.setPlayer(player);
        adsLoader.setPlayer(player);
        Uri contentUri = Uri.parse("test");
        Uri adTagUri = Uri.parse(ad_url);
        MediaItem mediaItem =
                new MediaItem.Builder()
                        .setUri(contentUri)
                        .setAdsConfiguration(new MediaItem.AdsConfiguration.Builder(adTagUri).build())
                        .build();

        // Prepare the content and ad to be played with the SimpleExoPlayer.
        player.setMediaItem(mediaItem);
        player.prepare();

        // Set PlayWhenReady. If true, content and ads will autoplay.
        player.setPlayWhenReady(true);
        player.addListener(this);


    }


    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        if (isPlaying) {
            playerView.setVisibility(View.VISIBLE);
        } else {
            playerView.setVisibility(View.GONE);

        }
    }
}



