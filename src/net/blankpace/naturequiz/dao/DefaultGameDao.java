package net.blankpace.naturequiz.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import net.blankpace.naturequiz.manager.ResourceManager;
import net.blankpace.naturequiz.model.Category;
import net.blankpace.naturequiz.model.Level;
import net.blankpace.naturequiz.utils.StringUtils;

/**
 * Level Data Access Object.
 * Reads the levels from XML assets.
 * 
 * @author hellenic
 */
public class DefaultGameDao implements GameDao
{
	private static final String TAG = DefaultGameDao.class.getName();
	
	private AssetManager assetManager;
	private ResourceManager resourceManager;
	
	public DefaultGameDao(AssetManager assetManager, Resources resources)
	{
		this.assetManager = assetManager;
		this.resourceManager = new ResourceManager(assetManager, resources);
	}
	
	@Override
	public Category loadCategory(String categoryFile)
	{
		try
		{
			XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			
			InputStream is = assetManager.open("categories/" + categoryFile);
			String categoryCode = getCategoryCode(categoryFile);
			
	        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            
            Category category = parseCategoryFromXML(parser, categoryCode);
            category.setFile(categoryFile);
            
            return category;
		}
		catch (Exception ioe)
		{
			Log.e(TAG, "Error while parsing the category XML... "+ categoryFile, ioe);
		}
		
		return null;
	}
	
	private String getCategoryCode(String categoryFile)
	{
		if (StringUtils.isEmpty(categoryFile))
			return categoryFile;
		
		if (categoryFile.contains("."))
			return categoryFile.substring(0, categoryFile.indexOf("."));
		
		return null;
	}

	private Category parseCategoryFromXML(XmlPullParser parser, String categoryCode) throws IOException, XmlPullParserException
	{
		Category category = new Category();
		int eventType = parser.getEventType();
		Level level = null;
		List<Level> levels = new ArrayList<Level>();
		
		int index = 0;
    	String name = null;
    	String text = null;
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                    break;
                    
                case XmlPullParser.START_TAG:
                	name = parser.getName();
                	if (name.equalsIgnoreCase("level"))
                	{
                		level = new Level();
                		String id = parser.getAttributeValue(null, "id");
            			try {
            				level.setId(Integer.valueOf(id));
            			} catch (NumberFormatException nfe) {
            				Log.e(TAG, "Unable to parse level's ID: "+ id);
            			}
            			level.setIndex(index);
            			index++;
                	}
                	break;
                	
                case XmlPullParser.TEXT:
                	text = parser.getText();
                	break;
                	
                case XmlPullParser.END_TAG:
                	name = parser.getName();
                	
                	if (name.equalsIgnoreCase("level"))
                	{
                		levels.add(level);
                		level = null;
                	}
                	else
                	{
                    	if (level == null)
                    		populateCategory(category, name, text);
                    	else
                    		populateLevel(level, categoryCode, name, text);
                	}
                	
                	break;
            }
            
            eventType = parser.next();
        }
        
        category.setLevels(levels);
        return category;
	}
	
	private void populateCategory(Category category, String name, String value) 
	{
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value))
			return;
		
		if (name.equalsIgnoreCase("image"))
		{
			//category.setImage(resourceManager.resolveDrawableResource(value));
			category.setImagePath(value);
		}
		else if (name.equalsIgnoreCase("levels") || name.equalsIgnoreCase("category"))
		{
			// TODO Refactor
		}
		else
		{
			invokeStringSetter(category, category.getClass(), name, value);
		}
	}
	
	private void populateLevel(Level level, String categoryCode, String name, String value)
	{
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value))
			return;
		
		// TODO Refactor
		if (name.equalsIgnoreCase("image"))
		{
			//level.setImage(resourceManager.resolveDrawableResource(categoryCode +"/"+ value));
			level.setImagePath(categoryCode +"/"+ value);
		}
		else if (name.equalsIgnoreCase("answers"))
		{
			level.setAnswers(resourceManager.resolveStringArrayResource(value));
		}
		else if (name.equalsIgnoreCase("synonyms"))
		{
			level.setSynonyms(resourceManager.resolveStringArrayResource(value));
		}
		else if (name.equalsIgnoreCase("facts"))
		{
			level.setFacts(resourceManager.resolveStringArrayResource(value));
		}
		else
		{
			invokeStringSetter(level, level.getClass(), name, value);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void invokeStringSetter(Object receiver, Class clazz, String name, String value)
	{
		try
		{
			String methodName = "set"+ StringUtils.capitalize(name);
			Method setter = clazz.getMethod(methodName, new Class[] { String.class });
			
			String localizedValue = resourceManager.resolveStringResource(value);
			setter.invoke(receiver, localizedValue);
		}
		catch (NoSuchMethodException e) {
			Log.e(TAG, "No method on Level for: " + name);
		}
		catch (Exception e) {
			Log.e(TAG, "Error on reflection while populating data...");
		}
	}
}
