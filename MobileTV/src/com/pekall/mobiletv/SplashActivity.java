package com.pekall.mobiletv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	private static final int STEP_PROGRESS = 10;
	private static final int STEP_DELAY = 1000;
	
	private TextView mTitle;
	private TextView mWelcomeInfo;
	
	private ProgressBar mProgressBar;
	
	private Handler mProgressHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			final int progress = msg.arg1;
			mProgressBar.setProgress(progress);
			
			if (progress < 100) {
				Message next = Message.obtain(this);
				next.arg1 = progress + STEP_PROGRESS;
				sendMessageDelayed(next, STEP_DELAY);
			} else {
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}
		
	};
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash_activity);
		
		mWelcomeInfo = (TextView) findViewById(R.id.welcome_info);
		mProgressBar = (ProgressBar) findViewById(R.id.progress);
		
		mTitle = (TextView) findViewById(R.id.title);
		
		mTitle.setText("手机电视");
		
		startAnimation();
	}
	
	private void startAnimation() {
		Message msg = Message.obtain(mProgressHandler);
		msg.arg1 = STEP_PROGRESS;
		
		mProgressHandler.sendMessageDelayed(msg, STEP_DELAY);
	}
}
