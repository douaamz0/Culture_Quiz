package com.example.culture_quizz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.culture_quizz.MainActivity;
import com.example.culture_quizz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Score extends AppCompatActivity {

    TextView tvscore;
    Button btry, blogout, bViewRanking;
    int score;
    ProgressBar progressBar;

    int totalQuizzes = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        btry=findViewById(R.id.buttonTry);
        tvscore = findViewById(R.id.textViewPercentage);
        blogout = findViewById(R.id.buttonLogout);
        progressBar = findViewById(R.id.pb);

        bViewRanking = findViewById(R.id.buttonViewRanking);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        int progress = (score * 100) / totalQuizzes;

        // Définir le texte du TextView avec le score
        tvscore.setText("Score: " + progress + "%");

        progressBar.setMax(totalQuizzes);
        progressBar.setProgress(score);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Enregistrer le score du user dans la base
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail(); // Récupération de l'e-mail de l'utilisateur
            DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference().child("scores");
            scoresRef.child(userEmail.replace(".", ",")).setValue(score)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("enregistrement score","Score enregistré avec succès");
                        } else {
                            Log.d("enregistrement score","échec de l'enregistrement du score");
                        }
                    });
        } else {
            Log.d("enregistrement score","user non connecté");
        }


        blogout.setOnClickListener(view -> {
            Intent logoutIntent = new Intent(Score.this, MainActivity.class);
            startActivity(logoutIntent);
            finish();
        });
        btry.setOnClickListener(view -> {

            Intent tryAgainIntent = new Intent(Score.this, Quiz1.class);
            startActivity(tryAgainIntent);
            finish();
        });

        // Lorsque l'utilisateur clique sur le bouton "Voir Classement"
        bViewRanking.setOnClickListener(view -> {
            // Redirection vers l'activité de classement
            Intent rankingIntent = new Intent(Score.this, Classement.class);
            startActivity(rankingIntent);
        });
    }
}
