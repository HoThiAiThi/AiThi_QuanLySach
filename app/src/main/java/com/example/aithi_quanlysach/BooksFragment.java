package com.example.aithi_quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class BooksFragment extends Fragment {
    ImageButton image_createBook;
    RecyclerView fragment_bookRecycleView;
    BookAdapter bookAdapter;
    List<Book> books;
    Firebase firebase = new Firebase();
    CollectionReference cou = FirebaseFirestore.getInstance().collection("Books");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Kết nối Id
        fragment_bookRecycleView = view.findViewById(R.id.fragment_bookRecycleView);
        image_createBook = view.findViewById(R.id.image_createBook);


        // Cài đặt danh sách
        books = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);

        bookAdapter = new BookAdapter(this, books);
        fragment_bookRecycleView.setLayoutManager(gridLayoutManager);
        fragment_bookRecycleView.setAdapter(bookAdapter);
        showData();

        // Xử lý sự kiện
        image_createBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateBookActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showData() {
        books.clear();
        firebase.collection_books.get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    bookAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}