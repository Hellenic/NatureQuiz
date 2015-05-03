package net.blankpace.naturequiz.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import net.blankpace.naturequiz.QuizConstants;
import net.blankpace.naturequiz.manager.ResourceManager;
import net.blankpace.naturequiz.model.CategoryView;
import net.blankpace.naturequiz.utils.StringUtils;

/**
 * Data access object to pull the list of CategoryView from XML files.
 * Separated from DefaultGameDao for speed.
 * 
 * TODO Some duplicate code with DefaultGameDao (which reads the same files).
 * 
 * @author hellenic
 *
 */
public class DefaultCategoryDao implements CategoryDao
{
	private static final String TAG = DefaultCategoryDao.class.getName();
	
	private List<CategoryView> categories;
	private AssetManager assetManager;
	private ResourceManager resourceManager;
	
	public DefaultCategoryDao(AssetManager assetManager, Resources resources)
	{
		this.assetManager = assetManager;
		this.resourceManager = new ResourceManager(assetManager, resources);
		this.categories = new ArrayList<CategoryView>();
		
		loadCategories();
	}
	
	private void loadCategories()
	{
		try
		{
			for (String file : assetManager.list(QuizConstants.ASSETS_CATEGORY_FOLDER))
			{
				CategoryView category = loadCategoryView(file);
				categories.add(category);
			}
		}
		catch (IOException e)
		{
			// If this happens, nothing really works.
		}
	}
	
	private CategoryView loadCategoryView(String categoryFile)
	{
		CategoryView category = new CategoryView();
		
		try
		{
			XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			
			InputStream is = assetManager.open(QuizConstants.ASSETS_CATEGORY_FOLDER +"/"+ categoryFile);
			
	        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	        parser.setInput(is, null);
	        
	        category = parseCategoryViewFromXML(parser);
	        category.setFile(categoryFile);
		}
		catch (IOException | XmlPullParserException ioe)
		{
			Log.e(TAG, "Error while parsing the category XML... "+ categoryFile, ioe);
		}
		
		return category;
	}
	
	private CategoryView parseCategoryViewFromXML(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		CategoryView category = new CategoryView();
		int eventType = parser.getEventType();
		
    	String name = null;
    	String text = null;
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                	name = parser.getName();
                	break;
                	
                case XmlPullParser.TEXT:
                	text = parser.getText();
                	break;
                	
                case XmlPullParser.END_TAG:
                	name = parser.getName();
            		switch (name)
            		{
	            		case "name":
	            			category.setName(resourceManager.resolveStringResource(text));
	            			break;
	            		case "image":
	            			//category.setImage(resourceManager.resolveDrawableResource(text));
	            			category.setImagePath(text);
	            			break;
            		}
                	break;
            }
            
            // TODO This is still a stub.
            
            // Once everything is populated for CategoryView, stop reading the file.
            if (!StringUtils.isEmpty(category.getName()))
            	return category;
            
            eventType = parser.next();
        }
        
        return category;
	}
	
	@Override
	public CategoryView getCategoryByName(String name)
	{
		if (name == null || name.isEmpty())
			return null;
		
		for (CategoryView category : categories)
		{
			if (category.getName().equals(name))
				return category;
		}
		
		return null;
	}

	@Override
	public List<CategoryView> getAllCategories()
	{
		return categories;
	}

}
