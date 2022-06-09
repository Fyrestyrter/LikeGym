package com.example.likegym;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registration extends AppCompatActivity {
    private static EditText username;
    private static EditText password;
    private static TextView attempts;
    private static Button login_btn;
    int attempt_counter = 5;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_registration);
        LoginButton();
    }

    public void LoginButton() {
        username = (EditText)findViewById(R.id.editTextPhone);
        password = (EditText)findViewById(R.id.editpass);
        attempts = (TextView)findViewById(R.id.attempts);
        login_btn = (Button)findViewById(R.id.reg);

        attempts.setText(Integer.toString(attempt_counter));

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("+79017124665")) {
                    if (password.getText().toString().equals("admin")) {
                        Toast.makeText(registration.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(registration.this, NavigationActivity.class);
                        startActivity(intent);
                    } else {
                        password.setError("Неверный пароль");

                    }
                } else {
                    username.setError("Неверный номер телефона");
                    attempt_counter--;
                    attempts.setText(Integer.toString(attempt_counter));
                }

                if (attempt_counter == 0) {
                    login_btn.setEnabled(false);
                }
            }
        });
    }
}