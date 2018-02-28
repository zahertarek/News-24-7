package com.zaher.news247.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaher.news247.Activities.NewsDetailActivity;
import com.zaher.news247.Adapters.NewsAdapter;
import com.zaher.news247.Models.APIResponse;
import com.zaher.news247.Models.Article;
import com.zaher.news247.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    ArrayList<Article> articles;
    int state;
    boolean isTwoPane;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        isTwoPane = getArguments().getBoolean("isTwoPane");
        Log.e("2",""+isTwoPane);

        articles = new ArrayList<>();
        if(savedInstanceState!=null)
        {

            state = savedInstanceState.getInt("state");
            articles.clear();
            for(Parcelable parcelable : savedInstanceState.getParcelableArrayList("articles")){
                articles.add((Article) parcelable);
            }

        }
        recyclerView = rootView.findViewById(R.id.news_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(articles, new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                if(isTwoPane){
                    Bundle args = new Bundle();
                    args.putParcelable("Article",articles.get(i));
                    NewsDetailFragment newsDetailFragment = new NewsDetailFragment();
                    newsDetailFragment.setArguments(args);
                    getFragmentManager().beginTransaction().replace(R.id.news_detail_fragment_container,newsDetailFragment).commit();
                }else {
                    Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra("Article", articles.get(i));
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(newsAdapter);

        if(savedInstanceState==null) {
            FetchNews fetchNews = new FetchNews();
            String input = getArguments().getString("input");
            fetchNews.execute(input);
        }

        Paper.init(getActivity());

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("state",linearLayoutManager.findFirstVisibleItemPosition());
        outState.putParcelableArrayList("articles",articles);
    }

    private class FetchNews extends AsyncTask<String,Void,List<Article>>{
        @Override
        protected List<Article> doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonResponse = null;
            String input = strings[0];

            try {
                Uri uri;
                URL url;
                uri = Uri.parse("https://newsapi.org/v2/top-headlines?category="+input+"&country=us&apiKey=5f3648856de4494e82fa8fa2a27c07d2");
                url = new URL(uri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    jsonResponse = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    jsonResponse = null;
                }
                jsonResponse = buffer.toString();
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(jsonResponse, APIResponse.class).getArticles();
            } catch (IOException e) {
                jsonResponse = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("NewsFragment", "Error closing stream", e);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Article> articlesr) {
           if(articlesr != null) {
               articles.clear();
               articles.addAll(articlesr);
               newsAdapter.notifyDataSetChanged();
               linearLayoutManager.scrollToPosition(state);
               Paper.book().write("articles",articlesr);
           }else{
              Toast.makeText(getContext(),"NO INTERNET",Toast.LENGTH_LONG).show();
           }
        }
    }

}
