package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.databinding.ActivityDetailBinding;
import com.example.flixter.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API_KEY = "AIzaSyBZpJIfit6gFDqMFMH6k9I1tQSqr80iiVk";
    public static final String VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    private ActivityDetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        tvTitle = binding.tvTitleD;
        tvOverview = binding.tvOverviewD;
        ratingBar = binding.ratingBar;
        youTubePlayerView = binding.player;



        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
//        tvTitle.setText(movie.getTitle());
//        tvOverview.setText(movie.getOverview());
//        ratingBar.setRating((float)movie.getRating());
        binding.setMovie(movie);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if(results.length() == 0){
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("detail activity", youtubeKey);
                    initializeYoutube(youtubeKey);


                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse Json", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });


    }

    private void initializeYoutube(final String youtubeKey) {
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        youTubePlayerView.initialize("YOUTUBE_API_KEY", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("detail activity", " on succes");
                // do any work here to cue video, play video, etc.
                if(movie.getVoteAv() > 5){
                    youTubePlayer.loadVideo(youtubeKey);
                }else{
                    youTubePlayer.cueVideo(youtubeKey);

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("detail activity", " on onInitializationFailure");
            }
        });
    }
}