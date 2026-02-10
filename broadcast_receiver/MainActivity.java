package com.example.exp9;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_SMS_PERMISSION = 101;
    private static final int REQ_NOTIFICATION_PERMISSION = 200;

    TextView tvStatus;
    Button btnStart, btnStop, btnSimulate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        btnStart = findViewById(R.id.btnStartService);
        btnStop = findViewById(R.id.btnStopService);
        btnSimulate = findViewById(R.id.btnSimulateEmergency);

        tvStatus.setText("Emergency monitoring is OFF");

        requestSmsPermissionIfNeeded();
        requestNotificationPermissionIfNeeded();

        // Start foreground service
        btnStart.setOnClickListener(v -> {
            Intent i = new Intent(this, EmergencyService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(i);
            } else {
                startService(i);
            }
            tvStatus.setText("Emergency monitoring is ON");
        });

        // Stop service
        btnStop.setOnClickListener(v -> {
            stopService(new Intent(this, EmergencyService.class));
            tvStatus.setText("Emergency monitoring is OFF");
        });

        // Simulate emergency (explicit broadcast)
        btnSimulate.setOnClickListener(v -> {
            Intent intent = new Intent(this, EmergencyAlertReceiver.class);
            intent.setAction("com.example.emergency.EMERGENCY_TRIGGERED");
            sendBroadcast(intent);

            Toast.makeText(this, "Emergency triggered", Toast.LENGTH_SHORT).show();
        });

        // Open SMS app when notification is tapped
        if (getIntent() != null && getIntent().getBooleanExtra("OPEN_SMS", false)) {
            openSmsApp();
        }
    }

    private void openSmsApp() {
        String phoneNumber = "+917904642843";
        String message =
                "Emergency Alert: Senior citizen may need immediate assistance. Please respond quickly.";

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
        smsIntent.putExtra("sms_body", message);
        startActivity(smsIntent);
    }

    private void requestSmsPermissionIfNeeded() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQ_SMS_PERMISSION
            );
        }
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQ_NOTIFICATION_PERMISSION
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Toast.makeText(this,
                "Permission response received",
                Toast.LENGTH_SHORT).show();
    }
}
