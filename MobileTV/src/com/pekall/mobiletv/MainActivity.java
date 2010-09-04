package com.pekall.mobiletv;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.pekall.mobiletv.adapter.MockContentItemAdapter;
import com.pekall.mobiletv.adapter.MockServiceItemAdapter;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private static final int TAB_COUNT = 3;
	
	private String[] ALL_SERVICE_NAMES = {
    		"中央一台",
    		"中央二台",
    		"中央三台",
    		"中央四台",
    		"中央五台",
    		"中央六台",
    		"中央七台",
    		"中央八台",
    		"中央九台",
    		"中央十台",
    };
	
	private String[] FAVORITE_SERVICE_NAMES = {
    		"中央一台",
    		"中央二台",
    		"中央三台",
    		"中央四台",
    };
	
	private static final String[] REMIND_CONTENT_NAMES = {
		"新闻联播",
		"科技新闻",
		"动物世界",
		"黄金剧场",
	};
	
	private TextView mTabServiceAll;
	private TextView mTabServiceFavorite;
	private TextView mTabContentRemind;
	
	private TextView[] mTabs;
	
	private ViewFlipper mViewFlipper;
	
	private ListView mListServiceAll;
	private ListView mListServiceFavorite;
	private ListView mListContentRemind;
	
	private ListView[] mLists;
	
	private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			selectTab(v);
		}
	};

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        setupWidgets();
        
        selectTab(mTabServiceAll);
        
    }

	private void setupWidgets() {
		mTabServiceAll = (TextView) findViewById(R.id.tab_service_all);
        mTabServiceFavorite = (TextView)findViewById(R.id.tab_service_favorite);
        mTabContentRemind = (TextView)findViewById(R.id.tab_content_remind);
        
        mTabs = new TextView[TAB_COUNT];
        mTabs[0] = mTabServiceAll;
        mTabs[1] = mTabServiceFavorite;
        mTabs[2] = mTabContentRemind;
        
        for (int i = 0; i < TAB_COUNT; i++) {
        	mTabs[i].setOnClickListener(mTabOnClickListener);
        }
        
        mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        
        mListServiceAll = (ListView) findViewById(R.id.list_service_all);
        mListServiceFavorite = (ListView) findViewById(R.id.list_service_favorite);
        mListContentRemind = (ListView) findViewById(R.id.list_content_remind);
        
        mLists = new ListView[TAB_COUNT];
        mLists[0] = mListServiceAll;
        mLists[1] = mListServiceFavorite;
        mLists[2] = mListContentRemind;
        
        ListAdapter mockServiceAllAdapter = new MockServiceItemAdapter(this, Arrays.asList(ALL_SERVICE_NAMES));
        mListServiceAll.setAdapter(mockServiceAllAdapter);
        
        ListAdapter mockServiceFavoriteAdapter = new MockServiceItemAdapter(this, Arrays.asList(FAVORITE_SERVICE_NAMES));
        mListServiceFavorite.setAdapter(mockServiceFavoriteAdapter);
        
        ListAdapter mockContentItemAdapter = new MockContentItemAdapter(this, Arrays.asList(REMIND_CONTENT_NAMES));
        mListContentRemind.setAdapter(mockContentItemAdapter);
	}
    
    private void selectTab(View tab) {
    	for (int i = 0; i < TAB_COUNT; i++) {
    		if (mTabs[i] == tab) {
    			mTabs[i].setEnabled(false);
    			mViewFlipper.setDisplayedChild(i);
    		} else { 
    			mTabs[i].setEnabled(true);
    		}
    	}
    }
}