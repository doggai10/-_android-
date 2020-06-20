package com.example.map.pp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieViewAdapter adapter;
    ArrayList<MovieInfo> movie_list;
    ArrayList<MovieInfo> first_list;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        movie_list = new ArrayList<>();

        String search = "https://api.themoviedb.org/3/movie/upcoming?api_key=9285197c339a75183368c8ca1834933f&language=ko-KR&page=1";
        String[]  url = {search};
        MovieAsyncTask movie = new MovieAsyncTask();
        movie.execute(url[0]);
        recyclerView.setLayoutManager(new GridLayoutManager(MovieActivity.this, 2));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("영화제목을 입력하세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MovieActivity.this, s + "에 대한 영화를 검색합니다.", Toast.LENGTH_LONG).show();
                String search = "https://api.themoviedb.org/3/search/movie?api_key=9285197c339a75183368c8ca1834933f&query=" + s + "&language=ko-KR&page=1";
                String[] url = {search};
                MovieAsyncTask Search = new MovieAsyncTask();
                Search.execute(url[0]);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String search_url = "https://api.themoviedb.org/3/movie/upcoming?api_key=9285197c339a75183368c8ca1834933f&language=ko-KR&page=1";
                String[] search = {search_url};
                MovieAsyncTask movie = new MovieAsyncTask();
                movie.execute(search[0]);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_search:
                Toast.makeText(this, "search", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public class MovieAsyncTask extends AsyncTask<String, Void, MovieInfo[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            movie_list.clear();

        }

        @Override
        protected MovieInfo[] doInBackground(String... strings) {

            OkHttpClient client = new OkHttpClient();
            Request request=new Request.Builder()
                        .url(strings[0]).build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement object = parser.parse(response.body().charStream()).getAsJsonObject().get("results");
                MovieInfo[] posts = gson.fromJson(object, MovieInfo[].class);
                return posts;
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(MovieInfo[] result) {
            super.onPostExecute(result);
            if(result.length > 0){
                for(MovieInfo p : result){
                    movie_list.add(p);
                }
            }
            adapter = new MovieViewAdapter(MovieActivity.this,movie_list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

    }

}
