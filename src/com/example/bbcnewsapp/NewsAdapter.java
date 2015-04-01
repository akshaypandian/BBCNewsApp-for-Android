/*In Class Assignment 7
 * 
 * NewsAdapter.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */

package com.example.bbcnewsapp;
/*
 * Adapter class to populate listview to show each news item
 */
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<News> {
	List<News> mData;
	Context mContext;
	int mResource;

	public NewsAdapter(Context context, int resource, List<News> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.mData = objects;
		this.mResource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(mResource, parent, false);
		}

		ImageView image = (ImageView) convertView.findViewById(R.id.imageView1);
		Picasso.with(mContext).load(mData.get(position).getThumbnailSmall())
				.into(image);
		TextView title = (TextView) convertView.findViewById(R.id.textView1);
		title.setText(mData.get(position).getTitle());

		return convertView;
	}

}
