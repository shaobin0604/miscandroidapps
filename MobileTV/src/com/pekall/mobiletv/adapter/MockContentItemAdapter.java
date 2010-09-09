package com.pekall.mobiletv.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pekall.mobiletv.R;


public class MockContentItemAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> mItems;
	
	public MockContentItemAdapter(Context context, List<String> items) {
		super();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mItems = items;
	}

	
	public MockContentItemAdapter(Context context) {
		super();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mItems = Collections.emptyList();
	}

	public void setItems(List<String> items) {
		mItems = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
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
		
		holder.getContentName().setText(mItems.get(position));
		
		return row;
	}
}
