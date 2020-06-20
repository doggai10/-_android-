package com.example.map.pp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {


    ArrayList<youtube> youtubeList;
    private String trailer;
    private String mv_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        String original_title = intent.getStringExtra("original_title");
        String poster_path = intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        String release_date = intent.getStringExtra("release_date");
        String rating = intent.getStringExtra("vote_average");
        String image = intent.getStringExtra("image");
        mv_id = intent.getStringExtra("id");
        TextView mv_title = (TextView) findViewById(R.id.mv_title);
        mv_title.setText(title);
        TextView org_title = (TextView) findViewById(R.id.mv_original_title);
        org_title.setText(original_title);
        ImageView poster = (ImageView) findViewById(R.id.mv_image);
        String url = "https://image.tmdb.org/t/p/w500/" + image;
        Glide.with(this)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(poster);

        TextView mv_overview = (TextView) findViewById(R.id.mv_overview);
        mv_overview.setText(overview);
        TextView mv_release_date = (TextView) findViewById(R.id.mv_release_date);
        mv_release_date.setText(release_date);

        TextView mv_rating = (TextView) findViewById(R.id.mv_rating);
        mv_rating.setText("평점 : " + rating+"점");
        youtubeList=new ArrayList<>();
        YoutubeAsyncTask youtube = new YoutubeAsyncTask();
        youtube.execute(mv_id);


    }
    public class YoutubeAsyncTask extends AsyncTask<String, Void, youtube[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected youtube[] doInBackground(String... strings) {
            String m_id = strings[0];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/"+mv_id+"/videos?api_key=9285197c339a75183368c8ca1834933f")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                youtube[] result = gson.fromJson(rootObject, youtube[].class);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(youtube[] youtube) {
            super.onPostExecute(youtube);

            if(youtube.length > 0){
                for(youtube p : youtube){
                    youtubeList.add(p);
                }

                trailer = youtubeList.get(0).getKey();
                YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                getLifecycle().addObserver(youTubePlayerView);
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = trailer;
                        youTubePlayer.loadVideo(videoId, 0);
                    }
                });
            }
        }

    }
}