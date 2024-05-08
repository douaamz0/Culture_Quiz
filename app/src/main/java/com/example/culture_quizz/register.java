package com.example.culture_quizz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.culture_quizz.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    EditText email, password,password1;
    Button bregister;
    FirebaseAuth MyAuthentification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        password1=findViewById(R.id.editTextTextConfirmPassword);
        bregister=findViewById(R.id.button);
        MyAuthentification=FirebaseAuth.getInstance();
        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                String pass1=password1.getText().toString();
                if(TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass1))
                {
                    Toast.makeText(register.this,"Veillez inserer les champs obligatoires !",
                            Toast.LENGTH_SHORT).show();
                    return;

                }
                if(pass.length()<6)
                {
                    Toast.makeText(register.this,"nombre de caractère insuffisants !",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pass.equals(pass1))
                {
                    Toast.makeText(register.this,"les mots de passes ne correspondent pas !",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                SignUp(mail,pass);
            }
        });

    }
    public void SignUp(String mail,String password)
    {
        MyAuthentification.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"Enregistrement réussi ! "
                                    ,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(register.this,"Enregistrement échoué "+task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

