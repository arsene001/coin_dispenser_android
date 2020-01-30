package com.app.coindispenser.logic;

import android.os.AsyncTask;

import java.io.IOException;

public class CalculationTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        ServerHandler serverHandler = new ServerHandler();
        String result = "";
        try {
            serverHandler.startConnection();
            result = serverHandler.calculateDenomination(strings[0]);
            serverHandler.stopConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
