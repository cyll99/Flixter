package com.example.flixter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.DetailActivity;
import com.example.flixter.MainActivity;
import com.example.flixter.R;
import com.example.flixter.databinding.ActivityMainBinding;
import com.example.flixter.databinding.HighAvMovItemBinding;
import com.example.flixter.databinding.ItemMovieBinding;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

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
            HighAvMovItemBinding pop = DataBindingUtil.inflate(inflater,R.layout.high_av_mov_item, parent, false);
            viewHolder = new ViewHolder1(pop);
        }else{
            ItemMovieBinding less_pop = DataBindingUtil.inflate(inflater,R.layout.item_movie, parent, false);
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
        return (movies.get(position).getVoteAv() > 7);
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
        ActivityMainBinding binding = DataBindingUtil.setContentView((Activity) context, R.layout.item_movie);

        TextView tvTtile;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;


        public ViewHolder2(@NonNull ItemMovieBinding binding) {
            super(binding.getRoot());



            tvTtile = binding.tvTitleD;
            tvOverview = binding.tvOverviewD;
            ivPoster = binding.ivPoster;
            container = binding.container;

        }


        public void bind(Movie movie) {


//            tvTtile.setText(movie.getTitle());
//            tvOverview.setText(movie.getOverview());
            binding.setMovie(movie);

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
                    .transform(new RoundedCorners(100))
                    .into(ivPoster);

            // 1 register the clisk listener on the whor row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2 Navigate on new activity on tap

//                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i =  new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));

                    context.startActivity(i);

                }
            });

        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{

        ImageView bakdrop;
        RelativeLayout container;


        public ViewHolder1(@NonNull HighAvMovItemBinding binding) {
            super(binding.getRoot());
            bakdrop = binding.backdrop;
            container = binding.container;

        }


        public void bind(Movie movie) {
            String ImageUrl;

            ImageUrl = movie.getBackdropPath();



            Glide.with(context).load(ImageUrl)
                    .placeholder(R.drawable.place)
                    .transform(new RoundedCorners(100))
                    .into(bakdrop);
            // 1 register the clisk listener on the whor row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2 Navigate on new activity on tap

//                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i =  new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, bakdrop, "profile");

                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
