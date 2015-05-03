package net.blankpace.naturequiz.service;

import net.blankpace.naturequiz.model.Category;
import net.blankpace.naturequiz.model.GameProgress;
import net.blankpace.naturequiz.model.Level;

/**
 * 
 * @author hellenic
 *
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
