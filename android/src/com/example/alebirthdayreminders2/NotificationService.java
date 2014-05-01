package com.example.alebirthdayreminders2;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationService extends IntentService {

	public NotificationService() {
		super("NotificationService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		Log.i("NotificationService", "Service activated");
		showNotification();
	}

	private void showNotification() {
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.notification_icon)
				.setContentTitle("Birthday notification")
				.setContentText("Your friend momo has a birthday")
				.setContentIntent(getPendingEmailIntent());

		NotificationManager mNotificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}

	private PendingIntent getPendingEmailIntent() {
		String uriText =
				"mailto:youremail@gmail.com" +
						"?subject=" + Uri.encode("Happy birthday! יום הולדת שמח");
		Uri uri = Uri.parse(uriText);

		Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
		sendIntent.setData(uri);

		return PendingIntent.getActivity(
				this, 0, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

}
