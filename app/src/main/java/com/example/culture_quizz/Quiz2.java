package com.example.culture_quizz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quiz2 extends AppCompatActivity {

    Button next;
    RadioGroup rg;
    RadioButton rb;
    int score;
    TextView tvTimer;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        next = findViewById(R.id.btnNext);
        rg = findViewById(R.id.radioGroup);
        tvTimer = findViewById(R.id.tvTimer);

        // Start the countdown timer
        startTimer();

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        DatabaseReference questionRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://culturequizz-c2000-default-rtdb.firebaseio.com/questions/question2");
        questionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String question = dataSnapshot.child("question").getValue(String.class);
                    Log.d("QuestionText", "Question Text: " + question);

                    String option1 = dataSnapshot.child("option1").getValue(String.class);
                    String option2 = dataSnapshot.child("option2").getValue(String.class);
                    String option3 = dataSnapshot.child("option3").getValue(String.class);
                    String correctAnswer = dataSnapshot.child("correctAnswer").getValue(String.class);

                    TextView questionTextView = findViewById(R.id.question2);
                    questionTextView.setText(question);

                    ArrayList<String> optionsList = new ArrayList<>();
                    optionsList.add(option1);
                    optionsList.add(option2);
                    optionsList.add(option3);

                    rg.removeAllViews();

                    for (int i = 0; i < optionsList.size(); i++) {
                        RadioButton radioButton = new RadioButton(Quiz2.this);
                        radioButton.setText(optionsList.get(i));
                        rg.addView(radioButton);
                    }

                    // Tag the correct answer for later validation
                    rg.setTag(correctAnswer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error getting data", error.toException());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Veuillez choisir une réponse", Toast.LENGTH_SHORT).show();
                } else {
                    if(countDownTimer != null){
                        countDownTimer.cancel();
                    }
                    int selectedRadioButtonId = rg.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedAnswer = selectedRadioButton.getText().toString();
                    String correctAnswer = rg.getTag().toString();

                    if (isAnswerCorrect(selectedAnswer, correctAnswer)) {
                        score++;
                    }

                    goToNextQuestion();
                }
            }

            private boolean isAnswerCorrect(String selectedAnswer, String correctAnswer) {
                return selectedAnswer.equals(correctAnswer);
            }

        });
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                // If time's up, go to the next question without incrementing score

                if(!isFinishing()){
                    goToNextQuestion();
                }
            }
        }.start();
    }
    private void goToNextQuestion() {
        Intent nextQuizIntent = new Intent(getApplicationContext(), Quiz3.class);
        nextQuizIntent.putExtra("score", score);
        startActivity(nextQuizIntent);
        finish();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(countDownTimer !=null){
            countDownTimer.cancel();
        }
    }
}
