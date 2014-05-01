package com.example.alebirthdayreminders2;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class NotifyTestingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify_testing);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notify_testing, menu);
		return true;
	}

	public void setAlarm(View v) {
		// Set alarm time to X seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 10);

		AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
		        AlarmManager.INTERVAL_DAY, getPendingServiceIntent());

		Toast.makeText(this, "Alarm scheduled", Toast.LENGTH_SHORT).show();
	}

	public void activateService(View v) {
		startService(getServiceIntent());
	}

	private PendingIntent getPendingServiceIntent() {
		return PendingIntent.getService(
				this, 0, getServiceIntent(), PendingIntent.FLAG_UPDATE_CURRENT);
	}

	private Intent getServiceIntent() {
		return new Intent(this, NotificationService.class);
	}

}
