package com.pekall.mobiletv;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.pekall.mobiletv.adapter.MockPurchaseItemAdapter;
import com.pekall.mobiletv.ui.TabBar;
import com.pekall.mobiletv.ui.TabBar.OnTabChangeListener;
import com.pekall.mobiletv.ui.TabBar.TabSpec;

public class PurchaseDataActivity extends Activity {
	
	
	private TextView mTitleTextView;
	
	private TabBar mTabBar;
	private ViewFlipper mFlipper;
	private ListView mOrderedPurchaseDataList;
	private ListView mUnorderedPurchaseDataList;
	
	
	private static final String[] ORDERED_PURCHASE_ITEMS = {
		"手机电视全网套餐1",
	};
	
	private static final String[] LOCAL_PURCHASE_ITEMS = {
		"手机电视全网套餐1",
	};
	
	private OnTabChangeListener mTabSelectedListener = new OnTabChangeListener() {
		
		@Override
		public void onTabChanged(int index) {
			mFlipper.setDisplayedChild(index);
		}
	};
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.purchase_data_activity);
		
		mTitleTextView = (TextView) findViewById(R.id.title);
		mTitleTextView.setText("套餐管理");
		
		mOrderedPurchaseDataList = (ListView) findViewById(R.id.ordered_purchase_data_list);
		MockPurchaseItemAdapter orderedAdapter = new MockPurchaseItemAdapter(this, Arrays.asList(ORDERED_PURCHASE_ITEMS));
		mOrderedPurchaseDataList.setAdapter(orderedAdapter);
		
		mUnorderedPurchaseDataList = (ListView) findViewById(R.id.unordered_purchase_data_list);
		MockPurchaseItemAdapter unorderedAdapter = new MockPurchaseItemAdapter(this, Arrays.asList(LOCAL_PURCHASE_ITEMS));
		mUnorderedPurchaseDataList.setAdapter(unorderedAdapter);
		
		
		mFlipper = (ViewFlipper) findViewById(R.id.flipper);
		
		mTabBar = (TabBar) findViewById(R.id.tab_bar);
		mTabBar.addTab(new TabSpec("已订购套餐", null));
		mTabBar.addTab(new TabSpec("本地套餐", null));
		mTabBar.pack();
		
		mTabBar.setOnTabChangeListener(mTabSelectedListener);
		mTabBar.setCurrentTab(0);
	}
}
