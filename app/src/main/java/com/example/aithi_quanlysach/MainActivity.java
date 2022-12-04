package com.example.aithi_quanlysach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    ProfileFragment profileFragment;
    BooksFragment booksFragment;
    BottomNavigationView bottomNavigationView;
    String user_name;

    public String getUser_name() {
        return user_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=  null) {
            user_name = bundle.getString("user_name");
        }
        bottomNavigationView = findViewById(R.id.main_bottomnavigationBar);
        profileFragment = new ProfileFragment();
        booksFragment = new BooksFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, booksFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_courses:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, booksFragment).commit();
                        return true;
                    case R.id.item_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}