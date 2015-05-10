package net.blankpace.naturequiz;

import net.blankpace.naturequiz.adapter.CategoryViewAdapter;
import net.blankpace.naturequiz.data.CategoryViewHolder;
import net.blankpace.naturequiz.dialog.ResetCategoryDialogFragment;
import net.blankpace.naturequiz.manager.DefaultProgressManager;
import net.blankpace.naturequiz.manager.ProgressManager;
import net.blankpace.naturequiz.model.CategoryView;
import net.blankpace.naturequiz.service.CategoryService;
import net.blankpace.naturequiz.service.DefaultCategoryService;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * View that lists all the available categories.
 * Category view will show you the progress of each category and allows you select any of them.
 * Selecting a category will start a PlayActivity.
 * 
 * @author hellenic
 *
 */
public class CategoryActivity extends ActionBarActivity implements OnItemClickListener, OnItemLongClickListener
{
	private CategoryService categoryService;
	private ProgressManager progressManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		categoryService = new DefaultCategoryService(getAssets(), getResources());
		
		ListView categoryList = (ListView)findViewById(R.id.category_list);
		progressManager = new DefaultProgressManager(getSharedPreferences(QuizConstants.PREFERENCE_NAME, Context.MODE_PRIVATE));
		
		categoryList.setAdapter(new CategoryViewAdapter<CategoryView>(this, R.layout.item_category, categoryService.getAllCategories(), progressManager));

		categoryList.setOnItemClickListener(this); 
		categoryList.setOnItemLongClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		LinearLayout layout = (LinearLayout)view;
		CategoryViewHolder holder = (CategoryViewHolder)layout.getTag();
		String categoryFile = holder.getCategory().getFile();
		
		Intent intent = new Intent(this, PlayActivity.class);
		intent.putExtra(CategoryService.CURRENT_CATEGORY_FILE_KEY, categoryFile);
		startActivity(intent);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
	{
		LinearLayout layout = (LinearLayout)view;
		CategoryViewHolder holder = (CategoryViewHolder)layout.getTag();
		String categoryName = holder.getCategory().getName();
		
		ResetCategoryDialogFragment dialogFragment = new ResetCategoryDialogFragment(progressManager, categoryName);
		dialogFragment.show(getFragmentManager(), "ResetCategoryDialog-"+ categoryName);
		
		// Return true, not to trigger click event also
		return true;
	}
}
