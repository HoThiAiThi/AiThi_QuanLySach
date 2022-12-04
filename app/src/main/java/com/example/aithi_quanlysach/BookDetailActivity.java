package com.example.aithi_quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class BookDetailActivity extends AppCompatActivity {
    TextView detailbook_Textwhat, detailbook_Textwhy, detailbook_Textname, detailbook_Textcreator;
    String book_name, book_title;
    ImageView detailbook_btnBack;
    Firebase firebase = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        connectID();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null ) {
            String course_id = bundle.getString("book_id");
            DocumentReference doc = firebase.collection_books.document(course_id);
            doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            detailbook_Textname.setText(document.getString("book_name"));
                            detailbook_Textwhat.setText(document.getString("book_what"));
                            detailbook_Textwhy.setText(document.getString("book_why"));
                            detailbook_Textcreator.setText(document.getString("book_creator"));
                        } else {
                            Toast.makeText(BookDetailActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        detailbook_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    public void connectID() {
        detailbook_Textname = findViewById(R.id.detailbook_Textname);
        detailbook_Textwhat = findViewById(R.id.detailbook_Textwhat);
        detailbook_Textwhy = findViewById(R.id.detailbook_Textwhy);
        detailbook_Textcreator = findViewById(R.id.detailbook_Textcreator);
        detailbook_btnBack = findViewById(R.id.detailbook_btnBack);
    }


}