package com.zaher.news247.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.zaher.news247.Models.Article;
import com.zaher.news247.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends Fragment {

    AdView mAdView;
    Article article;
    TextView title;
    TextView author;
    TextView source;
    TextView date;
    ImageView image;
    TextView description;
    TextView full;
    public NewsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        title = rootView.findViewById(R.id.article_title);
        author= rootView.findViewById(R.id.article_author);
        source = rootView.findViewById(R.id.article_source);
        date = rootView.findViewById(R.id.article_date);
        image = rootView.findViewById(R.id.article_image);
        description = rootView.findViewById(R.id.article_description);
        full = rootView.findViewById(R.id.article_full);

        article = getArguments().getParcelable("Article");

        title.setText(article.getTitle());
        author.setText(article.getAuthor());
        source.setText(article.getSource().getName());
        date.setText(article.getPublishedAt());
        description.setText(article.getDescription());

        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                startActivity(browserIntent);
            }
        });

        Picasso.with(getContext()).load(article.getUrlToImage()).fit().into(image);






        return rootView;
    }

}
