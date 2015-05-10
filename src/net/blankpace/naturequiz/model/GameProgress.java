package net.blankpace.naturequiz.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 
 * TODO Refactor. Model should not have JSON logic.
 * 
 * @author hellenic
 */
public class GameProgress
{
	private static final String KEY_CATEGORY_NAME = "categoryName";
	private static final String KEY_CURRENT_LEVEL = "currentLevelId";
	private static final String KEY_LEVEL_COUNT = "categoryLevelCount";
	private static final String KEY_LEVEL_PROGRESS = "categoryLevelProgress";
	
	private String categoryName;
	private int currentLevelId;
	private Map<Integer, Boolean> levelProgress;
	private int levelCount;
	private int completedCount;
	
	public GameProgress()
	{
		categoryName = "";
		currentLevelId = 0;
		levelCount = 0;
		levelProgress = new HashMap<>();
	}
	
	public GameProgress(String jsonString)
	{
		this();
		
		try
		{
			JSONObject object = (JSONObject) new JSONTokener(jsonString).nextValue();
			
			categoryName = object.getString(KEY_CATEGORY_NAME);
			currentLevelId = object.getInt(KEY_CURRENT_LEVEL);
			levelCount = object.getInt(KEY_LEVEL_COUNT);
			
			JSONObject levels = object.getJSONObject(KEY_LEVEL_PROGRESS);
			
			Iterator<String> keyIter = levels.keys();
			while (keyIter.hasNext())
			{
				String keyString = keyIter.next();
				Integer key = Integer.valueOf(keyString);
				Boolean completed = Boolean.valueOf(levels.getBoolean(keyString));
				levelProgress.put(key, completed);
				if (Boolean.TRUE.equals(completed))
					completedCount++;
			}
		}
		catch (JSONException | NullPointerException e)
		{
			// If it fails, then it fails.
		}
	}
	
	public void setCompleted(Level currentLevel)
	{
		levelProgress.put(currentLevel.getId(), Boolean.TRUE);
	}
	
	public boolean getCompleted(int levelId)
	{
		Integer key = Integer.valueOf(levelId);
		if (levelProgress.containsKey(key))
		{
			return Boolean.TRUE.equals(levelProgress.get(key));
		}
		
		return false;
	}
	
	public int getProgressPercentage() {
		if (levelCount == 0)
			return 0;
		
		Double percent = ((Double.valueOf(completedCount) / Double.valueOf(levelCount)) * 100);
		
		// Note! This will get boxed, as the method returns int.
		return percent.intValue();
	}
	public int getLevelCount() {
		return levelCount;
	}
	public void setLevelCount(int levelCount) {
		this.levelCount = levelCount;
	}
	public int getCompletedCount() {
		return completedCount;
	}
	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCurrentLevelId() {
		return currentLevelId;
	}
	public void setCurrentLevelId(int currentLevelId) {
		this.currentLevelId = currentLevelId;
	}
	
	@Override
	public String toString()
	{
		try
		{
			JSONObject progress = new JSONObject();
			progress.put(KEY_CATEGORY_NAME, categoryName);
			progress.put(KEY_CURRENT_LEVEL, currentLevelId);
			progress.put(KEY_LEVEL_COUNT, levelCount);
			
			JSONObject levels = new JSONObject();
			for (Integer key : levelProgress.keySet())
			{
				levels.put(String.valueOf(key), levelProgress.get(key).booleanValue());
			}
			progress.put(KEY_LEVEL_PROGRESS, levels);
			
			return progress.toString();
		}
		catch (JSONException | NullPointerException e)
		{
			// Too bad.
		}
		
		return null;
	}
}
