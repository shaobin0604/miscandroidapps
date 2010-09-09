package com.pekall.mobiletv;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MbbmsServiceActivity extends Activity {
	private static final String TAG = MbbmsServiceActivity.class.getSimpleName();
	
	private static final int DLG_SUBSCRIBE_CONFIRM = 1;
	private static final int DLG_UNSUBSCRIBE_CONFIRM = 2;
	
	
	private TextView mTitle;
	
	private ViewFlipper mFlipper;
	
	private View mSubscribe;
	private View mUnsubscribe;
	
	private boolean mMbbmsServiceOn;
	
	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == mSubscribe) {
				showDialog(DLG_SUBSCRIBE_CONFIRM);
			} else if (v == mUnsubscribe) {
				showDialog(DLG_UNSUBSCRIBE_CONFIRM);
			}
		}
	};
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mbbms_activity);
		
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("业务管理");
		
		mFlipper = (ViewFlipper) findViewById(R.id.flipper);
		
		int display = mMbbmsServiceOn ? 0 : 1;
		mFlipper.setDisplayedChild(display);
		
		mSubscribe = findViewById(R.id.mbbms_service_subscribe);
		mSubscribe.setOnClickListener(mOnClickListener);
		
		mUnsubscribe = findViewById(R.id.mbbms_service_unsubscribe);
		mUnsubscribe.setOnClickListener(mOnClickListener);
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DLG_SUBSCRIBE_CONFIRM:
			return new AlertDialog.Builder(this).setTitle("确认开通手机电视业务?")
				.setMessage("")
				.setCancelable(true)
				.setPositiveButton("是", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						subscribe();
					}

				})
				.setNegativeButton("否", null)
				.create();
		case DLG_UNSUBSCRIBE_CONFIRM:
			return new AlertDialog.Builder(this).setTitle("确认注销手机电视业务?")
			.setMessage("")
			.setCancelable(true)
			.setPositiveButton("是", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					unsubscribe();
				}
			})
			.setNegativeButton("否", null)
			.create();
		default:
			break;
		}
		return super.onCreateDialog(id);
	}
	
	private void subscribe() {
		// TODO Auto-generated method stub
		
	}
	
	private void unsubscribe() {
		// TODO Auto-generated method stub
		
	}
}
