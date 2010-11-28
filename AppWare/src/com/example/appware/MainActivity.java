package com.example.appware;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

public class MainActivity extends TabActivity implements TabContentFactory, OnTabChangeListener {
	
	private TabHost mTabHost;
	
	private Button mBtnLive;
	private Button mBtnAround;
	private Button mBtnTop;
	private Button mBtnSocial;
	private Button mBtnInstalled;
	
	private View mIndLive;
	private View mIndAround;
	private View mIndTop;
	private View mIndSocial;
	private View mIndInstalled;
	
	private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v == mBtnLive) {
				mTabHost.setCurrentTab(0);
			} else if (v == mBtnAround) {
				mTabHost.setCurrentTab(1);
			} else if (v == mBtnTop) {
				mTabHost.setCurrentTab(2);
			} else if (v == mBtnSocial) {
				mTabHost.setCurrentTab(3);
			} else if (v == mBtnInstalled) {
				mTabHost.setCurrentTab(4);
			}
		}
	};
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);
        
        mTabHost = getTabHost();
        
        mTabHost.addTab(mTabHost.newTabSpec("tab1")
                .setIndicator("tab1")
                .setContent(this));
        mTabHost.addTab(mTabHost.newTabSpec("tab2")
                .setIndicator("tab2")
                .setContent(this));
        mTabHost.addTab(mTabHost.newTabSpec("tab3")
                .setIndicator("tab3")
                .setContent(this));
        mTabHost.addTab(mTabHost.newTabSpec("tab4")
                .setIndicator("tab4")
                .setContent(this));
        mTabHost.addTab(mTabHost.newTabSpec("tab5")
                .setIndicator("tab5")
                .setContent(this));
        
        mTabHost.setOnTabChangedListener(this);
        
        mBtnLive = (Button) findViewById(R.id.Button_Bar_1);
        mBtnAround = (Button) findViewById(R.id.Button_Bar_2);
        mBtnTop = (Button) findViewById(R.id.Button_Bar_3);
        mBtnSocial = (Button) findViewById(R.id.Button_Bar_4);
        mBtnInstalled = (Button) findViewById(R.id.Button_Bar_5);
        
        mBtnLive.setOnClickListener(mTabOnClickListener);
        mBtnAround.setOnClickListener(mTabOnClickListener);
        mBtnTop.setOnClickListener(mTabOnClickListener);
        mBtnSocial.setOnClickListener(mTabOnClickListener);
        mBtnInstalled.setOnClickListener(mTabOnClickListener);
        
        mIndLive = findViewById(R.id.Indicator_1);
        mIndAround = findViewById(R.id.Indicator_2);
        mIndTop = findViewById(R.id.Indicator_3);
        mIndSocial = findViewById(R.id.Indicator_4);
        mIndInstalled = findViewById(R.id.Indicator_5);
    }

	@Override
	public View createTabContent(String tag) {
		final TextView tv = new TextView(this);
        tv.setText("Content for tab with tag " + tag);
        return tv;
	}

	@Override
	public void onTabChanged(String tabId) {
		final int on = R.drawable.appwidget_settings_ind_on_c;
		final int off = R.drawable.appwidget_settings_ind_off_c;
		
		mIndLive.setBackgroundResource("tab1".equals(tabId) ? on : off);
		mIndAround.setBackgroundResource("tab2".equals(tabId) ? on : off);
		mIndTop.setBackgroundResource("tab3".equals(tabId) ? on : off);
		mIndSocial.setBackgroundResource("tab4".equals(tabId) ? on : off);
		mIndInstalled.setBackgroundResource("tab5".equals(tabId) ? on : off);
	}	
    
    
}