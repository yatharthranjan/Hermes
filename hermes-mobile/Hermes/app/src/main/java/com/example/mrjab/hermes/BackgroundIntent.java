package com.example.mrjab.hermes;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by yatharth on 09/03/17.
 */

public class BackgroundIntent extends IntentService {
        public BackgroundIntent() {
            super("MyTestService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            // Do the task here
            Log.i("MyTestService", "Service running");
        }
}
