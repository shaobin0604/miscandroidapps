package com.pekall.mobiletv;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.pekall.mobiletv.adapter.MockContentItemAdapter;

public class SearchContentResultActivity extends Activity {
	
	private static final String[] CONTENT_NAMES = {
		"新闻联播",
		"科技新闻",
		"动物世界",
		"黄金剧场",
	};
	
	private ListView mContentList;
	private TextView mEmptyTextView;
	private TextView mTitleTextView;
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_content_result_activity);
		
		mTitleTextView = (TextView) findViewById(R.id.title);
		mTitleTextView.setText("节目搜索结果");
		
		mContentList = (ListView) findViewById(R.id.content_list);
		MockContentItemAdapter adapter = new MockContentItemAdapter(this, Arrays.asList(CONTENT_NAMES));
//		MockContentItemAdapter adapter = new MockContentItemAdapter(this);
		mContentList.setAdapter(adapter);
		
		mEmptyTextView = (TextView) findViewById(R.id.empty);
		mEmptyTextView.setText("没有节目包含\"歌舞青春\"请返回,重新搜索.");
		
		mContentList.setEmptyView(mTitleTextView);
	}
	
	
	
}
