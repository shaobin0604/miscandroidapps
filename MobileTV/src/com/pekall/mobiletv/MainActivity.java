package com.pekall.mobiletv;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private static final int TAB_COUNT = 3;
	
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
        
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
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
        
        ListAdapter mockServiceAllAdapter = new MockServiceAllAdapter(this);
        mListServiceAll.setAdapter(mockServiceAllAdapter);
        
        ListAdapter mockServiceFavoriteAdapter = new MockServiceFavoriteAdapter(this);
        mListServiceFavorite.setAdapter(mockServiceFavoriteAdapter);
        
        ListAdapter mockContentRemindAdapter = new MockContentRemindAdapter(this);
        mListContentRemind.setAdapter(mockContentRemindAdapter);
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
    
    private static class ServiceItemViewHolder {
		private View root;
		
		private TextView serviceName;
		private TextView contentName;
		private TextView contentTime;
		private ImageView lockIndicator;
		private ImageView favoriteIndicator;
		
		public ServiceItemViewHolder(View root) {
			super();
			this.root = root;
		}

		public TextView getServiceName() {
			if (serviceName == null)
				serviceName = (TextView)root.findViewById(R.id.service_name);
			return serviceName;
		}

		public TextView getContentName() {
			if (contentName == null)
				contentName = (TextView) root.findViewById(R.id.content_name);
			return contentName;
		}

		public TextView getContentTime() {
			if (contentTime == null)
				contentTime = (TextView) root.findViewById(R.id.content_time);
			return contentTime;
		}

		public ImageView getLockIndicator() {
			if (lockIndicator == null)
				lockIndicator = (ImageView) root.findViewById(R.id.lock_indicator);
			return lockIndicator;
		}
		
		public ImageView getFavoriteIndicator() {
			if (favoriteIndicator == null)
				favoriteIndicator = (ImageView) root.findViewById(R.id.favorite_indicator);
			return favoriteIndicator;
		}
	}
    
    private static class ContentItemViewHolder {
    	private View root;
    	private TextView contentName;
    	private TextView contentTime;
    	private TextView contentDate;
    	private TextView serviceName;
    	private ImageView remindIndicator;
    	
		public ContentItemViewHolder(View root) {
			super();
			this.root = root;
		}
    	
		public TextView getServiceName() {
			if (serviceName == null)
				serviceName = (TextView)root.findViewById(R.id.service_name);
			return serviceName;
		}

		public TextView getContentName() {
			if (contentName == null)
				contentName = (TextView) root.findViewById(R.id.content_name);
			return contentName;
		}

		public TextView getContentTime() {
			if (contentTime == null)
				contentTime = (TextView) root.findViewById(R.id.content_time);
			return contentTime;
		}

		public TextView getContentDate() {
			if (contentDate == null)
				contentDate = (TextView) root.findViewById(R.id.content_date);
			return contentDate;
		}

		public ImageView getStateIndicator() {
			if (remindIndicator == null)
				remindIndicator = (ImageView) root.findViewById(R.id.remind_indicator);
			return remindIndicator;
		}
    }
    
    private static class MockServiceAllAdapter extends BaseAdapter {

    	private Context mContext;
    	private LayoutInflater mInflater;

    	private String[] SERVICE_NAMES = {
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
    	
		public MockServiceAllAdapter(Context context) {
			super();
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return SERVICE_NAMES.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return SERVICE_NAMES[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ServiceItemViewHolder holder = null;
			
			if (convertView == null) {
				row = mInflater.inflate(R.layout.service_item, null);
				holder = new ServiceItemViewHolder(row);
				row.setTag(holder);
			} else {
				holder = (ServiceItemViewHolder)convertView.getTag();
			}
			
			holder.getServiceName().setText(SERVICE_NAMES[position]);
			
			return row;
		}
    }
    
    private static class MockServiceFavoriteAdapter extends BaseAdapter {
    	private Context mContext;
    	private LayoutInflater mInflater;

    	private String[] SERVICE_NAMES = {
    		"中央一台",
    		"中央二台",
    		"中央三台",
    		"中央四台",
    	};
    	
		public MockServiceFavoriteAdapter(Context context) {
			super();
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return SERVICE_NAMES.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return SERVICE_NAMES[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ServiceItemViewHolder holder = null;
			
			if (convertView == null) {
				row = mInflater.inflate(R.layout.service_item, null);
				holder = new ServiceItemViewHolder(row);
				row.setTag(holder);
			} else {
				holder = (ServiceItemViewHolder)convertView.getTag();
			}
			
			holder.getServiceName().setText(SERVICE_NAMES[position]);
			
			return row;
		}
    }
    
    private static class MockContentRemindAdapter extends BaseAdapter {

    	
    	private static final String[] CONTENT_NAMES = {
    		"新闻联播",
    		"科技新闻",
    		"动物世界",
    		"黄金剧场",
    	};
    	
    	private Context mContext;
    	private LayoutInflater mInflater;
    	
    	

		public MockContentRemindAdapter(Context context) {
			super();
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return CONTENT_NAMES.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return CONTENT_NAMES[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row = convertView;
			ContentItemViewHolder holder = null;
			
			if (convertView == null) {
				row = mInflater.inflate(R.layout.content_item, null);
				holder = new ContentItemViewHolder(row);
				row.setTag(holder);
			} else {
				holder = (ContentItemViewHolder)convertView.getTag();
			}
			
			holder.getContentName().setText(CONTENT_NAMES[position]);
			
			return row;
		}
    	
    }
}