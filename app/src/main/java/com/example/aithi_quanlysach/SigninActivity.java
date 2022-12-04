package com.example.aithi_quanlysach;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public class SigninActivity extends AppCompatActivity {
    EditText signin_username, signin_password;
    TextView signin_linksignup;
    CheckBox signin_cBoxremember, signin_cBoxshpassword;
    Button signin_btn;
    String user_name, user_password;
    Firebase firebase = new Firebase();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        connectIDLayout();
        sharedPreferences =getSharedPreferences("user_name", MODE_PRIVATE);

        signin_username.setText(sharedPreferences.getString("save_user_name", ""));
        signin_password.setText(sharedPreferences.getString("save_user_password", ""));
        signin_cBoxremember.setChecked(sharedPreferences.getBoolean("save_checked", false));

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            signin_username.setText(bundle.getString("user_name"));
            signin_password.setText(bundle.getString("user_password"));
        }


        signin_linksignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignup();
            }
        });


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();
                if(checkInput() == true) {
                    showDialogOk("Warning","Can not blank");
                } else {
                    firebase.collection_users.whereEqualTo("user_name", user_name)
                            .whereEqualTo("user_password", user_password)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()) {
                                        QuerySnapshot document = task.getResult();
                                        if(document.isEmpty()) {
                                            Toast.makeText(SigninActivity.this, "Sign in failling", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(SigninActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("user_name", user_name);
                                            editor.putString("user_password", user_password);
                                            editor.commit();
                                            if(signin_cBoxremember.isChecked()) {
                                                SharedPreferences.Editor save = sharedPreferences.edit();
                                                save.putString("save_user_name", user_name);
                                                save.putString("save_user_password", user_password);
                                                save.putBoolean("save_checked", true);
                                                save.commit();
                                            } else {
                                                SharedPreferences.Editor save = sharedPreferences.edit();
                                                save.remove("save_user_name");
                                                save.remove("save_user_password");
                                                save.remove("save_checked");
                                                save.commit();
                                            }
                                            gototMain(user_name);

                                        }
                                    }
                                }
                            });
                }

            }
        });

        signin_cBoxshpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    signin_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    signin_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


    }

    public void connectIDLayout() {
        signin_username = findViewById(R.id.signin_edtUsername);
        signin_password = findViewById(R.id.signin_edtPassword);
        signin_linksignup = findViewById(R.id.link_signup);
        signin_btn = findViewById(R.id.signin_btnSignin);
        signin_cBoxremember = findViewById(R.id.signin_cBoxremember);
        signin_cBoxshpassword = findViewById(R.id.signin_cBoxshpassword);
    }

    public void gotoSignup() {
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
    }


    public void getInput() {
        user_name = signin_username.getText().toString().trim();
        user_password = signin_password.getText().toString().trim();
    }

    public boolean checkInput() {
        if(user_name.isEmpty() || user_password.isEmpty()) {
            return true;
        }
        return false;
    }

    public void showDialogOk(String warning, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_warning);
        alertDialog.setTitle(warning);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }

    public void gototMain(String user_name) {
        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        intent.putExtra("user_name", user_name);
        startActivity(intent);
    }
}