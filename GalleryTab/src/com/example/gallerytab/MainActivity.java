package com.example.gallerytab;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Gallery gallery;
	private TextAdapter textAdapter;

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
		
		TabBar tabBar = new TabBar(this);
		tabBar.addTab(new Button(this));
		tabBar.addTab(new Button(this));
		
		setContentView(tabBar);
		
//		setContentView(R.layout.main);
//		gallery = (Gallery) findViewById(R.id.gallery);
//		textAdapter = new TextAdapter(this);
//		gallery.setAdapter(textAdapter);
//		gallery.setSelection(initialPos());
	}

	private int initialPos() {
//		int start = Integer.MAX_VALUE / 2;
//		
//		for (int i = 0; i < PROGRAM_NAMES.length; i++) {
//			start += i;
//			if (start % PROGRAM_NAMES.length == 0)
//				break;
//		}
//		
//		return start;
		return 0;
	}

	public class TextAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;

		public TextAdapter(Context context) {
			mContext = context;
			TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
			mGalleryItemBackground = R.drawable.gallery_item_background;
		}

		// 第1点改进，返回一个很大的值，例如，Integer.MAX_VALUE
		public int getCount() {
			return PROGRAM_NAMES.length;
		}

		public Object getItem(int position) {
			return position;
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
	
			if (position < 0) {
				position = position + PROGRAM_NAMES.length;
			}
			
			text.setText(PROGRAM_NAMES[position % PROGRAM_NAMES.length]);
			
			text.setLayoutParams(new Gallery.LayoutParams(102, 37));
			text.setGravity(Gravity.CENTER);
			text.setBackgroundResource(mGalleryItemBackground);
			return text;
		}
	}
}