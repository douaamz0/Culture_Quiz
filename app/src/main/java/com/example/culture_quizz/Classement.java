package com.example.culture_quizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Classement extends AppCompatActivity {

    private LinearLayout layoutRankingList;
    Button blogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classement);
        blogout=findViewById(R.id.buttonLogoutC);
        layoutRankingList = findViewById(R.id.layoutRankingList);

        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference().child("scores");

        // Récupération des scores depuis Firebase
        scoresRef.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Effacer la liste actuelle de classement
                layoutRankingList.removeAllViews();

                int rank = 1;

                for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                    String userEmail = scoreSnapshot.getKey(); // Utiliser l'e-mail comme identifiant
                    int score = scoreSnapshot.getValue(Integer.class);

                    // Créer une vue pour afficher le classement
                    //charger le layout ranking_item et le rattacher au layout actuelle par layoutRaningList
                    View rankingView = LayoutInflater.from(Classement.this).inflate(R.layout.ranking_item, layoutRankingList, false);



                    TextView textViewUserId = rankingView.findViewById(R.id.textViewUserId);
                    TextView textViewScore = rankingView.findViewById(R.id.textViewScore);


                    textViewUserId.setText(userEmail+" :");
                    textViewScore.setText(String.valueOf(score*100/5)+"%");

                    layoutRankingList.addView(rankingView);

                    rank++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer l'erreur d'annulation
            }

        });
        blogout.setOnClickListener(view -> {
            Intent logoutIntent = new Intent(Classement.this, MainActivity.class);
            startActivity(logoutIntent);
            finish();
        });
    }
}
