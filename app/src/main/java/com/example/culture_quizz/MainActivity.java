package com.example.culture_quizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button sign;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        sign=findViewById(R.id.button);
        register=findViewById(R.id.register);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("Douaa")
                        && password.getText().toString().equals("azerty")){

                    Intent i1=new Intent(MainActivity.this, Quiz1.class);
                    startActivity(i1);
            }else{
                    //Toast affiche une le msg sous forme d'alert
                    Toast.makeText(getApplicationContext(),"le login ou le password sont incorrects",
                            Toast.LENGTH_SHORT).show();
                }
        }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(getApplicationContext(),com.example.culture_quizz.register.class);
                startActivity(i2);
            }
        });


    }
}