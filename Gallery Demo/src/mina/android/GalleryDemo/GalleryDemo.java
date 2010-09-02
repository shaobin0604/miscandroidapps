package mina.android.GalleryDemo;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;

public class GalleryDemo extends Activity {
	//Controls
	Gallery gallery;
	
	private static final String[] PROGRAMS = {
		"中央一台",
		"中央二台",
		"中央三台",
		"中央四台",
		"中央五台",
		"中央六台",
		"中央七台",
	};
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gallery=(Gallery)findViewById(R.id.gallery);
        
        //Array adapter to display our values in the gallery control
        GalleryAdapter adapter = new GalleryAdapter(this, Arrays.asList(PROGRAMS));
        
        gallery.setAdapter(adapter);
    }
	
	private static class GalleryAdapter extends BaseAdapter {
		private Context mContext;
		private List<String> mItems;
		
		public GalleryAdapter(Context mContext, List<String> items) {
			super();
			this.mContext = mContext;
			this.mItems = items;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
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
			TextView button = null;
			
			if (convertView == null) {
				button = new TextView(mContext);
				button.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT, Gallery.LayoutParams.WRAP_CONTENT));
				button.setBackgroundResource(R.drawable.button);
			} else {
				button = (TextView) convertView;
			}
			
			button.setText(mItems.get(position));
			
			return button;
		}
		
	}
}