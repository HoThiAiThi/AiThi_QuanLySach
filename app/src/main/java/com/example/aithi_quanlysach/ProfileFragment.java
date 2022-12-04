package com.example.aithi_quanlysach;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;


public class ProfileFragment extends Fragment {
    TextView profile_User_name, profile_User_id, profile_User_fullname, profile_User_phone, profile_User_email;
    Button progile_signOut;
    SharedPreferences sharedPreferences;
    Firebase firebase;
    String user_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectID(view);
        firebase = new Firebase();
        sharedPreferences = getContext().getSharedPreferences("user_name", Context.MODE_PRIVATE);
        user_name = sharedPreferences.getString("user_name", "Null");

        showData(user_name);

        progile_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSigin();
            }
        });
    }

    public void connectID(View view) {
        profile_User_name = view.findViewById(R.id.profile_User_name);
        profile_User_id = view.findViewById(R.id.profile_User_id);
        profile_User_fullname = view.findViewById(R.id.profile_User_fullname);
        profile_User_phone = view.findViewById(R.id.profile_User_phone);
        profile_User_email = view.findViewById(R.id.profile_User_email);
        progile_signOut = view.findViewById(R.id.progile_signOut);
    }

    public void showData(String user_name) {
        firebase.collection_users.whereEqualTo("user_name", user_name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            QuerySnapshot doc = task.getResult();
                            profile_User_name.setText(doc.getDocuments().get(0).getString("user_email"));
                            if(doc.size() == 0) {
                                Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
                            } else {
                                profile_User_name.setText(doc.getDocuments().get(0).getString("user_name"));
                                profile_User_id.setText("ID: " + doc.getDocuments().get(0).getId());
                                profile_User_fullname.setText(doc.getDocuments().get(0).getString("user_firstname") + " " + doc.getDocuments().get(0).getString("user_lastname"));
                                profile_User_phone.setText(doc.getDocuments().get(0).getString("user_phone"));
                                profile_User_email.setText(doc.getDocuments().get(0).getString("user_email"));
                            }

                        }
                    }
                });
    }

    public void gotoSigin() {
        Intent intent = new Intent(getContext(), SigninActivity.class);
        startActivity(intent);
    }

}