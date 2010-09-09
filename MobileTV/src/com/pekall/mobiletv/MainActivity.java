package com.pekall.mobiletv;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.pekall.mobiletv.adapter.MockContentItemAdapter;
import com.pekall.mobiletv.adapter.MockServiceItemAdapter;
import com.pekall.mobiletv.ui.TabBar;
import com.pekall.mobiletv.ui.TabBar.TabSpec;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private static final int TAB_COUNT = 3;
	
	// 节目单
	private static final int OPTIONS_MENU_CONTENT_LIST        = Menu.FIRST;
	// 订购套餐管理
	private static final int OPTIONS_MENU_PURCHASE_ITEM       = Menu.FIRST + 1;
	// 业务管理
	private static final int OPTIONS_MENU_MBBMS_SERVICE       = Menu.FIRST + 2;
	// 搜索节目
	private static final int OPTIONS_MENU_SEARCH              = Menu.FIRST + 3;
	// 更新节目单
	private static final int OPTIONS_MENU_UPDATE_SG           = Menu.FIRST + 4;
	// 设置
	private static final int OPTIONS_MENU_SETTINGS            = Menu.FIRST + 5;
	// 帮助
	private static final int OPTIONS_MENU_HELP                = Menu.FIRST + 6;
	// 退出
	private static final int OPTIONS_MENU_QUIT                = Menu.FIRST + 7;
	
	// 收藏频道
	private static final int CONTEXT_MENU_ADD_TO_FAVORITE_SERVICE = Menu.FIRST;
	// 取消收藏频道
	private static final int CONTEXT_MENU_REMOVE_FROM_FAVORITE_SERVICE = Menu.FIRST + 1;
	
	
	
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
	
	private TabBar mTabBar;
	
	private ViewFlipper mViewFlipper;
	
	private ListView mListServiceAll;
	private ListView mListServiceFavorite;
	private ListView mListContentRemind;
	
	private TabBar.OnTabChangeListener mOnTabChangeListener = new TabBar.OnTabChangeListener() {
		
		@Override
		public void onTabChanged(int index) {
			mViewFlipper.setDisplayedChild(index);
		}
	};

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main_activity);
        
        setupWidgets();    
    }

	private void setupWidgets() {
        mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        
        mListServiceAll = (ListView) findViewById(R.id.list_service_all);
        mListServiceFavorite = (ListView) findViewById(R.id.list_service_favorite);
        mListContentRemind = (ListView) findViewById(R.id.list_content_remind);
        
        ListAdapter mockServiceAllAdapter = new MockServiceItemAdapter(this, Arrays.asList(ALL_SERVICE_NAMES));
        mListServiceAll.setAdapter(mockServiceAllAdapter);
        
        ListAdapter mockServiceFavoriteAdapter = new MockServiceItemAdapter(this, Arrays.asList(FAVORITE_SERVICE_NAMES));
        mListServiceFavorite.setAdapter(mockServiceFavoriteAdapter);
        
        ListAdapter mockContentItemAdapter = new MockContentItemAdapter(this, Arrays.asList(REMIND_CONTENT_NAMES));
        mListContentRemind.setAdapter(mockContentItemAdapter);
        
        mTabBar = (TabBar) findViewById(R.id.tab_bar);
		mTabBar.addTab(new TabSpec("全部频道", null));
		mTabBar.addTab(new TabSpec("收藏频道", null));
		mTabBar.addTab(new TabSpec("节目提醒", null));
		mTabBar.pack();
		
		mTabBar.setOnTabChangeListener(mOnTabChangeListener);
		mTabBar.setCurrentTab(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, OPTIONS_MENU_CONTENT_LIST, Menu.NONE, R.string.menu_content_list).setIntent(new Intent(this, ContentListActivity.class));
		menu.add(Menu.NONE, OPTIONS_MENU_PURCHASE_ITEM, Menu.NONE, R.string.menu_purchase_item).setIntent(new Intent(this, PurchaseDataActivity.class));
		menu.add(Menu.NONE, OPTIONS_MENU_MBBMS_SERVICE, Menu.NONE, R.string.menu_mbbms_service).setIntent(new Intent(this, MbbmsServiceActivity.class));
		menu.add(Menu.NONE, OPTIONS_MENU_SEARCH, Menu.NONE, R.string.menu_search).setIntent(new Intent(this, SearchContentActivity.class));
		menu.add(Menu.NONE, OPTIONS_MENU_UPDATE_SG, Menu.NONE, R.string.menu_update_content_list);
		menu.add(Menu.NONE, OPTIONS_MENU_SETTINGS, Menu.NONE, R.string.menu_settings).setIntent(new Intent(this, SettingsActivity.class));
		menu.add(Menu.NONE, OPTIONS_MENU_HELP, Menu.NONE, R.string.menu_help).setIntent(new Intent(this, HelpActivity.class));
		menu.add(Menu.NONE, OPTIONS_MENU_QUIT, Menu.NONE, R.string.menu_quit);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case OPTIONS_MENU_QUIT:
			handleQuit();
			break;
		case OPTIONS_MENU_UPDATE_SG:
			handleUpdateSg();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void handleUpdateSg() {
		// TODO Auto-generated method stub
		
	}

	private void handleQuit() {
		// TODO shutdown application gracefully
		finish();
	}
}