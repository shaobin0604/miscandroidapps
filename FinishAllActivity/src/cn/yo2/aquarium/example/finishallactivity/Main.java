package cn.yo2.aquarium.example.finishallactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Main extends BaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    
    private static final int MENU_START_AC_1 = Menu.FIRST;
    private static final int MENU_FINISH_ALL = Menu.FIRST + 1;
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, MENU_START_AC_1, Menu.NONE, "Start Activity1").setIntent(new Intent(this, Activity1.class));
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