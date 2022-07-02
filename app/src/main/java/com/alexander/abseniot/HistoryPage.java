package com.alexander.abseniot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoryPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        times = new ArrayList<>();
        Log.wtf("test", "HistoryPage");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = getIntent().getStringExtra("uid");
        db.collection("days")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference doc = db.collection("days").document(document.getId()).collection("user").document(uid);
                                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                times.add(document.getDate("time").toString());
                                                RecyclerView recyclerView = findViewById(R.id.recyclerView_historyPage);
                                                recyclerView.setHasFixedSize(true);
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                recyclerView.setAdapter(new HistoryAdapter(times));
                                            } else {
                                                Log.d("test", "No such document");
                                            }
                                            Log.wtf("times", times.toString());
                                        } else {
                                            Log.d("test", "get failed with ", task.getException());
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.wtf("test", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

}
