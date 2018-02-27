package com.zaher.news247.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zaher.news247.Adapters.NewsAdapter;
import com.zaher.news247.Models.Article;
import com.zaher.news247.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    ArrayList<Article> articles;
    RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private NewsAdapter newsAdapter;
    LinearLayoutManager linearLayoutManager;
    FirebaseDatabase database ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(firebaseAuth.getCurrentUser().getUid());
        articles = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                articles.clear();
               Iterable<DataSnapshot> articlesI = dataSnapshot.getChildren();

                for (DataSnapshot articleItem : articlesI) {
                    Article a = articleItem.getValue(Article.class);
                    articles.add(a);

                }

            newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        recyclerView = findViewById(R.id.fav_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(articles, new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent(getBaseContext(), NewsDetailActivity.class);
                intent.putExtra("Article",articles.get(i));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(newsAdapter);

    }
}
