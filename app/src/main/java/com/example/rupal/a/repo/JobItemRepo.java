package com.example.rupal.a.repo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import com.example.rupal.a.db.DatabaseHelper;
import com.example.rupal.a.db.DatabaseManager;
import com.example.rupal.a.model.Job12;

public class JobItemRepo implements Crud{

	private DatabaseHelper helper;

	/*public JobItemRepo(Context context)
	{
		DatabaseManager.init(context);
		helper = DatabaseManager.getInstance().getHelper();
	}

	@Override
	public int create(Object item) {
		
		int index = -1;
		
		Job12 object = (Job12) item;
		try {
			index = helper.getJobItemDao().create(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return index;
	}
	

	@Override
	public int update(Object item) {
		
		int index = -1;
		
		Job12 object = (Job12) item;
		
		try {
			helper.getJobItemDao().update(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
	}

	@Override
	public int delete(Object item) {
		
		int index = -1;
		
		Job12 object = (Job12) item;
		
		try {
			helper.getJobItemDao().delete(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return index;
	}


	@Override
	public Object findById(int id) {
		
		Job12 wishList = null;
		try {
			wishList = helper.getJobItemDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wishList;
	}


	@Override
	public List<?> findAll() {

		List<Job12> items = null;

		try {
			items = helper.getJobItemDao().queryBuilder().orderBy("place",true).query();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	@Override
	public List<?> findAll(String filter, String filter_order)
	{
		List<Job12> items = null;

		boolean order;
		if(filter_order.equals("asc")) {
			order = true;
		} else {
			order = false;
		}

		try {
			// items = helper.getJobItemDao().queryBuilder().orderBy("salary",true).query();
			System.out.println("JOB Fetch" );

			items = helper.getJobItemDao().queryBuilder().query();

			System.out.println("JOB Completed" + items.size());

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("JOB Exception" + e.getMessage().toString());

		}

		return items;
	}
*/}
