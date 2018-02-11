package com.example.rupal.a.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.rupal.a.model.Categoryname;
import com.example.rupal.a.model.Job12;
import com.example.rupal.a.model.Science;
import com.example.rupal.a.model.WishScience;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.dao.Dao;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "WishListDB.sqlite";

	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
    //pressure
	private Dao<Science, Integer> scienceDao = null;

	private Dao<Job12, Integer> jobItemDao = null;

	private Dao<Categoryname, Integer> categoryItemDao = null;

	private Dao<WishScience, Integer> wishScienceDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, WishScience.class);
			TableUtils.createTable(connectionSource, Science.class);
			TableUtils.createTable(connectionSource, Job12.class);
			TableUtils.createTable(connectionSource, Categoryname.class);
		} catch (SQLException e) {

			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			List<String> allSql = new ArrayList<String>(); 
			switch(oldVersion) 
			{
			  case 1: 
				  //allSql.add("alter table AdData add column `new_col` VARCHAR");
				  //allSql.add("alter table AdData add column `new_col2` VARCHAR");
			}
			for (String sql : allSql) {
				db.execSQL(sql);
			}
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
			throw new RuntimeException(e);
		}
		
	}


	public Dao<Job12, Integer> getJobItemDao() {
		if (null == jobItemDao) {
			try {
				jobItemDao = getDao(Job12.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return jobItemDao;
	}

	public Dao<Categoryname, Integer> getCategoryItemDao() {
		if (null == categoryItemDao) {
			try {
				categoryItemDao = getDao(Categoryname.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return categoryItemDao;
	}

	/*public Dao<Job12, Integer> getJobDao() {
		if (null == jobItemDao) {
			try {
				jobItemDao = getDao(Job12.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return jobItemDao;
	}*/

	public Dao<Science, Integer> getScienceDao() {
		if (null == scienceDao) {
			try {
				scienceDao = getDao(Science.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return scienceDao;
	}


	public Dao<WishScience, Integer> getWishScienceDao() {
		if (null == wishScienceDao) {
			try {
				wishScienceDao = getDao(WishScience.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return wishScienceDao;
	}

}
