package com.app.coindispenser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.coindispenser.logic.ServerHandler;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private ServerHandler serverHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferences", 0);

        serverHandler = new ServerHandler();

        Button loginButton = findViewById(R.id.login);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call server to handle login mechanism

                final String message = "LOGIN#username:" + username.getText() + "#password:" + password.getText();

                new Thread() {

                    public void run() {

                        boolean result = serverHandler.loginUserIn(message);

                        if (result) {
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putBoolean("loggedIn", true);
                            edit.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }

                }.start();

            }
        });
    }
}
