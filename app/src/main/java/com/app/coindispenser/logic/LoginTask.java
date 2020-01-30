package com.app.coindispenser.logic;

import android.os.AsyncTask;

import java.io.IOException;

public class LoginTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... strings) {

        boolean result = false;
        try {
            ServerHandler serverHandler = new ServerHandler();
            serverHandler.startConnection();
            result = serverHandler.loginUserIn(strings[0]);
            serverHandler.stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
