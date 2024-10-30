package com.senai.estoquefarma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.snackbar.Snackbar;
import com.senai.estoquefarma.Controller.UserController;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private EditText inputEmail;
    private EditText inputPass;
    private Button btnLogin;
    private MaterialSwitch switchLogin;
    private Button btnCriar;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        inputEmail = findViewById(R.id.email);
        inputPass = findViewById(R.id.pass);
        btnLogin = findViewById(R.id.login);
        switchLogin = findViewById(R.id.switchLogin);
        btnCriar = findViewById(R.id.criar);

        sp = getSharedPreferences(getString(R.string.prefs_login), Context.MODE_PRIVATE);
        inputEmail.setText(sp.getString(getString(R.string.prefs_email), ""));
        inputPass.setText(sp.getString(getString(R.string.prefs_pass), ""));
        switchLogin.setChecked(sp.getBoolean(getString(R.string.pref_switch), false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userController = new UserController(MainActivity.this);
                boolean login = userController.authentication(inputEmail.getText().toString(), inputPass.getText().toString());

                if (login) {
                    editor = sp.edit();
                    if (switchLogin.isChecked()) {
                        editor.putString(getString(R.string.prefs_email), inputEmail.getText().toString());
                        editor.putString(getString(R.string.prefs_pass), inputPass.getText().toString());
                        editor.putBoolean(getString(R.string.pref_switch), switchLogin.isChecked());
                    } else {
                        editor.remove(getString(R.string.prefs_email));
                        editor.remove(getString(R.string.prefs_pass));
                        editor.remove(getString(R.string.pref_switch));
                    }
                    editor.apply();
                    Intent i = new Intent(MainActivity.this, Home.class);
                    startActivity(i);
                    finish();
                } else {
                    Snackbar.make(view, "Usuário ou senha inválidos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
                finish();
            }
        });
    }
}

