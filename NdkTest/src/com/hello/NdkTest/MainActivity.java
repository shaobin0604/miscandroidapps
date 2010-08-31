package com.hello.NdkTest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText(stringFromJNI());
		setContentView(tv);
	}

	public native String stringFromJNI();

	static {
		System.loadLibrary("hello-jni");
	}
}