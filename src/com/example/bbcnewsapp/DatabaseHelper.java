/*In Class Assignment 8
 * 
 * DatabaseHelper.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */

package com.example.bbcnewsapp;
/*
 * Helper class to store and retrieve news items in the local
 * database
 */

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "news.db";
	private static final int DATABASE_VERSION = 1;
	
	private Dao<News, String> newsDao = null;
	private RuntimeExceptionDao<News, String> newsRuntimeDao = null; 

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {

		try {
			TableUtils.createTable(connectionSource, News.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, News.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Dao<News, String> getDao() throws SQLException{
		if(newsDao == null){
			newsDao = getDao(News.class);
		}
		return newsDao;
	}
	
	public RuntimeExceptionDao<News, String> getNewsRuntimeExceptionDao(){
		if(newsRuntimeDao == null){
			newsRuntimeDao = getRuntimeExceptionDao(News.class);
		}
		return newsRuntimeDao;
	}
}
