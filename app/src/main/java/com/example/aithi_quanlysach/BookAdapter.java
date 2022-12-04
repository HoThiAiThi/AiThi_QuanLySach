package com.example.aithi_quanlysach;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    Random random = new Random();
    BooksFragment booksFragment;
    List<Book> books;
    Firebase firebase = new Firebase();
    public void setData(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }


    public BookAdapter(BooksFragment booksFragment, List<Book> books) {
        this.booksFragment= booksFragment;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        if(book == null){
            return;
        }
        int a = setColorIndex(position, 6);
        holder.item_background.setBackgroundColor(android.graphics.Color.argb(255, setColor(a).getR(), setColor(a).getG(), setColor(a).getB()));
        holder.item_bookID.setText(book.getBook_id());
        holder.item_bookName.setText(book.getBook_name());
        holder.item_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String book_id = holder.item_bookID.getText().toString().trim();
                showDialogDelete("Warning", "Are you sure?", book_id);
            }
        });

        holder.item_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(booksFragment.getContext(), EditBookActivity.class);
                String course_id = holder.item_bookID.getText().toString().trim();
                intent.putExtra("book_id", course_id);
                booksFragment.getContext().startActivity(intent);
            }
        });

        holder.item_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(booksFragment.getContext(), BookDetailActivity.class);
                String course_id = holder.item_bookID.getText().toString().trim();
                intent.putExtra("book_id", course_id);
                booksFragment.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(books != null) {
            return books.size();
        }
        return 0;
    }

    public class    BookViewHolder extends RecyclerView.ViewHolder{
        TextView  item_bookName, item_bookID;
        Button item_Delete, item_Edit, item_Detail;
        LinearLayout item_background;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            item_bookName = itemView.findViewById(R.id.item_bookName);
            item_bookID = itemView.findViewById(R.id.item_bookID);
            item_Delete = itemView.findViewById(R.id.item_Delete);
            item_Edit = itemView.findViewById(R.id.item_Edit);
            item_Detail = itemView.findViewById(R.id.item_Detail);
            item_background = itemView.findViewById(R.id.item_background);
        }
    }


    public static class Color {
        int r, g, b;

        public Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }

    public Color setColor(int index) {
        List<Color> colorList = new ArrayList<>();
        colorList.add(new Color(204, 255, 204));
        colorList.add(new Color(243, 236, 234));
        colorList.add(new Color(224, 235, 235));
        colorList.add(new Color(217, 238, 225));
        colorList.add(new Color(255, 255, 204));
        colorList.add(new Color(204, 242, 255));
        colorList.add(new Color(204, 204, 153));

        return colorList.get(index);
    }

    public int setColorIndex(int position, int index) {
        int a = (int) Math.floor(position/index);

        return position - a*index;
    }

    public void showDialogDelete(String warning, String message, String course_id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(booksFragment.getContext());
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_warning);
        alertDialog.setTitle(warning);
        alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebase.collection_books.document(course_id).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(booksFragment.getContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
                                Intent ref = new Intent(booksFragment.getContext(), MainActivity.class);
                                booksFragment.showData();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(booksFragment.getContext(), "Delete failling", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        alertDialog.show();
    }



}
