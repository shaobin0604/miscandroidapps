/**
 * 
 */
package com.pekall.mobiletv.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pekall.mobiletv.R;

public class ServiceItemViewHolder {
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