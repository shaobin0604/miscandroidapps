package com.example.rssparser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.example.rssparser.VoaRssParser.Article;

import android.test.AndroidTestCase;

public class TestVoaRssParser extends AndroidTestCase {
	
	public void testParse() throws UnsupportedEncodingException {
		assertTrue(true);
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("voa.xml");
		
		ArrayList<Article> list = VoaRssParser.parse(new InputStreamReader(is, "utf-8"));
		
		System.out.println(list);
	}
}
