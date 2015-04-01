/*In Class Assignment 8
 * 
 * DetailedActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */

package com.example.bbcnewsapp;
/*
 * Shows all the details of corresponding news item
 */
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedActivity extends Activity {
	TextView title;
	TextView pubDate;
	TextView description;
	ImageView newsImage;
	String imageURL;
	String read;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed);

		title = (TextView) findViewById(R.id.textView1);
		pubDate = (TextView) findViewById(R.id.textView2);
		description = (TextView) findViewById(R.id.textView4);
		newsImage = (ImageView) findViewById(R.id.imageView1);

		title.setText(getIntent().getExtras().getString("Title") + "");
		pubDate.setText(getIntent().getExtras().getString("PubDate") + "");
		description.setText(getIntent().getExtras().getString("Description")
				+ "");
		imageURL = getIntent().getExtras().getString("URL");
		read = getIntent().getExtras().getString("Reading");

		Log.d("demo", imageURL + "");
		Picasso.with(DetailedActivity.this).load(imageURL).into(newsImage);
		if (read.equals("True")) {
			position = getIntent().getExtras().getInt("Position");
			Intent intent = new Intent();
			intent.putExtra("pos", position);
			setResult(RESULT_OK, intent);
		} else {
			setResult(RESULT_CANCELED);
		}
	}
}
