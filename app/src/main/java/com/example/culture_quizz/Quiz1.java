package com.example.culture_quizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz1 extends AppCompatActivity {

    Button next;
    RadioGroup rg;
    RadioButton rb;
    String correctAnswer = "Victor Hugo";
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);
        next = findViewById(R.id.btnNext);
        rg = findViewById(R.id.radioGroup);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Veuillez choisir une réponse",
                            Toast.LENGTH_SHORT).show();
                } else {
                    rb = findViewById(rg.getCheckedRadioButtonId());
                    if (rb.getText().toString().equals(correctAnswer)) score += 1;
                    Intent i1 = new Intent(getApplicationContext(), Quiz2.class);
                    i1.putExtra("score", score);
                    startActivity(i1);
                }


            }
        });
    }
}


