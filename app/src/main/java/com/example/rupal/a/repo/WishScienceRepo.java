package com.example.rupal.a.repo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import com.example.rupal.a.db.DatabaseHelper;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.model.WishScience;

public class WishScienceRepo implements Crud {

	private DatabaseHelper helper;

	public WishScienceRepo(Context context) {
		DatabaseManager.init(context);
		helper = DatabaseManager.getInstance().getHelper();
	}

	@Override
	public int create(Object item) {

		int index = -1;

		System.out.println("Table Sceince Created");

		WishScience object = (WishScience) item;
		try {
			index = helper.getWishScienceDao().create(object);
		} catch (SQLException e) {

			System.out.println("Table Sceince Exception Created" + e.getMessage());

			e.printStackTrace();
		}

		return index;
	}

	@Override
	public int update(Object item) {
		
		int index = -1;
		
		WishScience object = (WishScience) item;
		
		try {
			helper.getWishScienceDao().update(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
	}

	@Override
	public int delete(Object item) {
		
		int index = -1;
		
		WishScience object = (WishScience) item;
		
		try {
			helper.getWishScienceDao().delete(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
		
	}

	@Override
	public Object findById(int id) {
		
		WishScience wishScience = null;
		try {
			wishScience = helper.getWishScienceDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wishScience;
		
	}

	@Override
	public List<?> findAll() {

		List<WishScience> items = null;

		try {
			items =  helper.getWishScienceDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	@Override
	public List<?> findAll(String string, String noUse) {
		return null;
	}
}
