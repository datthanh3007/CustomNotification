package com.example.customnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStartService, btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.button_start);
        btnStopService = findViewById(R.id.button_stop);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService();
            }
        });
    }

    public void startService() {
        Intent intent = new Intent(this, MusicService.class);
        //intent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, intent);
    }

    public void stopService() {
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
    }
}