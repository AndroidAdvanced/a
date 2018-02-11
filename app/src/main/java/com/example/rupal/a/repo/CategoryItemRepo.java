package com.example.rupal.a.repo;

import android.content.Context;

import com.example.rupal.a.db.DatabaseHelper;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.model.Categoryname;

import java.sql.SQLException;
import java.util.List;

public class CategoryItemRepo implements Crud{

	private DatabaseHelper helper;

	public CategoryItemRepo(Context context)
	{
		DatabaseManager.init(context);
		helper = DatabaseManager.getInstance().getHelper();
	}

	@Override
	public int create(Object item) {
		
		int index = -1;
		
		Categoryname object = (Categoryname) item;
		try {
			index = helper.getCategoryItemDao().create(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return index;
	}

	@Override
	public int update(Object item) {
		
		int index = -1;
		
		Categoryname object = (Categoryname) item;
		
		try {
			helper.getCategoryItemDao().update(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
	}


	@Override
	public int delete(Object item) {
		
		int index = -1;

		Categoryname object = (Categoryname) item;
		
		try {
			helper.getCategoryItemDao().delete(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
	}


	@Override
	public Object findById(int id) {

		Categoryname wishList = null;
		try {
			wishList = helper.getCategoryItemDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wishList;
	}


	@Override
	public List<?> findAll() {
		
		List<Categoryname> items = null;
		
		try {
			items =  helper.getCategoryItemDao().queryForAll();
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
