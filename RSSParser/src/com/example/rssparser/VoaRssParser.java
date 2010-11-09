package com.example.rssparser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class VoaRssParser {
	public static class Article {
		public String title;
		public String link;
		public String date;

		@Override
		public String toString() {
			return "Article [date=" + date + ", link=" + link + ", title="
					+ title + "]";
		}

	}

	public static ArrayList<Article> parse(Reader in) {
		ArrayList<Article> list = new ArrayList<Article>();
		
		Article item = null;
		XmlPullParserFactory factory = null;
		try {
			factory = XmlPullParserFactory.newInstance();

			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			xpp.setInput(in);
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String tagName = xpp.getName();
					if ("item".equals(tagName)) {
						item = new Article();
					} else if ("title".equals(tagName) && item != null) {
						if (XmlPullParser.CDSECT == xpp.nextToken()) {
							item.title = xpp.getText();
						}
					} else if ("link".equals(tagName) && item != null) {
						item.link = xpp.nextText();
					} else if ("pubDate".equals(tagName) && item != null) {
						item.date = xpp.nextText();
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if ("item".equals(xpp.getName())) {
						System.out.println(item);
						list.add(item);
						item = null;
					}
				}
				eventType = xpp.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
