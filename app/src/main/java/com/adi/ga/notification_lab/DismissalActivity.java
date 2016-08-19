package com.adi.ga.notification_lab;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DismissalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dismissal);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("WIFI");
        boolean wifi = bundle.getBoolean("WIFI");

        if (wifi) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(MainActivity.WIFI_NOTIFICATION);
        }



    }
}
