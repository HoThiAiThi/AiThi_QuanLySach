package com.example.aithi_quanlysach;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class CreateBookActivity extends AppCompatActivity {
    EditText createbook_Edtname, createbook_Edtwhat, createbook_Edtwhy, createbook_Edtcreator;
    ImageView createbook_btnBack;
    Button createbook_btnCreate;
    String str_bookName, str_bookWhat, str_bookWhy, str_bookCreator;
    Firebase firebase = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        connectID();

        createbook_btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();
                if(checkInputName()) {
                    showDialogOk("Warning", "Name can not blank");
                } else {
                    Map<String, Object> book_create = new HashMap<>();
                    book_create.put("book_name", str_bookName);
                    book_create.put("book_what", str_bookWhat);
                    book_create.put("book_why", str_bookWhy);
                    book_create.put("book_creator", str_bookCreator);
                    showDialogCreate("Notifying", "Are you sure?", book_create);
                }

            }
        });

        createbook_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });




    }
    public void connectID() {
        createbook_Edtname = findViewById(R.id.createbook_Edtname);
        createbook_Edtwhat = findViewById(R.id.createbook_Edtwhat);
        createbook_Edtwhy = findViewById(R.id.createbook_Edtwhy);
        createbook_Edtcreator = findViewById(R.id.createbook_Edtcreator);

        createbook_btnBack = findViewById(R.id.createbook_btnBack);

        createbook_btnCreate = findViewById(R.id.createbook_btnCreate);
    }

    public void getInput(){
        str_bookName = createbook_Edtname.getText().toString().trim();
        str_bookWhat = createbook_Edtwhat.getText().toString().trim();
        str_bookWhy = createbook_Edtwhy.getText().toString().trim();
        str_bookCreator = createbook_Edtcreator.getText().toString().trim();
    }

    public void showDialogCreate(String warning, String message, Map<String, Object> course_create) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_notifications);
        alertDialog.setTitle(warning);
        alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.setNegativeButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebase.collection_books.add(course_create)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(CreateBookActivity.this, "Create successfully", Toast.LENGTH_SHORT).show();
                                gotoMain();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateBookActivity.this, "Create failing", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        alertDialog.show();
    }

    public void showDialogOk(String warning, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_warning);
        alertDialog.setTitle(warning);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();
    }

    public void gotoMain() {
        Intent intent = new Intent(CreateBookActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public boolean checkInputName() {
        if(str_bookName.isEmpty())
            return true;
        return false;
    }
}