package com.example.lrandom.notifydemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnStartService,btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartService=(Button)findViewById(R.id.btnStartService);
        btnStopService=(Button)findViewById(R.id.btnStopService);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MyService.class);
                startService(intent);
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MyService.class);
                stopService(intent);
            }
        });

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent =new Intent(getBaseContext(),MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),0,intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+3000,pendingIntent);
    }
}
