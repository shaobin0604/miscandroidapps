package com.pekall.mobiletv;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.pekall.mobiletv.adapter.MockPurchaseItemAdapter;
import com.pekall.mobiletv.ui.TabBar;
import com.pekall.mobiletv.ui.TabBar.TabSpec;

public class PurchaseDataActivity extends Activity {
	
	
	private TextView mTitleTextView;
	
	private TabBar mTabBar;
	private ViewFlipper mFlipper;
	private ListView mOrderedPurchaseDataList;
	private ListView mUnorderedPurchaseDataList;
	
	
	private static final String[] PURCHASE_ITEMS = {
		"本地套餐一",
		"本地套餐二"
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
		mUnorderedPurchaseDataList = (ListView) findViewById(R.id.unordered_purchase_data_list);
		
		MockPurchaseItemAdapter adapter = new MockPurchaseItemAdapter(this, Arrays.asList(PURCHASE_ITEMS));
		
		mOrderedPurchaseDataList.setAdapter(adapter);
		
		mFlipper = (ViewFlipper) findViewById(R.id.flipper);
		mTabBar = (TabBar) findViewById(R.id.tab_bar);
		mTabBar.addTab(new TabSpec("已订购套餐", null));
		mTabBar.addTab(new TabSpec("未订购套餐", null));
		
		mTabBar.pack();
	}
}
