package net.blankpace.naturequiz.service;

import java.util.List;

import net.blankpace.naturequiz.model.CategoryView;

/**
 * Category service is meant to be used on category listing.
 * It is separated from GameDao for speed purposes.
 * 
 * 
 * @author hellenic
 */
public interface CategoryService
{
	public static final String CURRENT_CATEGORY_FILE_KEY = "CurrentCategory";
	
	public CategoryView getCategoryByName(String name);
	
	public List<CategoryView> getAllCategories();
}
