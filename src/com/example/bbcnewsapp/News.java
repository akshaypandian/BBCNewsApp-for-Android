/*In Class Assignment 8
 * 
 * News.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */

package com.example.bbcnewsapp;
/*
 * Class to hold data relating to each news item
 */
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class News{

	@DatabaseField(id = true)
	String title;
	@DatabaseField
	String description;
	@DatabaseField
	String pubDate;
	@DatabaseField
	String thumbnailSmall;
	@DatabaseField
	String thumbnailLarge;
	@DatabaseField
	Date date;

	public News() {

	}

	public News(String title, String description, String pubDate,
			String thumbnailSmall, String thumbnailLarge) {
		super();
		this.title = title;
		this.description = description;
		this.pubDate = pubDate;
		this.thumbnailSmall = thumbnailSmall;
		this.thumbnailLarge = thumbnailLarge;
		this.date = new Date(System.currentTimeMillis());
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getThumbnailSmall() {
		return thumbnailSmall;
	}

	public void setThumbnailSmall(String thumbnailSmall) {
		this.thumbnailSmall = thumbnailSmall;
	}

	public String getThumbnailLarge() {
		return thumbnailLarge;
	}

	public void setThumbnailLarge(String thumbnailLarge) {
		this.thumbnailLarge = thumbnailLarge;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "News [title=" + title + ", description=" + description
				+ ", pubDate=" + pubDate + ", thumbnailSmall=" + thumbnailSmall
				+ ", thumbnailLarge=" + thumbnailLarge + ", date=" + date + "]";
	}

	
}
