package com.zaher.news247.Activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zaher.news247.Fragments.NewsDetailFragment;
import com.zaher.news247.Models.Article;
import com.zaher.news247.R;

public class NewsDetailActivity extends AppCompatActivity {

    Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        article = getIntent().getParcelableExtra("Article");

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putParcelable("Article", article);

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
            newsDetailFragment.setArguments(args);
            fragmentManager.beginTransaction().add(R.id.news_detail_fragment_container, newsDetailFragment).commit();
        }
    }
}
