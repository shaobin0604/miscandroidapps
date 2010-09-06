package com.example.tabbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tabbar.TabBar.OnTabSelectedListener;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		setContentView(R.layout.main);
		
		final TextView text = (TextView) findViewById(R.id.content);
		
		TabBar tabBar = (TabBar) findViewById(R.id.tabbar);
		
		tabBar.addTab(new TabBar.TabSpec("AAA", null));
		tabBar.addTab(new TabBar.TabSpec("BBB", null));
		tabBar.addTab(new TabBar.TabSpec("CCC", null));
		tabBar.addTab(new TabBar.TabSpec("DDD", null));
		tabBar.addTab(new TabBar.TabSpec("EEE", null));
		tabBar.addTab(new TabBar.TabSpec("FFF", null));
		tabBar.addTab(new TabBar.TabSpec("GGG", null));
		tabBar.addTab(new TabBar.TabSpec("HHH", null));
		tabBar.addTab(new TabBar.TabSpec("III", null));
		tabBar.addTab(new TabBar.TabSpec("JJJ", null));
		tabBar.addTab(new TabBar.TabSpec("KKK", null));
		tabBar.addTab(new TabBar.TabSpec("LLL", null));
		tabBar.addTab(new TabBar.TabSpec("MMM", null));
		tabBar.addTab(new TabBar.TabSpec("NNN", null));
		tabBar.addTab(new TabBar.TabSpec("OOO", null));
		tabBar.addTab(new TabBar.TabSpec("PPP", null));
		tabBar.addTab(new TabBar.TabSpec("QQQ", null));
		tabBar.addTab(new TabBar.TabSpec("RRR", null));
		tabBar.addTab(new TabBar.TabSpec("SSS", null));
		tabBar.addTab(new TabBar.TabSpec("TTT", null));
		tabBar.addTab(new TabBar.TabSpec("UUU", null));
		tabBar.addTab(new TabBar.TabSpec("VVV", null));
		tabBar.addTab(new TabBar.TabSpec("WWW", null));
		tabBar.addTab(new TabBar.TabSpec("XXX", null));
		
		tabBar.pack();
		
		tabBar.setOnTabSelectedListener(new OnTabSelectedListener() {
			
			@Override
			public void onTabSelected(int index) {
				text.setText("tab " + index);
			}
		});
		
		tabBar.setCurrentTab(0);
    }
}