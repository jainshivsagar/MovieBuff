package com.example.moviebuff.RecyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviebuff.ActivityClass.MainActivity;
import com.example.moviebuff.ActivityClass.MovieInfo;
import com.example.moviebuff.ModelClass.MovieClass;
import com.example.moviebuff.R;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.MyViewHolder>{
    private List<MovieClass> movieList;
    LayoutInflater inflater;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        View view=null;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.mthumb_icon);
            title=itemView.findViewById(R.id.mthumb_title);
            this.view=itemView;
        }
        public void setData(final MovieClass movie){
            this.title.setText(movie.getTitle());
            String imgurl= MainActivity.imagePathBuilder(movie.getPosterPath(),"w500");
            Picasso.get().load(imgurl).error(R.drawable.imgnotavailable).fit().centerInside().into(this.icon, new Callback() {  //Second Argument i.e. new Callback is used to track the status
                // of loading image process
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                }
            });
        }
    }

    public RecyclerAdapter(Context context,List<MovieClass> movieList) {
        this.movieList = movieList;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.movie_thumbnail_layout,parent,false);
        MyViewHolder mVH=new MyViewHolder(view);
        return mVH;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        final MovieClass movie=movieList.get(i);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code For Share Element Transition
                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,holder.icon,"imagetransition");
                Intent intent=new Intent(context,MovieInfo.class);
                String json=new Gson().toJson(movie);   //Code To Pass Movie Object As Json String
                intent.putExtra("moviejson",json);
                context.startActivity(intent,activityOptionsCompat.toBundle());
            }
        });
        holder.setData(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }



}
