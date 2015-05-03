package net.blankpace.naturequiz.service;

import java.util.List;

import android.content.res.AssetManager;
import android.content.res.Resources;
import net.blankpace.naturequiz.dao.CategoryDao;
import net.blankpace.naturequiz.dao.DefaultCategoryDao;
import net.blankpace.naturequiz.model.CategoryView;

public class DefaultCategoryService implements CategoryService {

	private CategoryDao categoryDao;
	
	public DefaultCategoryService(AssetManager assetManager, Resources resources)
	{
		categoryDao = new DefaultCategoryDao(assetManager, resources);
	}

	@Override
	public CategoryView getCategoryByName(String name)
	{
		if (name != null && !name.isEmpty())
			return categoryDao.getCategoryByName(name);
		
		return null;
	}

	@Override
	public List<CategoryView> getAllCategories()
	{
		return categoryDao.getAllCategories();
	}

}
