package net.blankpace.naturequiz.dao;

import java.util.List;

import net.blankpace.naturequiz.model.CategoryView;

public interface CategoryDao
{
	public CategoryView getCategoryByName(String name);
	
	public List<CategoryView> getAllCategories();
}
