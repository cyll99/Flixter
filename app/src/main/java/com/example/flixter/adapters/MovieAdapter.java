package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "Oncreate Viewholder");
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType == 1){
            View pop = inflater.inflate(R.layout.high_av_mov_item, parent, false);
            viewHolder = new ViewHolder1(pop);
        }else{
            View less_pop = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder2(less_pop);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onbind Viewholder "+position);

        //instanciation of viewholder

        // get the movie at the passed position
        Movie movie = movies.get(position);

        // bind the movie data into the Movie Holder
        if (getItem(position)){
            ViewHolder1 holder1 = (ViewHolder1) holder;
            holder1.bind(movie);
        }
        else{
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.bind(movie);
        }
    }

    //Involves populatingg data into the item through holder


    public boolean getItem(int position){
        return (movies.get(position).getVoteAv() > 5);
    }

    @Override
    public int getItemViewType(int position){
        if(getItem(position)){
            return 1;
        }
        else{
            return 0;
        }
    }


        // return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{

        TextView tvTtile;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            tvTtile = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }


        public void bind(Movie movie) {
            tvTtile.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String ImageUrl;

            // if phone is in landscape imageUrl = backdrop image
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                ImageUrl = movie.getBackdropPath();
            }else{
                // else imageUrl = post image
                ImageUrl = movie.getPosterPath();

            }


            Glide.with(context).load(ImageUrl)
                    .placeholder(R.drawable.place)
                    .into(ivPoster);
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{

        ImageView bakdrop;


        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            bakdrop = itemView.findViewById(R.id.backdrop);

        }


        public void bind(Movie movie) {
            String ImageUrl;

            ImageUrl = movie.getBackdropPath();



            Glide.with(context).load(ImageUrl)
                    .placeholder(R.drawable.place)
                    .into(bakdrop);
        }
    }
}
