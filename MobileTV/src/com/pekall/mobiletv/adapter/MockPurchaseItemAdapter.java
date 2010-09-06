package com.pekall.mobiletv.adapter;

import java.util.Collections;
import java.util.List;

import com.pekall.mobiletv.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MockPurchaseItemAdapter extends BaseAdapter {
	
	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> mItems;
	
	

	public MockPurchaseItemAdapter(Context context,
			List<String> items) {
		super();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		if (items == null)
			items = Collections.emptyList();
		this.mItems = items;
	}
	
	public MockPurchaseItemAdapter(Context context) {
		this(context, null);
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
		PurchaseItemViewHolder holder = null;
		View row = convertView;
		if (row == null) {
			row = mInflater.inflate(R.layout.purchase_item, null);
			holder = new PurchaseItemViewHolder(row);
			row.setTag(holder);
		} else {
			holder = (PurchaseItemViewHolder)row.getTag();
		}
		
		holder.getTitle().setText(mItems.get(position));
		
		return row;
	}

}
