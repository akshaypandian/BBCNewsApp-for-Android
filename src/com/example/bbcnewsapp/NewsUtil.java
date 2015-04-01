/*In Class Assignment 7
 * 
 * NewsUtil.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */

package com.example.bbcnewsapp;
/*
 * Class that parses XML data using XMLPullParser and makes corresponding news objects
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class NewsUtil {
	static public class NewsPullParser {

		static public ArrayList<News> parseNews(InputStream in)
				throws XmlPullParserException, IOException {
			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(in, "UTF-8");
			News news = null;
			ArrayList<News> newsList = new ArrayList<News>();
			int event = parser.getEventType();
			int count = 0;
			while (event != XmlPullParser.END_DOCUMENT) {

				switch (event) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("item")) {
						count++;
						news = new News();
					} else if (parser.getName().equals("title")) {
						if (news != null) {
							news.setTitle(parser.nextText());
						}
					} else if (parser.getName().equals("description")) {
						if (news != null) {
							news.setDescription(parser.nextText());
						}
					} else if (parser.getName().equals("pubDate")) {
						news.setPubDate(parser.nextText());
					} else if (parser.getName().equals("media:thumbnail")) {
						if (Integer.parseInt(parser.getAttributeValue(null,
								"width")) < 100) {
							news.setThumbnailSmall(parser.getAttributeValue(
									null, "url"));
						} else if (Integer.parseInt(parser.getAttributeValue(
								null, "width")) > 100) {
							news.setThumbnailLarge(parser.getAttributeValue(
									null, "url"));
						}
					}
					break;

				case XmlPullParser.END_TAG:
					if (parser.getName().equals("item")) {
						newsList.add(news);
						news = null;
					}
					break;
				}
				event = parser.next();
			}

			Log.d("count", count + "");
			return newsList;

		}
	}
}
