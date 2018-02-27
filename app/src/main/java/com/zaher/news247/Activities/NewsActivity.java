package com.zaher.news247.Activities;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.zaher.news247.Fragments.NewsFragment;
import com.zaher.news247.R;

public class NewsActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        firebaseAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        TextView header = headerLayout.findViewById(R.id.header_title);
        header.setText(firebaseAuth.getCurrentUser().getEmail().toString());

        navigationView.getMenu().getItem(2).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                Bundle args = new Bundle();
                switch (item.getItemId()){

                    case R.id.nav_sports:
                        args.putString("input","sports");break;
                    case R.id.nav_business:
                        args.putString("input","business");break;
                    case R.id.nav_general:
                        args.putString("input","general");break;
                    case R.id.nav_health:
                        args.putString("input","health");break;
                    case R.id.nav_science:
                        args.putString("input","science");break;
                    case R.id.nav_technology:
                        args.putString("input","technology");break;
                    case R.id.nav_entertainment:
                        args.putString("input","entertainment");break;
                }
                NewsFragment newsFragment = new NewsFragment();
                newsFragment.setArguments(args);
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.news_fragment_Container,newsFragment).commit();



                drawerLayout.closeDrawers();



                return true;
            }
        });

        if(savedInstanceState==null) {
            Bundle args = new Bundle();
            args.putString("input","general");
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setArguments(args);
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.news_fragment_Container, newsFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.sign_out_item:
                firebaseAuth.signOut();
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.saved_articles_button:
                Intent intent1 = new Intent(getBaseContext(),FavoritesActivity.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}
