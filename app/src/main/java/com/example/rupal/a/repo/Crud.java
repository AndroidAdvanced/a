package com.example.rupal.a.repo;

import java.util.List;

interface Crud
{
	public int create(Object item);
	public int update(Object item);
	public int delete(Object item);
	public Object findById(int id);
//	public Object findByEq(String s, Object id);

	public List<?> findAll();
	public List<?> findAll(String string, String filter);
}
