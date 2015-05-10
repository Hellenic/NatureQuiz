package net.blankpace.naturequiz.service;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import net.blankpace.naturequiz.dao.DefaultGameDao;
import net.blankpace.naturequiz.dao.GameDao;
import net.blankpace.naturequiz.manager.DefaultProgressManager;
import net.blankpace.naturequiz.manager.ProgressManager;
import net.blankpace.naturequiz.model.Category;
import net.blankpace.naturequiz.model.GameProgress;
import net.blankpace.naturequiz.model.Level;

public class DefaultGameService implements GameService
{
	@SuppressWarnings("unused")
	private static final String TAG = DefaultGameService.class.getName();
	
	private GameDao gameDao;
	private Category currentCategory;
	private Level currentLevel;
	private ProgressManager progressManager;
	
	public DefaultGameService(AssetManager assetManager, Resources resources, String categoryFile, SharedPreferences prefs)
	{
		gameDao = new DefaultGameDao(assetManager, resources);
		currentCategory = gameDao.loadCategory(categoryFile);
		progressManager = new DefaultProgressManager(prefs);
	}

	@Override
	public void setCurrentLevel(int id)
	{
		Level level = getLevelById(id);
		if (level != null)
			currentLevel = level;
		
		progressManager.saveCurrentLevel(currentCategory, currentLevel);
	}
	
	@Override
	public Level getCurrentLevel()
	{
		// If current is still null, game has just loaded
		if (currentLevel == null)
		{
			loadSavedLevel();
		}
		
		return currentLevel;
	}

	@Override
	public void setCurrentCategory(String categoryFile)
	{
		currentCategory = gameDao.loadCategory(categoryFile);
	}

	@Override
	public Category getCurrentCategory()
	{
		return currentCategory;
	}

	@Override
	public Level getLevelById(int id)
	{
		if (getCurrentCategory() == null)
			return null;
		
		for (Level level : currentCategory.getLevels())
		{
			if (level.getId() == id)
				return level;
		}
		
		return null;
	}
	
	@Override
	public Level getLevelByIndex(int levelIndex)
	{
		if (getCurrentCategory() == null)
			return null;
		
		for (Level level : currentCategory.getLevels())
		{
			if (level.getIndex() == levelIndex)
				return level;
		}
		
		return null;
	}
	
	@Override
	public Level getNextLevel()
	{
		// If current is still null, game has just loaded
		if (currentLevel == null)
		{
			loadSavedLevel();
		}
		else
		{
			// Otherwise update ID and get next
			int next = currentLevel.getId()+1;
			currentLevel = getLevelById(next);
		}

		return currentLevel;
	}

	@Override
	public void setCurrentLevelAsCompleted()
	{
		progressManager.completeLevel(currentCategory, currentLevel);
	}
	
	private void loadSavedLevel()
	{
		// Check progress manager if there is saved level, use first if not
		GameProgress gp = progressManager.getProgress(currentCategory.getName());
		if (gp.getCurrentLevelId() > 0)
			currentLevel = getLevelById(gp.getCurrentLevelId());
		else
			currentLevel = getLevelById(1);
	}

	@Override
	public GameProgress getProgress()
	{
		return progressManager.getProgress(currentCategory.getName());
	}
}
