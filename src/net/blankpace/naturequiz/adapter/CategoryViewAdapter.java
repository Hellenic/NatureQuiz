package net.blankpace.naturequiz.adapter;

import java.util.List;

import net.blankpace.naturequiz.R;
import net.blankpace.naturequiz.data.CategoryViewHolder;
import net.blankpace.naturequiz.manager.ProgressManager;
import net.blankpace.naturequiz.model.CategoryView;
import net.blankpace.naturequiz.model.GameProgress;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Category view is used by CategoryActivity and it lists the categories.
 * 
 * @author hellenic
 *
 * @param <T>
 */
public class CategoryViewAdapter<T> extends ArrayAdapter<CategoryView>
{
	private int resource;
	private CategoryView[] categoryViews;
	private ProgressManager progressManager;
	
	public CategoryViewAdapter(Context context, int resource, List<CategoryView> allCategories, ProgressManager progressManager)
	{
		this(context, resource, allCategories.toArray(new CategoryView[] {}), progressManager);
	}
	
	public CategoryViewAdapter(Context context, int resource, CategoryView[] objects, ProgressManager progressManager)
	{
		super(context, resource, objects);
		this.resource = resource;
		this.categoryViews = objects;
		this.progressManager = progressManager;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		CategoryViewHolder holder = null;
		
		// If the view ain't there, inflate it
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(this.resource, parent, false);
		}
		// Otherwise, get the saved one
		else
		{
			holder = (CategoryViewHolder)convertView.getTag();
		}
		
		// TODO Can more of this be recycled?
		populateCategoryView(holder, position, convertView);
		
		return convertView;
	}
	
	private void populateCategoryView(CategoryViewHolder holder, int position, View convertView)
	{
		CategoryView category = categoryViews[position];
		
		holder = new CategoryViewHolder();
		holder.setPosition(position);
		holder.setTextView((TextView)convertView.findViewById(R.id.category_item_text));
		holder.getTextView().setText(category.getName());
		
		GameProgress categoryProgress = progressManager.getProgress(category.getName());
		ProgressBar bar = (ProgressBar)convertView.findViewById(R.id.category_item_progress);
		bar.setMax(categoryProgress.getLevelCount());
		bar.setProgress(categoryProgress.getCompletedCount());
		holder.setProgressBarView(bar);
		
		holder.setPercentView((TextView)convertView.findViewById(R.id.category_item_percent));
		holder.getPercentView().setText(String.valueOf(categoryProgress.getProgressPercentage()) + " %");
		
		holder.setCategory(category);
		convertView.setTag(holder);
	}
}
