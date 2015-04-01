/*In Class Assignment 8
 * 
 * NewsActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */

package com.example.bbcnewsapp;
/*
 * Retrieves data from BBC RSS feeds and feeds it to Utility class which parses the result
 * and makes corresponding news objects. Shows all the news items in listview, clicking
 * on which will open the detailed view.
 * User has the option to add any news item to his favorite list which is stored in a local 
 * database using ormlite
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class NewsActivity extends Activity {
	ArrayList<News> newsList;
	List<News> nl;
	String newsURL = null;
	ProgressDialog pd;
	ListView listview;
	DatabaseHelper dbHelper;
	RuntimeExceptionDao<News, String> newsDao;
	public static final int code = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		newsList = new ArrayList<News>();
		newsURL = getIntent().getExtras().getString(MainActivity.newslink);
		if (newsURL.isEmpty()) {
			dbHelper = OpenHelperManager.getHelper(NewsActivity.this,
					DatabaseHelper.class);
			newsDao = dbHelper.getNewsRuntimeExceptionDao();
			nl = newsDao.queryForAll();
			Log.d("db", nl.toString());
			setUpList(nl);
		} else {
			new GetNewsAsyncTask().execute(newsURL);
		}
	}

	private class GetNewsAsyncTask extends
			AsyncTask<String, Void, ArrayList<News>> {

		@Override
		protected ArrayList<News> doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					InputStream in = con.getInputStream();
					return NewsUtil.NewsPullParser.parseNews(in);
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(NewsActivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCancelable(false);
			pd.setMessage("Loading News...");
			pd.show();
		}

		@Override
		protected void onPostExecute(ArrayList<News> result) {
			if (result != null) {
				newsList = result;
			}
			setUpData(newsList);
		}
	}

	private void setUpData(ArrayList<News> news) {
		listview = (ListView) findViewById(R.id.listView1);
		final NewsAdapter adapter = new NewsAdapter(NewsActivity.this,
				R.layout.row_layout, newsList);
		listview.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
		pd.dismiss();

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(NewsActivity.this,
						DetailedActivity.class);
				intent.putExtra("Title", newsList.get(position).getTitle());
				intent.putExtra("PubDate", newsList.get(position).getPubDate());
				intent.putExtra("Description", newsList.get(position)
						.getDescription());
				intent.putExtra("URL", newsList.get(position)
						.getThumbnailLarge());
				intent.putExtra("Reading", "False");

				startActivity(intent);
			}
		});
		
		/*
		 * Long clicking any news item row will add the news item to the database
		 */
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				dbHelper = OpenHelperManager.getHelper(NewsActivity.this,
						DatabaseHelper.class);
				newsDao = dbHelper.getNewsRuntimeExceptionDao();

				if (newsDao.idExists(newsList.get(position).getTitle())) {
					newsDao.deleteById(newsList.get(position).getTitle());
					Toast.makeText(NewsActivity.this,
							"Deleted from Reading List", Toast.LENGTH_SHORT)
							.show();
				} else {
					newsDao.create(new News(newsList.get(position).getTitle(),
							newsList.get(position).getDescription(), newsList
									.get(position).getPubDate(), newsList.get(
									position).getThumbnailSmall(), newsList
									.get(position).getThumbnailLarge()));
					Toast.makeText(NewsActivity.this, "Added to Reading List",
							Toast.LENGTH_SHORT).show();
				}
				OpenHelperManager.releaseHelper();
				// listview.setAdapter(adapter);
				return true;
			}
		});
	}

	private void setUpList(final List<News> nl) {
		if (nl.isEmpty()) {
			Toast.makeText(NewsActivity.this, "No items in list",
					Toast.LENGTH_LONG).show();
		} else {
			Collections.sort(nl, new Comparator<News>() {

				@Override
				public int compare(News lhs, News rhs) {
					// TODO Auto-generated method stub
					return lhs.getDate().compareTo(rhs.getDate());
				}

			});
			listview = (ListView) findViewById(R.id.listView1);
			final NewsAdapter adapter = new NewsAdapter(NewsActivity.this,
					R.layout.row_layout, nl);
			listview.setAdapter(adapter);
			adapter.setNotifyOnChange(true);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Intent intent = new Intent(NewsActivity.this,
							DetailedActivity.class);
					intent.putExtra("Title", nl.get(position).getTitle());
					intent.putExtra("PubDate", nl.get(position).getPubDate());
					intent.putExtra("Description", nl.get(position)
							.getDescription());
					intent.putExtra("URL", nl.get(position).getThumbnailLarge());
					intent.putExtra("Reading", "True");
					intent.putExtra("Position", position);
					newsDao.deleteById(nl.get(position).getTitle());
					OpenHelperManager.releaseHelper();
					startActivityForResult(intent, code);
				}
			});

			listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					dbHelper = OpenHelperManager.getHelper(NewsActivity.this,
							DatabaseHelper.class);
					newsDao = dbHelper.getNewsRuntimeExceptionDao();

					newsDao.deleteById(nl.get(position).getTitle());
					Toast.makeText(NewsActivity.this,
							"Deleted from Reading List", Toast.LENGTH_SHORT)
							.show();
					Log.d("size", nl.size() + "");
					nl.remove(position);
					listview.setAdapter(adapter);
					OpenHelperManager.releaseHelper();
					return true;
				}

			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == code) {
			if (resultCode == RESULT_OK) {
				nl.remove(data.getExtras().getInt("pos"));
				NewsAdapter adapter = new NewsAdapter(NewsActivity.this,
						R.layout.row_layout, nl);
				listview.setAdapter(adapter);
			}
		}
	}
}
