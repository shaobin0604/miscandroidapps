package com.example.gallerytab;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private Gallery gallery;
	private TabAdapter textAdapter;

	private static final String[] PROGRAM_NAMES = { 
		"中央一台",
		"中央二台",
		"中央三台",
		"中央四台",
		"中央五台",
		"中央六台",
		"中央七台",
		"中央八台",
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		gallery = (Gallery) findViewById(R.id.gallery);
		textAdapter = new TabAdapter(this, Arrays.asList(PROGRAM_NAMES));
		gallery.setAdapter(textAdapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TabAdapter adapter = (TabAdapter)parent.getAdapter();
				adapter.setSelectedPos(position);
			}
			
		});
	}

	

	public class TabAdapter extends BaseAdapter {
		private Context mContext;
		private List<String> mList;
		private int mSelectedPos;

		public TabAdapter(Context context, List<String> list) {
			mContext = context;
			TypedArray a = obtainStyledAttributes(R.styleable.Gallery);
			a.recycle();
			if (list == null)
				list = Collections.emptyList();
			mList = list; 
		}
		
		public void setSelectedPos(int pos) {
			if (pos != mSelectedPos) {
				mSelectedPos = pos;
				notifyDataSetChanged();
			}
		}
		
		public int getSelectedPos() {
			return mSelectedPos;
		}

		public int getCount() {
			return mList.size();
		}

		public Object getItem(int position) {
			return mList.get(position);
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
			
			text.setTextColor(Color.WHITE);
			text.setText(mList.get(position));
			
			text.setLayoutParams(new Gallery.LayoutParams(102, 40));
			text.setGravity(Gravity.CENTER);
			
			if (position == mSelectedPos)
				text.setBackgroundResource(R.drawable.tab_button_select);
			else
				text.setBackgroundResource(R.drawable.tab_button_unselect);
			
			return text;
		}
	}
}