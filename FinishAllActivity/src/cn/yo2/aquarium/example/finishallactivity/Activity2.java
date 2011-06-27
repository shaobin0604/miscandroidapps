package cn.yo2.aquarium.example.finishallactivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

public class Activity2 extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView tv = new TextView(this);
		tv.setText("Activity2");
		tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		setContentView(tv);
	}
	
    private static final int MENU_FINISH_ALL = Menu.FIRST;
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
