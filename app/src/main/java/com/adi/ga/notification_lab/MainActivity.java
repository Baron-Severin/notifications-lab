package com.adi.ga.notification_lab;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public final static int WIFI_NOTIFICATION = 1000;
    boolean wifiConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.notification_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifiConnected = isWifiConnected();
                setNotification(wifiConnected);
            }
        });

    }

    private boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void setNotification(Boolean wifiConnected) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder.setContentText("Hi, This is Android Notification Detail!");
//        mBuilder.setAutoCancel(false);

        Intent intent = new Intent(this, DismissalActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder.setContentTitle("Notification Alert, Click Me!");
//        mBuilder.setContentText("Hi, This is Android Notification Detail!");
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();

        Bundle bundle = new Bundle();

        //TODO: my call
        if (wifiConnected) {
            mBuilder.setContentTitle("WIFI is connected!");
            bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.cookie_small)).build();
            bundle.putBoolean("WIFI", true);
        } else {
            mBuilder.setContentTitle("No WIFI!");
            bundle.putBoolean("WIFI", false);
        }

        mBuilder.setStyle(bigPictureStyle);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// WIFI_NOTIFICATION allows you to update the notification later on.

        intent.putExtra("WIFI", bundle);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        mBuilder.setContentIntent(pIntent);
        mNotificationManager.notify(WIFI_NOTIFICATION, mBuilder.build());
    }


}
