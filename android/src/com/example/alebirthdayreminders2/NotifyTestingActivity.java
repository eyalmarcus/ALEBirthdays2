package com.example.alebirthdayreminders2;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;

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

	public void showNotification(View v) {
		NotificationCompat.Builder mBuilder =
			new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.notification_icon)
				.setContentTitle("Birthday notification")
				.setContentText("Your friend momo has a birthday")
				.setContentIntent(getPendingIntent());

		NotificationManager mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(0, mBuilder.build());
	}

	private Intent getEmailIntent() {
		String uriText =
		    "mailto:youremail@gmail.com" +
		    "?subject=" + Uri.encode("Happy birthday! יום הולדת שמח");
		Uri uri = Uri.parse(uriText);

		Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
		sendIntent.setData(uri);

		return sendIntent;
	}

	private PendingIntent getPendingIntent() {
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addNextIntent(getEmailIntent());
		PendingIntent pendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		return pendingIntent;
	}
}
