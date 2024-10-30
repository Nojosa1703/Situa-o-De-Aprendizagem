package com.senai.estoquefarma;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.senai.estoquefarma.Controller.UserController;
import com.senai.estoquefarma.entities.User;

public class Register extends AppCompatActivity {

    private EditText inputEmail, inputPass, inputName;
    private Button btnRegister, btnLogin;
    private UserController userController;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.email2);
        inputName = findViewById(R.id.nome2);
        inputPass = findViewById(R.id.senha2);
        btnRegister = findViewById(R.id.register);
        btnLogin = findViewById(R.id.login2);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                User user = new User(null, inputName.getText().toString(), inputEmail.getText().toString(), inputPass.getText().toString());

                try {
                    userController = new UserController(Register.this);
                    Snackbar.make(view, userController.save(user), Snackbar.LENGTH_SHORT).show();
                } catch (Exception e) {
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}