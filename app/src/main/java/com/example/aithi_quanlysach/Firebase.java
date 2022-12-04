package com.example.aithi_quanlysach;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Firebase {
    FirebaseFirestore db;
    CollectionReference collection_books;
    CollectionReference collection_users ;
    public Firebase() {
        db = FirebaseFirestore.getInstance();
        collection_books = db.collection("Books");
        collection_users = db.collection("Users");
    }

    // FIREBASE USERS

    // Create data collection Users
    public void createUser(String user_name, String user_password, String user_firstname, String user_lastname, String user_email, String user_phone) {
        Map<String, Object> user = new HashMap<>();
        user.put("user_name", user_name);
        user.put("user_password", user_password);
        user.put("user_firstname", user_firstname);
        user.put("user_lastname", user_lastname);
        user.put("user_email", user_email);
        user.put("user_phone", user_phone);

        collection_users.add(user);

    }
    // FIREBASE COURSES

    // Create data collection Courses
    public void createBook(String book_name, String book_what, String book_why, String book_creator) {
        Map<String, Object> book = new HashMap<>();
        book.put("user_name", book_name);
        book.put("user_password", book_what);
        book.put("user_firstname", book_why);
        book.put("user_lastname", book_creator);

        collection_books.add(book);
    }

    // Read data collection Courses

    public List<Book> readCourses() {
        List<Book> books = new ArrayList<>();
        collection_books.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        books.add(new Book(document.getId()
                                , document.getString("book_name")
                                ,document.getString("book_what")
                                , document.getString("book_why")
                                , document.getString("book_creator")));
                    }

                }
            }
        });

        return books;

    }
}
