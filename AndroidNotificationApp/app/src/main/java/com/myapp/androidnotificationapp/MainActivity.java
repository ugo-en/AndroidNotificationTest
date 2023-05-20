package com.myapp.androidnotificationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private String CHANNEL_ID = "com.myapp.androidnotificationapp";

    private Button alertBtn, customNotifBtn, toastBtn, snackBarBtn;

    @SuppressLint("RemoteViewLayout")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertBtn = findViewById(R.id.alert_dialog_btn);
        customNotifBtn = findViewById(R.id.custom_notifications_btn);
        toastBtn = findViewById(R.id.toast_notification_btn);
        snackBarBtn = findViewById(R.id.snackbar_notification_btn);

        // create notification channel
        createNotificationChannel();

        snackBarBtn.setOnClickListener((v) -> {
            View snackBar = findViewById(R.id.snackbar);
            Snackbar.make(snackBar, "Snackbar babyyyy", Snackbar.LENGTH_LONG).show();
        });

        toastBtn.setOnClickListener((v -> {
            Toast.makeText(getBaseContext(), "Sample Toast Message", Toast.LENGTH_LONG).show();
        }));

        customNotifBtn.setOnClickListener((v) -> {
            // Create notification channel
            createNotificationChannel();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.music_icon_foreground)
                    .setContentTitle("Sample Notification")
                    .setContentText("This is a sample notification")
                    .setPriority(NotificationCompat.PRIORITY_MAX);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            // NotificationId is a unique int for each notification that you must define
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Request the missing permission(s)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 200);
                }
                return;
            }
            notificationManager.notify(109910, builder.build());
        });

        alertBtn.setOnClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title")
                    .setMessage("Message")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Handle OK button click
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        // Handle Cancel button click
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_HIGH
            ); 
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}