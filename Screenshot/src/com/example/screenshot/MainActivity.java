
package com.example.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity implements OnClickListener {
    private Spinner mSpinner;
    private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToggleButton = (ToggleButton) findViewById(R.id.tb_watch);
        mToggleButton.setOnClickListener(this);
        mSpinner = (Spinner) findViewById(R.id.sp_watch_mode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == mToggleButton) {
            Intent intent = new Intent(this, WatchService.class);
            intent.putExtra(WatchService.EXTRA_WATCH_MODE, mSpinner.getSelectedItemPosition());
            if (mToggleButton.isChecked()) {
                startService(intent);
            } else {
                stopService(intent);
            }
        }
    }

}
