package com.adi.ga.notification_lab;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        Button button = (Button) findViewById(R.id.make_call);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
            }
        });



    }


    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:4254431666"));
        getPermissionToCall();
        try {
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    @TargetApi(23)
    public void getPermissionToCall() {
        if ((Build.VERSION.SDK_INT >= 23)) {
            // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
            // checking the build version since Context.checkSelfPermission(...) is only available
            // in Marshmallow
            // 2) Always check for permission (even if permission has already been granted)
            // since the user can revoke permissions at any time through Settings
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                // The permission is NOT already granted.
                // Check if the user has been asked about this permission already and denied
                // it. If so, we want to give more explanation about why the permission is needed.
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CALL_PHONE)) {
                    // Show our own UI to explain to the user why we need to read the contacts
                    // before actually requesting the permission and showing the default UI
                }

                // Fire off an async request to actually get the permission
                // This will show the standard permission request dialog UI
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        5);
            }
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == 5) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Use phone permission granted", Toast.LENGTH_SHORT).show();
                makeCall();
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
//                boolean showRationale = shouldShowRequestPermissionRationale( this, Manifest.permission.READ_CONTACTS)

//                if (showRationale) {
                    // do something here to handle degraded mode
//                    else {
                        Toast.makeText(this, "Use phone permission denied", Toast.LENGTH_SHORT).show();
//                    }
                }
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }


    }
