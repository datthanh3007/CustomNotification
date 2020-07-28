package com.example.customnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.core.app.NotificationCompat;

public class MusicService extends Service {
    private static final String TAG = "Music Service";
    private static final String EXTRA_BUTTON_CLICKED = "EXTRA_BUTTON_CLICKED";
    private static final String CHANNEL_ID = "Music Channel";
    private static final Integer NOTIFICATION_ID = 12;

//    public MusicService() {
//    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra(EXTRA_BUTTON_CLICKED, -1);
            switch (id) {
                case R.id.play:
                    Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Play pressed.");
                    break;
                case R.id.prev:
                    Toast.makeText(context, "Previous", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Previous pressed.");
                    break;
                case R.id.next:
                    Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Next pressed.");
            }
        }
    };

    @Override
    public void onCreate() {
        createNotificationChannel();
        getApplicationContext().registerReceiver(receiver, new IntentFilter("ACTION_NOTIFICATION_BUTTON_CLICK"));
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RemoteViews notificationLayout =
                new RemoteViews(getPackageName(), R.layout.custom_notification);

        notificationLayout.setOnClickPendingIntent(R.id.play,
                onButtonNotificationClick(R.id.play));
        notificationLayout.setOnClickPendingIntent(R.id.prev,
                onButtonNotificationClick(R.id.prev));
        notificationLayout.setOnClickPendingIntent(R.id.next,
                onButtonNotificationClick(R.id.next));

        Notification
                notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(notificationLayout)
                .build();
        //NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //notificationManager.notify(1, notification);
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent("ACTION_NOTIFICATION_BUTTON_CLICK");
        intent.putExtra(EXTRA_BUTTON_CLICKED, id);
        return PendingIntent.getBroadcast(this, id, intent, 0);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
