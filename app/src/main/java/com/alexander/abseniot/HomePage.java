package com.alexander.abseniot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yy");
//        Log.wtf("test", date.format(cal.getTime()));

        String uid = getIntent().getStringExtra("uid");
        String username = getIntent().getStringExtra("username");
//        Log.wtf("test", uid);
        final String[] checkinDate = {null};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView tv_username = (TextView) findViewById(R.id.tv_username);
        tv_username.setText("Welcome " + username);

        MaterialCardView sudahAbsen = (MaterialCardView) findViewById(R.id.sudahAbsen);
        MaterialCardView belumAbsen = (MaterialCardView) findViewById(R.id.belumAbsen);

        DocumentReference doc = db.collection("days").document(date.format(cal.getTime())).collection("user").document(uid);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

//                        Log.d("test", "DocumentSnapshot data: " + document.getDate("time"));
                        checkinDate[0] = document.getDate("time").toString();
//                        Log.wtf("date", checkinDate[0]);
                        TextView tv_time = (TextView) findViewById(R.id.tv_checkinTime);
                        tv_time.setText(checkinDate[0]);
                        sudahAbsen.setVisibility(View.VISIBLE);
                        belumAbsen.setVisibility(View.GONE);
                    } else {
                        sudahAbsen.setVisibility(View.GONE);
                        belumAbsen.setVisibility(View.VISIBLE);
//                        Log.d("test", "No such document");
                    }
                } else {
//                    Log.d("test", "get failed with ", task.getException());
                }
            }
        });

        Button btn_history = (Button) findViewById(R.id.history);
        btn_history.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HistoryPage.class);

            intent.putExtra("uid", uid);
            startActivity(intent);
        });

        Button btn_logout = (Button) findViewById(R.id.logout);
        btn_logout.setOnClickListener(view -> {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        });
    }

}