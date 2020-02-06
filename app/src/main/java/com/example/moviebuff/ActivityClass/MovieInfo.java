package com.example.moviebuff.ActivityClass;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviebuff.ModelClass.MovieClass;
import com.example.moviebuff.ModelClass.TMDB;
import com.example.moviebuff.Networking.GetMovieDataInterface;
import com.example.moviebuff.Networking.RetrofitInstance;
import com.example.moviebuff.R;
import com.example.moviebuff.RecyclerAdapter.RecyclerAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MovieInfo extends AppCompatActivity {

    ImageView icon;
    TextView tittle,orig_title,popularity,vote_count,avg_vote,releas_date,overview;
    MovieClass movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // initialize views
        icon=findViewById(R.id.movie_icon);
        tittle=findViewById(R.id.title1);
        orig_title=findViewById(R.id.orig_title);
        popularity=findViewById(R.id.popularity);
        vote_count=findViewById(R.id.vote_count);
        avg_vote=findViewById(R.id.avg_vote);
        releas_date=findViewById(R.id.release_date);
        overview=findViewById(R.id.overview);

        //Getting Data From MovieJson
        String jsondata=getIntent().getStringExtra("moviejson");
        movie=new Gson().fromJson(jsondata,MovieClass.class);

        //Setting Data into views

        tittle.setText(movie.getTitle());
        orig_title.setText(movie.getOriginalTitle());
        popularity.setText(movie.getPopularity().toString());
        avg_vote.setText(movie.getVoteAverage().toString()+"/10");
        vote_count.setText(movie.getVoteCount().toString());
        releas_date.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());

        String imgUrl=MainActivity.imagePathBuilder(movie.getPosterPath(),"w500");

        Picasso.get().load(imgUrl).error(R.drawable.imgnotavailable).fit().centerInside().into(icon, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
            }
        });
        }
}
