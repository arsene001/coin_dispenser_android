package com.app.coindispenser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.coindispenser.logic.LoginTask;

import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getApplicationContext().getSharedPreferences("preferences", 0);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login: {

                final String message = "LOGIN#username:" + username.getText() + "#password:" + password.getText();

                try {

                    Boolean result = new LoginTask().execute(message).get();

                    if (result) {
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean("loggedIn", true);
                        edit.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid login. Please enter correct login details",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            }
        }
    }
}
