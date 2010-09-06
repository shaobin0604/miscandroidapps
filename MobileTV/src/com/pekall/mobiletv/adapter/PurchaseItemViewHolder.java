package com.pekall.mobiletv.adapter;

import com.pekall.mobiletv.R;

import android.view.View;
import android.widget.TextView;

public class PurchaseItemViewHolder {
	private View root;
	private TextView title;
	private TextView notice;
	private TextView services;
	private TextView period;
	private TextView price;
	private TextView description;
	
	public PurchaseItemViewHolder(View root) {
		this.root = root;
	}

	public TextView getTitle() {
		if (title == null)
			title = (TextView) root.findViewById(R.id.purchase_item_title);
		return title;
	}

	public TextView getNotice() {
		if (notice == null)
			notice = (TextView) root.findViewById(R.id.notice);
		return notice;
	}

	public TextView getServices() {
		if (services == null)
			services = (TextView) root.findViewById(R.id.service_list);
		return services;
	}

	public TextView getPeriod() {
		if (period == null)
			period = (TextView) root.findViewById(R.id.subscribe_period);
		return period;
	}

	public TextView getPrice() {
		if (price == null)
			price = (TextView) root.findViewById(R.id.price);
		return price;
	}

	public TextView getDescription() {
		if (description == null)
			description = (TextView) root.findViewById(R.id.description);
		return description;
	}
	
	
}
