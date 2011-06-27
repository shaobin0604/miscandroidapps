package cn.yo2.aquarium.example.finishallactivity;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	protected App mApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mApp = (App) getApplication();
		mApp.addActivity(this);
	}
	
	
	@Override
	protected void onDestroy() {
		mApp.removeActivity(this);
		super.onDestroy();
	}
}
