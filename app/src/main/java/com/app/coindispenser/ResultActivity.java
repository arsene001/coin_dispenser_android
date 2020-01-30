package com.app.coindispenser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView breakdownText = findViewById(R.id.breakdown);
        TextView totalText = findViewById(R.id.total);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String message = extras.getString("message");

            String total = message.split("#")[0];
            totalText.setText(String.format("Total R%s", total));

            String data = message.split("#")[1].replace(":", "\n");
            breakdownText.setText(data);

            System.out.println(message);
        }

        Button resetButton = findViewById(R.id.reset);
        resetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset: {

                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);

                break;
            }
        }
    }
}
