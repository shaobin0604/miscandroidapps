package com.example.testjsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String html = "<html><head><title>First parse</title></head>"
        	  + "<body><p>Parsed HTML into a doc.</p></body></html>";
        	Document doc = Jsoup.parse(html);
        
        
    }
}