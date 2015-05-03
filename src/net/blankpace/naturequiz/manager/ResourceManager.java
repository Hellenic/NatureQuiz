package net.blankpace.naturequiz.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ResourceManager
{
	private static final String TAG = ResourceManager.class.getName();
	private static final String PACKAGE_NAME = "net.blankpace.naturequiz";
	
	private AssetManager assetManager;
	private Resources resources;
	
	public ResourceManager(AssetManager assetManager, Resources resources)
	{
		this.assetManager = assetManager;
		this.resources = resources;
	}
	
	/**
	 * Resolves given filename to Drawable resource.
	 * 
	 * @param imageResource
	 * @return Drawable, if found
	 */
	public Drawable resolveDrawableResource(String imageResource)
	{
		try {
        	InputStream is = assetManager.open("images/"+ imageResource);
			return Drawable.createFromStream(is, "");
		}
		catch (IOException re) {
			Log.e(TAG, "Resource exception while populating level data... Resource: "+ imageResource);
		}
		
		return null;
	}
	
	/**
	 * Resolves given filename to Bitmap resource.
	 * 
	 * @param imageResource
	 * @return Bitmap, if found
	 */
	public Bitmap resolveBitmapResource(String imageResource)
	{
		try
		{
	    	final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        InputStream is = assetManager.open("images/"+ imageResource);
	    	return BitmapFactory.decodeStream(is);
		}
		catch (IOException re) {
			Log.e(TAG, "Resource exception while populating level data... Resource: "+ imageResource);
		}
		
		return null;
	}
	
	/**
	 * Resolves given Android @string resource text to actual resource.
	 *  
	 * @param textResource
	 * @return The resource text if found.
	 */
	public String resolveStringResource(String textResource)
	{
		try
		{
	        return resources.getString(getResourceIdentifier(textResource));
		}
		catch (NotFoundException | ArrayIndexOutOfBoundsException | NullPointerException e)
		{
			return textResource;
		}
	}
	
	public List<String> resolveStringArrayResource(String textResource)
	{
		List<String> resourceArray = new ArrayList<>();
		
		try
		{
	        int id = getResourceIdentifier(textResource);
	        resourceArray = Arrays.asList(resources.getStringArray(id));
		}
		catch (NotFoundException | ArrayIndexOutOfBoundsException | NullPointerException e)
		{
			resourceArray.add(textResource);
		}
		
		return resourceArray;
	}
	
	private int getResourceIdentifier(String textResource)
	{
        String[] split = textResource.split("/");
        String pack = split[0].replace("@", "");
        String resourceName = split[1];
        return resources.getIdentifier(resourceName, pack, PACKAGE_NAME);
	}
}
