package com.pekall.mobiletv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentPreviewActivity extends Activity {
	private static final String TAG = ContentPreviewActivity.class.getSimpleName();
	
	private ImageView mPreviewImage;
	private TextView mContentName;
	private TextView mContentTime;
	private TextView mContentIntro;
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_preview);
		
		mPreviewImage = (ImageView) findViewById(R.id.content_preview_img);
		mContentName = (TextView) findViewById(R.id.content_name);
		mContentTime = (TextView) findViewById(R.id.content_time);
		mContentIntro = (TextView) findViewById(R.id.content_preview_intro);
		
		mContentIntro.setText(R.string.content_preview_intro);
	}
}
