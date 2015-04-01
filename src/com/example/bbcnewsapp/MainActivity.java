/*In Class Assignment 8
 * 
 * MainActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */

package com.example.bbcnewsapp;
/*
 * Allows the user to select news from any category listed. This restricts news items to
 * only that category. Passes this category url to the NewsActivity so that all news items
 * in that category are retrieved from BBC RSS feeds
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	public static final String newslink = "news";
	String[] category = { "Top Stories", "World", "UK", "Business", "Politics",
			"Health", "Education & Family", "Science & Environment",
			"Technology", "Reading List" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listview = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, category);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/rss.xml");
					startActivity(intent);
					break;
				}

				case 1: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/world/rss.xml");
					startActivity(intent);
					break;
				}
				case 2: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/uk/rss.xml");
					startActivity(intent);
					break;
				}
				case 3: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/business/rss.xml");
					startActivity(intent);
					break;

				}
				case 4: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/politics/rss.xml");
					startActivity(intent);
					break;

				}
				case 5: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/health/rss.xml");
					startActivity(intent);
					break;

				}
				case 6: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/education/rss.xml");
					startActivity(intent);
					break;

				}
				case 7: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/science_and_environment/rss.xml");
					startActivity(intent);
					break;

				}
				case 8: {
					Intent intent = new Intent(MainActivity.this,
							NewsActivity.class);
					intent.putExtra(newslink,
							"http://feeds.bbci.co.uk/news/technology/rss.xml");
					startActivity(intent);
					break;

				}
					
				case 9:{
					Intent intent = new Intent(MainActivity.this, NewsActivity.class);
					intent.putExtra(newslink, "");
					startActivity(intent);
				}
				}

			}
		});
	}
}
