package com.app.coindispenser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.coindispenser.logic.ServerHandler;

import java.io.IOException;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private ServerHandler serverHandler;
    private BigDecimal amountDue = new BigDecimal(25.50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferences", 0); // 0 - for private mode

        boolean loggedIn = preferences.getBoolean("loggedIn", false);

        if (loggedIn) {

            serverHandler = new ServerHandler();
            try {
                serverHandler.startConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            final EditText amountCaptured = findViewById(R.id.amountCaptured);
            Button submitButton = findViewById(R.id.submit);

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BigDecimal amount = new BigDecimal(amountCaptured.getText().toString());

                    final String message = "CALCULATION#amountDue:" + amountDue + "#amountCaptured:" + amount;

                    new Thread() {

                        public void run() {

                            try {

                                String result = serverHandler.calculateDenomination(message);

                                System.out.println(result);

                                if (result != null) {
                                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                    intent.putExtra("message", result);
                                    serverHandler.stopConnection();
                                    startActivity(intent);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }.start();

                }
            });


        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
