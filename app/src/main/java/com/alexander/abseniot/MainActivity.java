package com.alexander.abseniot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_email;
    EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        MaterialCardView btn_login = (MaterialCardView) findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("email", et_email.getText().toString()).whereEqualTo("password", et_password.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            String uid = null;
                            String username = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("test", document.getId() + " => " + document.getString("email"));;
                                uid = document.getId();
                                username = document.getString("name");

                            }
                            Log.w("currentUID", uid);
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            intent.putExtra("uid", uid);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        } else {
//                            Log.w("test", "Error getting documents.", task.getException());
                            Toast.makeText(MainActivity.this, "Your login credential is invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}