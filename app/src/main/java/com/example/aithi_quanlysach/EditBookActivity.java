package com.example.aithi_quanlysach;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditBookActivity extends AppCompatActivity {
    EditText editbook_Edtname, editbook_Edtwhat, editbook_Edtwhy, editbook_Edtcreator;
    ImageView editbook_btnBack;
    Button editbook_btnUpdate;
    TextView editbook_Textwhat, editbook_Textwhy, editbook_Textcreator;
    Firebase firebase = new Firebase();
    String book_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        connectID();
        editbook_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null ) {
            book_id = bundle.getString("book_id");
            DocumentReference doc = firebase.collection_books.document(book_id);
            doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            editbook_Edtname.setText(document.getString("book_name"));
                            editbook_Edtwhat.setText(document.getString("book_what"));
                            editbook_Edtwhy.setText(document.getString("book_why"));
                            editbook_Edtcreator.setText(document.getString("book_creator"));
                        } else {
                            Toast.makeText(EditBookActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        editbook_btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInputName(editbook_Edtname.getText().toString().trim())) {
                    showDialogOk("Warning", "Name can not blank");
                } else {
                    DocumentReference doc = firebase.collection_books.document(book_id);
                    Map<String, Object> book_update = new HashMap<>();
                    book_update.put("book_name", editbook_Edtname.getText().toString().trim());
                    book_update.put("book_what", editbook_Edtwhat.getText().toString().trim());
                    book_update.put("book_why", editbook_Edtwhy.getText().toString().trim());
                    book_update.put("book_creator", editbook_Edtcreator.getText().toString().trim());
                    showDialogEdit("Warning", "Are your sure?", doc, book_update);
                }

            }
        });




    }

    public void connectID() {
        editbook_Edtname = findViewById(R.id.editbook_Edtname);
        editbook_Edtwhat = findViewById(R.id.createbook_Edtcreator);
        editbook_Edtwhy = findViewById(R.id.createbook_Edtwhy);
        editbook_Edtcreator = findViewById(R.id.createbook_Edtcreator);

        editbook_btnBack = findViewById(R.id.createbook_btnBack);
        editbook_btnUpdate = findViewById(R.id.editbook_btnUpdate);

        editbook_Textwhat = findViewById(R.id.editbook_Textwhat);
        editbook_Textwhy = findViewById(R.id.editbook_Textwhy);
        editbook_Textcreator = findViewById(R.id.editbook_Textcreator);
    }

    public void gotoMain() {
        Intent intent = new Intent(EditBookActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void showDialogEdit(String warning, String message, DocumentReference doc, Map<String, Object> book_update) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_warning);
        alertDialog.setTitle(warning);
        alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.setNegativeButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                doc.update(book_update)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditBookActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditBookActivity.this, "Update failing", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        alertDialog.show();
    }

    public boolean checkInputName(String name) {
        if(name.isEmpty())
            return true;
        return false;
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
}