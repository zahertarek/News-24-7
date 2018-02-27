package com.zaher.news247.Activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zaher.news247.Fragments.NewsFragment;
import com.zaher.news247.R;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if(savedInstanceState==null) {
            NewsFragment newsFragment = new NewsFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.news_fragment_Container, newsFragment).commit();
        }
    }
}
