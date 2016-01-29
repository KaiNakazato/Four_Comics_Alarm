package com.example.alarm_service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.base_fourcomics_alarm.MainActivity;
import com.example.control_alarm.AlarmFragment;
import com.example.control_alarm.AlarmPreference;
import com.example.control_alarm.AlarmPreferenceDefinition;
import com.example.framecomics.R;

import java.util.Calendar;

/**
 * Created by toyozumi on 2016/01/22.
 */
public class AlarmService extends Service {

    public static final String TAG = "AlarmService";
    private static final int notificationId = 0;
    private NotificationManager notificationManager;
    private AlarmPreference ap;

    private int targetHour, targetMinute, selectCharacter;
    private boolean isSnooze, isVibration;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ap = new AlarmPreference(getApplicationContext());
        String hour, minute;
        targetHour = ap.getPreferenceTime(AlarmPreferenceDefinition.HOUR_KEY);
        hour = targetHour < 10 ? "0" + targetHour : "" + targetHour;
        targetMinute = ap.getPreferenceTime(AlarmPreferenceDefinition.MINUTE_KEY);
        minute = targetMinute < 10 ? "0" + targetMinute : "" + targetMinute;

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext());
        notificationBuilder.setOngoing(true);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
        notificationBuilder.setContentTitle("Four Comics Alarm");
        notificationBuilder.setContentText("目覚まし予約 " + hour + ":" + minute);
        notificationBuilder.setTicker("目覚ましがセットされています");
        notificationManager.notify(notificationId, notificationBuilder.build());

        Calendar calendar = Calendar.getInstance();
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        int nowMinute = calendar.get(Calendar.MINUTE);
        int nowSecond = calendar.get(Calendar.SECOND);

        long delay;
        if ((targetHour - nowHour) * 60 + (targetMinute - nowMinute) > 0) {
            delay = ((((targetHour - nowHour) * 60 + (targetMinute - nowMinute)) * 60) - nowSecond) * 1000;
        } else {
            delay = ((((((23 - nowHour) * 60 + (59 - nowMinute)) + (targetHour * 60) + targetMinute)) * 60) - nowSecond) * 1000;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopService(new Intent(getApplicationContext(), AlarmService.class));
                ap.editPreferenceBool(AlarmPreferenceDefinition.ALARM_SWITCH_KEY, false);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(TAG, true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplication().startActivity(intent);
            }
        }, delay);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        notificationManager.cancel(notificationId);
    }
}
