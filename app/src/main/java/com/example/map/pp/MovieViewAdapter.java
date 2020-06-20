package com.example.map.pp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.RecyclerViewHolders>{

    public ArrayList<MovieInfo> movieList;
    private LayoutInflater Inflate;
    private Context Context;
    public TextView textView;
    public MovieViewAdapter(Context context, ArrayList<MovieInfo> itemList) {
        this.Context = context;
        this.Inflate = LayoutInflater.from(context);
        this.movieList = itemList;
    }
    @NonNull
    @Override
    public MovieViewAdapter.RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=Inflate.inflate(R.layout.list_movie,parent,false);
        RecyclerViewHolders viewHolder = new RecyclerViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewAdapter.RecyclerViewHolders holder, final int position) {
        String url = "https://image.tmdb.org/t/p/w500/" + movieList.get(position).getPoster_path();
        Glide.with(Context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.imageView);
       holder.textView.setText(movieList.get(position).getTitle());
       holder.textView2.setText("평점: "+movieList.get(position).getVote_average()+"점");

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(Context, MovieDetailActivity.class);
               intent.putExtra("title",movieList.get(position).getTitle());
               intent.putExtra("original_title",movieList.get(position).getOriginal_title());
               intent.putExtra("overview",movieList.get(position).getOverview());
               intent.putExtra("release_date",movieList.get(position).getRelease_date());
               intent.putExtra("vote_average",movieList.get(position).getVote_average());
               intent.putExtra("poster_path",movieList.get(position).getPoster_path());
               intent.putExtra("image",movieList.get(position).getBackdrop_path());
               intent.putExtra("id",movieList.get(position).getId());
               Context.startActivity(intent);


           }
       });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public RecyclerViewHolders(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.movieView);
            textView=(TextView)itemView.findViewById(R.id.movieText);
            textView2=(TextView)itemView.findViewById(R.id.movieRating);
        }
    }

}
