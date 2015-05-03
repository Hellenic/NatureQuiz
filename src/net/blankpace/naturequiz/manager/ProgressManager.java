package net.blankpace.naturequiz.manager;

import net.blankpace.naturequiz.model.Category;
import net.blankpace.naturequiz.model.GameProgress;
import net.blankpace.naturequiz.model.Level;

public interface ProgressManager
{
	public void setProgress(GameProgress progress);
	
	public GameProgress getProgress(String categoryName);
	
	public void resetCategory(String categoryName);
	
	public void clearProgress();
	
	public void saveCurrentLevel(Category currentCategory, Level currentLevel);

	public void completeLevel(Category currentCategory, Level currentLevel);
}
