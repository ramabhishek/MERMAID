package com.example.mermaid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private ToggleButton toggleButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        timePicker = findViewById(R.id.timePicker);
        toggleButton = findViewById(R.id.togglebtn);
        alarmManager=(AlarmManager) getSystemService (ALARM_SERVICE);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    Toast.makeText(AlarmActivity.this, "alarm is ON", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(AlarmActivity.this,MyReceiver.class );
                    pendingIntent= pendingIntent.getBroadcast(AlarmActivity.this,0,i,0);

                    Calendar calender=Calendar.getInstance();
                    calender.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calender.set(Calendar.MINUTE, timePicker.getMinute());

                    long  time = calender.getTimeInMillis() - ( calender.getTimeInMillis() % 60000);

                    if(System.currentTimeMillis()>time){
                        if(calender.AM_PM==0){
                            time=time + (1000*60*60*12);
                        }
                        else{
                            time=time+ (1000*60*60*24);


                        }
                    }

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,0,pendingIntent);





                } else {
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(AlarmActivity.this, "alarm is OFF", Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

}


