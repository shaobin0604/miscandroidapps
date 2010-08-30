package com.geolo.android;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListSample extends Activity {

	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";

	public Map<String,?> createItem(String title, String caption) {
		Map<String,String> item = new HashMap<String,String>();
		item.put(ITEM_TITLE, title);
		item.put(ITEM_CAPTION, caption);
		return item;
	}
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		List<Map<String,?>> security = new LinkedList<Map<String,?>>();
		security.add(createItem("Remember passwords", "Save usernames and passwords for Web sites"));
		security.add(createItem("Clear passwords", "Save usernames and passwords for Web sites"));
		security.add(createItem("Show security warnings", "Show warning if there is a problem with a site's security"));

		// create our list and custom adapter
		SeparatedListAdapter adapter = new SeparatedListAdapter(this);
		
		adapter.addSection("Array test", new ArrayAdapter<String>(this,
			R.xml.list_item, new String[] { "First item", "Item two","Item third" ,"Item four" }));
		
		adapter.addSection("Security", new SimpleAdapter(this, security, R.xml.list_complex,
			new String[] { ITEM_TITLE, ITEM_CAPTION }, new int[] { R.id.list_complex_title, R.id.list_complex_caption }));

		/*����һ��ListView����*/
		ListView listView = new ListView(this);
		/*����һ��ListView�ĵ�һ��Ķ�����ͬword��ҳ�׺�ҳβ*/
		DemoListHeaderView headerView = new DemoListHeaderView(this);
		/*��ͷ���б�headerView�ӵ�ListView�ĵ�һ�����ҳ�ף�*/
        listView.addHeaderView(headerView);
        
        listView.setAdapter(adapter);
        
        registerForContextMenu(listView);
		this.setContentView(listView);

	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add("A");
		menu.add("B");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

}