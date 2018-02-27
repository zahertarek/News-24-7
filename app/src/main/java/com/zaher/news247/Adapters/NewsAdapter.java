package com.zaher.news247.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zaher.news247.Models.Article;
import com.zaher.news247.R;

import java.util.List;

/**
 * Created by New on 2/26/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    List<Article> articles;
    OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int i);
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;
        TextView title;
        NewsViewHolder(View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.news_card_view);
            imageView = itemView.findViewById(R.id.news_image_view);
            title = itemView.findViewById(R.id.news_text_view);
        }

    }

    public NewsAdapter(List<Article> articles,OnItemClickListener listener){
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card,parent,false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(v);
        return newsViewHolder;
    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        holder.title.setText(articles.get(position).getTitle());

        if(articles.get(position).equals("")||articles.get(position).equals("")){
            //add predefined phote
        }else{
            Picasso.with(holder.imageView.getContext()).load(articles.get(position).getUrlToImage()).fit().into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
