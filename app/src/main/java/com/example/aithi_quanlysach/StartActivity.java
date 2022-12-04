package com.example.aithi_quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    Button start_getStarted;
    private Object put;
    Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebase = new Firebase();
        setContentView(R.layout.activity_start);
        connectID();

        start_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignin();

            }
        });

    }
    public void connectID() {
        start_getStarted = findViewById(R.id.start_getStarted);
    }
    public void gotoSignin() {
        Intent intent = new Intent(StartActivity.this, SigninActivity.class);
        startActivity(intent);
    }
}