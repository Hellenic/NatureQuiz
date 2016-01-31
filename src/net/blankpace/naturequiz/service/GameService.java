package net.blankpace.naturequiz.service;

import net.blankpace.naturequiz.model.Category;
import net.blankpace.naturequiz.model.GameProgress;
import net.blankpace.naturequiz.model.Level;

/**
 * Service that handles the levels.
 * Can be used to fetch current level, to change level or set next level.
 * 
 * @author hellenic
 */
public interface GameService
{
	public void setCurrentLevel(int id);
	
	public Level getCurrentLevel();
	
	public void setCurrentLevelAsCompleted();
	
	public void setCurrentCategory(String categoryFile);
	
	public Category getCurrentCategory();
	
	public Level getLevelById(int id);
	
	public Level getLevelByIndex(int levelIndex);
	
	public Level getNextLevel();

	public GameProgress getProgress();
}
