package com.pekall.mobiletv;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class ContentListActivity extends Activity {
	private static final String TAG = ContentListActivity.class.getSimpleName();
	
	private Gallery mServiceGallery;
	private ListView mContentList;
	
	private TextView mDate;
	private TextView mDateChooser;
	
	private int mClickedPos;

	private static final String[] SERVICE_NAMES = {
		"中央一台",
		"中央二台",
		"中央三台",
		"中央四台",
		"中央五台",
	};
	
	private static final String[][] CONTENT_NAMES = {
		{"新闻30分", "今日说法"},
		{"财经连线", "财经下午茶"},
		{"星光大道"},
		{"国际新闻"},
		{"体育新闻", "足球之夜"},
	};
	
	private OnItemClickListener mServiceItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectService(position);
		}
		
	};
	
	private OnItemSelectedListener mServiceItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.content_list_activity);
		
		mDate = (TextView) findViewById(R.id.content_date);
		mDateChooser = (TextView) findViewById(R.id.content_date_chooser);
		
		mServiceGallery = (Gallery) findViewById(R.id.service_gallery);
		mContentList = (ListView) findViewById(R.id.content_list);
		
		ServiceItemAdapter serviceAdapter = new ServiceItemAdapter(this, Arrays.asList(SERVICE_NAMES));
		mServiceGallery.setAdapter(serviceAdapter);
		mServiceGallery.setOnItemSelectedListener(mServiceItemSelectedListener);
		mServiceGallery.setOnItemClickListener(mServiceItemClickListener);
		
		selectService(0);
	}
	
	private void selectService(int position) {
		ServiceItemAdapter serviceItemadapter = (ServiceItemAdapter) mServiceGallery.getAdapter(); 
		serviceItemadapter.setSelectedPosition(position);
		
		List<String> items = Arrays.asList(CONTENT_NAMES[position]);
		
		ContentItemAdapter contentItemadapter = (ContentItemAdapter)mContentList.getAdapter(); 
		
		if (contentItemadapter == null) {
			contentItemadapter = new ContentItemAdapter(ContentListActivity.this, items);
			mContentList.setAdapter(contentItemadapter);
		} else {
			contentItemadapter.setItems(items);
			contentItemadapter.notifyDataSetChanged();
		}
	}

	private static class ContentItemAdapter extends BaseAdapter {
		private Context mContext;
		private List<String> mItems;
		private LayoutInflater mInflater;
		
		public ContentItemAdapter(Context context, List<String> items) {
			super();
			this.mContext = context;
			this.mItems = items;
			this.mInflater = LayoutInflater.from(context);
		}
		
		public void setItems(List<String> items) {
			mItems = items;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}
		

		@Override
		public Object getItem(int position) {
			
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ViewHolder holder = null;
			if (convertView == null) {
				row = mInflater.inflate(R.layout.content_item_1, null);
				holder = new ViewHolder(row);
				row.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag(); 
			}
			
			holder.getContentName().setText(mItems.get(position));
			
			return row;
		}
		
		private static class ViewHolder {
			private View root;
			private ImageView currentIndicator;
			private TextView contentName;
			private TextView contentTime;
			
			private ImageView remindIndicator;
			
			public ViewHolder(View root) {
				this.root = root;
			}

			public ImageView getCurrentIndicator() {
				if (currentIndicator == null)
					currentIndicator = (ImageView) root.findViewById(R.id.current_indicator);
				return currentIndicator;
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

			public ImageView getRemindIndicator() {
				if (remindIndicator == null)
					remindIndicator = (ImageView) root.findViewById(R.id.remind_indicator);
				return remindIndicator;
			}
			
			
		}
	}
	
	private static class ServiceItemAdapter extends BaseAdapter {
		private Context mContext;
		private List<String> mItems;
		
		private int mSelectedPosition;
		
		public void setSelectedPosition(int position) {
			mSelectedPosition = position;
			notifyDataSetChanged();
		}

		public ServiceItemAdapter(Context context, List<String> items) {
			mContext = context;
			mItems = items;
		}

		public int getCount() {
			return mItems.size();
		}

		public Object getItem(int position) {
			return mItems.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView text = null;
			if (convertView == null ) {
				text = new TextView(mContext);
			} else {
				text = (TextView) convertView;
			}
			
			text.setText(mItems.get(position));
			text.setTextColor(Color.WHITE);
			
			text.setLayoutParams(new Gallery.LayoutParams(102, 37));
			text.setGravity(Gravity.CENTER);
			
			if (position == mSelectedPosition)
				text.setBackgroundResource(R.drawable.tab_button_select);
			else
				text.setBackgroundResource(R.drawable.tab_button_unselect);
			return text;
		}
	}
}
