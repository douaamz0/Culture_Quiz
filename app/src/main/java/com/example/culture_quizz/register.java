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

    EditText name,email, password,password1;
    Button bregister;
    FirebaseAuth MyAuthentification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialisr le firebase
        FirebaseApp.initializeApp(getApplicationContext());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //Initialisation à partir du fichier xml
        name=findViewById(R.id.editTextTextName);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        password1=findViewById(R.id.editTextTextConfirmPassword);
        bregister=findViewById(R.id.button);

        //Instancier la variable MyAuthentification
        MyAuthentification=FirebaseAuth.getInstance();
        bregister.setOnClickListener(new View.OnClickListener() {

            //le listener du click recupere les champs et fait des tests
            //si tout va bien il appel la methode SignUp sinn il affiche le msg d'erreur
            @Override
            public void onClick(View v) {
                String inputName=name.getText().toString();
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                String pass1=password1.getText().toString();
                if( TextUtils.isEmpty(inputName) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass1))
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

    /*la méthode SignUp utilise la methode createWithEmailAndPassword pour creer le nouveau user
    si tout se passe bien elle affcihe un msg et redirige vers le l'activité de l'authentification
    sinon elle affiche le msg d'erreur
     */
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
                            //terminer l'activité actuelle
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

