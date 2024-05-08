package com.example.culture_quizz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.culture_quizz.MainActivity;
import com.example.culture_quizz.R;

public class Score extends AppCompatActivity {

    TextView tvscore;
    Button btry, blogout;
    int score;
    int totalQuizzes = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tvscore = findViewById(R.id.textViewPercentage);
        blogout = findViewById(R.id.buttonLogout);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        int progress = (score * 100) / totalQuizzes;

        // Définir le texte du TextView avec le score
        tvscore.setText("Score: " + progress +"%");

        blogout.setOnClickListener(view -> {
            Intent logoutIntent = new Intent(Score.this, MainActivity.class);
            startActivity(logoutIntent);
            finish();
        });
    }

}