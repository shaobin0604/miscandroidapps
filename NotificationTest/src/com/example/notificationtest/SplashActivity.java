package com.example.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	private static final int WHAT_LOADING_COMPLETE = 1;
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_LOADING_COMPLETE:
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				
				finish();
				break;

			default:
				break;
			}
		}
		
	};
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		App app = (App) getApplication();
		
		app.setActivityShown(true);
		
		TextView name = (TextView) findViewById(R.id.who_am_i);
		
		name.setText("Splash");
		
		mHandler.sendEmptyMessageDelayed(WHAT_LOADING_COMPLETE, 5000);
		
		showNotification();
	}
	
	private void showNotification() {
		Notification notification = new Notification(android.R.drawable.stat_notify_chat, "test notification", System.currentTimeMillis());
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ReminderNotificationStarter.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, "test notification",
        		"test notification", contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Send the notification.
        notificationManager.notify(R.layout.main, notification);
	}
}
