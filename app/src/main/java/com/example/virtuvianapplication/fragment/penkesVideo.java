package com.example.virtuvianapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virtuvianapplication.app.ApiConfig;
import com.example.virtuvianapplication.databinding.FragmentPenkesVideoBinding;
import com.example.virtuvianapplication.response.FileResponse;
import com.example.virtuvianapplication.util.PreferenceManager;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class penkesVideo extends Fragment {
    private FragmentPenkesVideoBinding binding;
    private PreferenceManager preferenceManager;
   private StyledPlayerView playerView;
   private ExoPlayer player;
   private ImaAdsLoader adsLoader;
   String FULFILLED;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPenkesVideoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        playerView = binding.videoView;
        adsLoader = new ImaAdsLoader.Builder(binding.getRoot().getContext()).build();
    }

    @Override
    public void onStart() {
        super.onStart();
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
            }
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adsLoader.release();
    }

    private void releasePlayer() {
        adsLoader.setPlayer(null);
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    private void initializePlayer() {
        Call<FileResponse> call = ApiConfig.getApiRequest().getFile();
        call.enqueue(new Callback<FileResponse>() {
            @Override
            public void onResponse(Call<FileResponse> call, Response<FileResponse> response) {
                FileResponse fileResponse = response.body();
                for (int i = 0; i < fileResponse.getData().length; i++) {
                    FULFILLED = "https://virtuvcloud.com"+fileResponse.getData()[i].getFile_path();

                    // Set up the factory for media sources, passing the ads loader and ad view providers.
                    DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(binding.getRoot().getContext());
                    MediaSourceFactory mediaSourceFactory =
                            new DefaultMediaSourceFactory(dataSourceFactory)
                                    .setAdsLoaderProvider(unusedAdTagUri -> adsLoader)
                                    .setAdViewProvider(playerView);

                    // Create an ExoPlayer and set it as the player for content and ads.
                    player = new ExoPlayer.Builder(binding.getRoot().getContext()).setMediaSourceFactory(mediaSourceFactory).build();
                    playerView.setPlayer(player);
                    adsLoader.setPlayer(player);

                    // Create the MediaItem to play, specifying the content URI and ad tag URI.
                    Uri contentUri = Uri.parse(FULFILLED);
                    Uri geTagUri = Uri.parse(FULFILLED);
                    MediaItem mediaItem =
                            new MediaItem.Builder()
                                    .setUri(contentUri)
                                    .setAdsConfiguration(new MediaItem.AdsConfiguration.Builder(geTagUri).build())
                                    .build();

                    // Prepare the content and ad to be played with the SimpleExoPlayer.
                    player.setMediaItem(mediaItem);
                    player.prepare();

                    // Set PlayWhenReady. If true, content and ads will autoplay.
                    player.setPlayWhenReady(false);
                }
            }

            @Override
            public void onFailure(Call<FileResponse> call, Throwable t) {

            }
        });
    }
}