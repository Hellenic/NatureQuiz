package net.blankpace.naturequiz.dao;

import net.blankpace.naturequiz.model.Category;

public interface GameDao
{
	public Category loadCategory(String categoryFile);
}
