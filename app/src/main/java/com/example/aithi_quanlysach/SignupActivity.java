package com.example.aithi_quanlysach;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SignupActivity extends AppCompatActivity {
    EditText signup_username, signup_password, signup_lastname, signup_firstname, signup_phone, signup_email;
    TextView signup_linksignin;
    Button signup_btn;
    String user_name, user_password, user_lastname, user_firstname, user_phone, user_email;
    Firebase firebase = new Firebase();
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        connectIDLayout();

        signup_linksignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();
                if(checkInput()) {
                    showDialogOk("Warning","Can not blank", R.drawable.ic_warning);
                } else {
                    Map<String, Object> user = new HashMap<>();
                    user.put("user_name", user_name);
                    user.put("user_password", user_password);
                    user.put("user_lastname", user_lastname);
                    user.put("user_firstname", user_firstname);
                    user.put("user_phone", user_phone);
                    user.put("user_email", user_email);

                    firebase.collection_users.whereEqualTo("user_name", user_name)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()) {
                                        QuerySnapshot document = task.getResult();
                                        if(document.isEmpty()) {
                                            firebase.collection_users.add(user);
                                            showDialogNoButton("Notifying", "Sign up successfully", R.drawable.ic_notifications);
                                            timer.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    gotoSignin();
                                                }
                                            }, 2000);

                                        } else {
                                            showDialogOk("Notifying", "Username already exists", R.drawable.ic_notifications);
                                        }
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignupActivity.this, "Sign up failing", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });


    }

    public void connectIDLayout() {
        signup_username = findViewById(R.id.signup_edtUsername);
        signup_password = findViewById(R.id.signup_edtPassword);
        signup_lastname = findViewById(R.id.signup_edtLasttname);
        signup_firstname = findViewById(R.id.signup_edtFirstname);
        signup_phone = findViewById(R.id.signup_edtPhone);
        signup_email = findViewById(R.id.signup_edtEmail);
        signup_linksignin = findViewById(R.id.link_signin);
        signup_btn = findViewById(R.id.signup_btnSignup);
    }

    public void gotoSignin() {
        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_password", user_password);
        startActivity(intent);
    }

    public void getInput() {
        user_name = signup_username.getText().toString().trim();
        user_password = signup_password.getText().toString().trim();
        user_firstname = signup_lastname.getText().toString().trim();
        user_lastname = signup_firstname.getText().toString().trim();
        user_phone = signup_phone.getText().toString().trim();
        user_email = signup_email.getText().toString().trim();
    }


    public boolean checkInput() {
        if(user_name.isEmpty() || user_password.isEmpty() || user_firstname.isEmpty() || user_lastname.isEmpty() || user_phone.isEmpty() || user_email.isEmpty()) {
            return true;
        }
        return false;
    }

    public void showDialogOk(String warning, String message, int icon) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setIcon(icon);
        alertDialog.setTitle(warning);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }

    public void showDialogNoButton(String warning, String message, int icon) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setIcon(icon);
        alertDialog.setTitle(warning);
        alertDialog.show();
    }


}