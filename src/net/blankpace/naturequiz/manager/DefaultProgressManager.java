package net.blankpace.naturequiz.manager;

import android.content.SharedPreferences;
import net.blankpace.naturequiz.model.Category;
import net.blankpace.naturequiz.model.GameProgress;
import net.blankpace.naturequiz.model.Level;
import net.blankpace.naturequiz.utils.StringUtils;

public class DefaultProgressManager implements ProgressManager
{
	@SuppressWarnings("unused")
	private static final String TAG = DefaultProgressManager.class.getName();
	
	private static final String PREF_KEY_CATEGORY_STATUS_PREFIX = "CategoryStatus_";
	
	private SharedPreferences sharedPrefs;
	
	public DefaultProgressManager(SharedPreferences sharedPrefs)
	{
		this.sharedPrefs = sharedPrefs;
	}

	@Override
	public void resetCategory(String categoryName)
	{
		if (sharedPrefs.contains(PREF_KEY_CATEGORY_STATUS_PREFIX + categoryName))
		{
			SharedPreferences.Editor editor = sharedPrefs.edit();
			editor.remove(PREF_KEY_CATEGORY_STATUS_PREFIX + categoryName);
			editor.commit();
		}
	}
	
	@Override
	public void clearProgress()
	{
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.clear();
		editor.commit();
	}

	@Override
	public void saveCurrentLevel(Category currentCategory, Level currentLevel)
	{
		if (currentCategory == null || currentLevel == null)
			return;
		
		String categoryName = currentCategory.getName();
		GameProgress progress = getProgress(categoryName);
		progress.setCurrentLevelId(currentLevel.getId());
		
		setProgress(progress);
	}

	@Override
	public void completeLevel(Category currentCategory, Level currentLevel)
	{
		if (currentCategory == null || currentLevel == null)
			return;
		
		String categoryName = currentCategory.getName();
		GameProgress progress = getProgress(categoryName);
		progress.setCompleted(currentLevel);
		progress.setLevelCount(currentCategory.getLevels().size());
		
		setProgress(progress);
	}

	@Override
	public GameProgress getProgress(String categoryName)
	{
		if (StringUtils.isEmpty(categoryName))
			return null;
		
		String key = PREF_KEY_CATEGORY_STATUS_PREFIX + categoryName;
		GameProgress gp = null;
		if (sharedPrefs.contains(key))
		{
			String progressJson = sharedPrefs.getString(key, null);
			gp = new GameProgress(progressJson);
		}
		else
		{
			gp = new GameProgress();
			gp.setCategoryName(categoryName);
		}
		
		return gp;
	}

	@Override
	public void setProgress(GameProgress progress)
	{
		if (progress == null)
			return;
		
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString(PREF_KEY_CATEGORY_STATUS_PREFIX + progress.getCategoryName(), progress.toString());
		editor.commit();
	}
}
