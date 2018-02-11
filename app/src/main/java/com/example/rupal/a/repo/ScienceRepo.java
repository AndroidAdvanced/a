package com.example.rupal.a.repo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import com.example.rupal.a.db.DatabaseHelper;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.model.Job12;
import com.example.rupal.a.model.Science;

public class ScienceRepo implements Crud {

	private DatabaseHelper helper;

	public ScienceRepo(Context context)
	{
		DatabaseManager.init(context);
		helper = DatabaseManager.getInstance().getHelper();
	}

	@Override
	public int create(Object item) {
		
		int index = -1;
		
		Science object = (Science) item;
		try {
			index = helper.getScienceDao().create(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
	}


	@Override
	public int update(Object item) {
		
		int index = -1;
		
		Science object = (Science) item;
		
		try {
			helper.getScienceDao().update(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
	}


	@Override
	public int delete(Object item) {
		
		int index = -1;
		
		Science object = (Science) item;
		
		try {
			helper.getScienceDao().delete(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
	}


	@Override
	public Object findById(int id) {
		
		Science wishList = null;
		try {
			wishList = helper.getScienceDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wishList;
	}


	@Override
	public List<?> findAll() {

		List<Science> items = null;

		try {
			items = helper.getScienceDao().queryForAll();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	@Override
	public List<?> findAll(String category, String noUse) {
		
		List<Science> items = null;
		
		try {

			System.out.print("ItemSize:::GetFinds");

			items = helper.getScienceDao().queryBuilder().where().eq("category", category).query();
			System.out.print("ItemSize:::" + items.size());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
}
