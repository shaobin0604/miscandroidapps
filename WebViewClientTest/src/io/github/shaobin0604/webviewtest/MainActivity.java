
package io.github.shaobin0604.webviewtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(ArrayAdapter.createFromResource(this, R.array.demo_entries,
                android.R.layout.simple_list_item_1));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        switch (position) {
            case 0:
                gotoActivity(WithoutWebViewClientActivity.class);
                break;
            case 1:
                gotoActivity(WithDefaultWebViewClientActivity.class);
                break;
            case 2:
                gotoActivity(WithWebViewClientShouldOverrideUrlLoadingActivity.class);
                break;
            default:
                break;
        }
    }

    private void gotoActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
