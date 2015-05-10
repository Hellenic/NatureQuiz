package net.blankpace.naturequiz.dao;

import net.blankpace.naturequiz.model.Category;

/**
 * Data Access Object to access the level data.
 * 
 * @author hellenic
 *
 */
public interface GameDao
{
	public Category loadCategory(String categoryFile);
}
