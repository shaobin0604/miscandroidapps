package cn.yo2.aquarium.example.finishallactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

public class Activity1 extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Activity1");
		tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		setContentView(tv);
	}
	
	private static final int MENU_START_AC_2 = Menu.FIRST;
    private static final int MENU_FINISH_ALL = Menu.FIRST + 1;
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, MENU_START_AC_2, Menu.NONE, "Start Activity2").setIntent(new Intent(this, Activity2.class));
    	menu.add(Menu.NONE, MENU_FINISH_ALL, Menu.NONE, "Finish All");
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case MENU_FINISH_ALL:
    		mApp.finishAllActivity();
			break;

		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }
}
