package cn.yo2.aquarium.example.finishallactivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class App extends Application {
	
	private List<Activity> mActivityStack;
	
	public void addActivity(Activity activity) {
		mActivityStack.add(activity);
	}
	
	public void removeActivity(Activity activity) {
		mActivityStack.remove(activity);
	}
	
	public void finishAllActivity() {
		for (int i = mActivityStack.size() - 1; i >= 0; i--) {
			Activity activity = mActivityStack.get(i);
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mActivityStack = new ArrayList<Activity>();
	}
}
