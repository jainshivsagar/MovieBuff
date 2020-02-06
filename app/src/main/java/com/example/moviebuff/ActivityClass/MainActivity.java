package com.example.moviebuff.ActivityClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.transition.ChangeBounds;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.moviebuff.ModelClass.MovieClass;
import com.example.moviebuff.ModelClass.TMDB;
import com.example.moviebuff.Networking.GetMovieDataInterface;
import com.example.moviebuff.Networking.RetrofitInstance;
import com.example.moviebuff.R;
import com.example.moviebuff.RecyclerAdapter.RecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    RecyclerAdapter adapter;
    Spinner spinner;
    List<MovieClass> movieList;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code to Set App Logo To The Left Of The App Title
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_logo);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Calulates The Screen Width In Terms Of Dp
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        //Initializing Recycler view & Setting LayoutManager
        spinner=findViewById(R.id.sortbyspinner);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        recyclerView=findViewById(R.id.recycler_view);
        gridLayoutManager=new GridLayoutManager(this,(int)dpWidth/150);
        recyclerView.setLayoutManager(gridLayoutManager);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadMovie(getQueryString(spinner.getSelectedItemPosition()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMovie(getQueryString(spinner.getSelectedItemPosition()));
            }
        });
        loadMovie("popularity.desc");
    }

    public String getQueryString(int spPosition){
        switch(spPosition){
            case 0: return "popularity.desc";
            case 1: return "popularity.asc";
            case 2: return "release_date.desc";
            case 3: return "release_date.asc";
        }
        return "popularity.desc";
    }

    public void loadMovie(String query){
        //Fetching Data By Making a API call using retrofit object
        GetMovieDataInterface getMovieDataService= RetrofitInstance.getRetrofitInstance()
                .create(GetMovieDataInterface.class);

        //Sort Movies by Popularity.Des
        Call<TMDB> call=getMovieDataService.getMovieData(query);
        /*Log the URL called*/
        System.out.println("URL Called "+ call.request().url());
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<TMDB>() {
            @Override
            public void onResponse(Call<TMDB> call, Response<TMDB> response) {
                System.out.println("Response Method.");
                TMDB tmdb=response.body();
                movieList=tmdb.getResults();
                setAdapterData();
            }

            @Override
            public void onFailure(Call<TMDB> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error Occured.",Toast.LENGTH_SHORT).show();
                if(!isOnline())
                    Toast.makeText(getApplicationContext(),"No Internet Connectivity.",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void setAdapterData(){
        //Writing Log
        for(MovieClass obj:movieList){
            Log.w(obj.getId().toString(),obj.getTitle());
        }
        adapter=new RecyclerAdapter(this,movieList);
        recyclerView.setAdapter(adapter);
    }

    //The Fully Working Url Will Contruct From 3 Pieces
    //Example:-8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
    //1. base_url=https://image.tmdb.org/t/p/
    //2. file_size=w500 means width 500 or any other size
    //3. file_path=8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
    //Important To get different image size available on TheMovieDB use following API
    //https://api.themoviedb.org/3/configuration?api_key=105d5402b9979cd998437c33b61b0797, it will return the json of available image sizes
    public static String imagePathBuilder(String img_path,String size){
        String base_url="https://image.tmdb.org/t/p/";
        String file_size=size;//this size using as default
        String file_path=img_path;
        return base_url+file_size+file_path;
    }

    public boolean isOnline(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }
}
