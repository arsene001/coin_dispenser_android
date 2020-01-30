package com.app.coindispenser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.coindispenser.logic.CalculationTask;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BigDecimal amountDue = new BigDecimal(25.50);
    private EditText amountCaptured;
    private SharedPreferences preferences;

    private List<BigDecimal> denominations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        denominations = new ArrayList<>();
        denominations.add(new BigDecimal(100));
        denominations.add(new BigDecimal(50));
        denominations.add(new BigDecimal(20));
        denominations.add(new BigDecimal(10));

        preferences = getApplicationContext()
                .getSharedPreferences("preferences", 0);

        boolean loggedIn = preferences.getBoolean("loggedIn", false);

        amountCaptured = findViewById(R.id.amountCaptured);
        Button submitButton = findViewById(R.id.submit);
        Button logoutButton = findViewById(R.id.logout);

        if (!loggedIn) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        submitButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit: {
                BigDecimal amount = new BigDecimal(amountCaptured.getText().toString());

                if (denominations.contains(amount)) {

                    if (amount.compareTo(amountDue) > 0) {
                        final String message = "CALCULATION#amountDue:" + amountDue + "#amountCaptured:" + amount;

                        String result;
                        try {
                            result = new CalculationTask().execute(message).get();

                            System.out.println("RESULT " + result);

                            if (result != null) {
                                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                intent.putExtra("message", result);
                                startActivity(intent);
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Amount captured must be bigger than R" + amountDue, Toast.LENGTH_LONG);
                        toast.show();
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "The rand note value is incorrect. Allowed notes are 100, 50, 20 and 10", Toast.LENGTH_LONG);
                    toast.show();
                }

                break;
            }

            case R.id.logout: {

                SharedPreferences.Editor edit = preferences.edit();
                edit.putBoolean("loggedIn", false);
                edit.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                break;
            }
        }
    }
}
