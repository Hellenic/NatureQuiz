package net.blankpace.naturequiz.view;

import net.blankpace.naturequiz.R;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Category progress bar. Basically a container for multiple progress bars.
 * BarView is always fullscreen wide and the single bars will be scaled based 
 * on how many levels there are in the category.
 * 
 * Category contain multiple levels and each level is shown as one bar.
 * Unaswered levels are shown as yellow. Current level is shown as blue and completed 
 * level is shown with green color.
 * 
 * @author hellenic
 *
 */
public class CategoryProgressBarView extends LinearLayout
{
	private Context context = null;
	
	public CategoryProgressBarView(Context context)
	{
		super(context);
		this.context = context;
		setOrientation(HORIZONTAL);
	}
	
	public CategoryProgressBarView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		setOrientation(HORIZONTAL);
	}
	
	public CategoryProgressBarView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.context = context;
		setOrientation(HORIZONTAL);
	}
	
	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		
		// When layout is drawn, also set up the child bars
		for (int i=0; i<this.getChildCount(); i++)
		{
			ProgressBar bar = (ProgressBar)this.getChildAt(i);
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)bar.getLayoutParams();
			int barMargins = lp.leftMargin + lp.rightMargin;
			
			// Divide the total width for each bar
			int barWidth = parentWidth / this.getChildCount();
			
			// Subtract the margins from bar width
			barWidth -= barMargins;
			
			// Set the bar width
			setWidth(bar, barWidth);
		}
	}

	// Here I need to affect the size of the actual single bar
	// Also it should somehow get updated when device is turned
	public void addBar(int color)
	{
		// Add the bar to view from XML
		LayoutInflater.from(context).inflate(R.layout.category_progressbar_view, this);
		
		// Find the added bar
		int latestChild = this.getChildCount()-1;
		ProgressBar bar = (ProgressBar)this.getChildAt(latestChild);
		if (bar == null)
			return;
		
		// Set bar initial color
		setColor(bar, color);
	}
	
	public void setColor(int index, int color)
	{
		ProgressBar bar = (ProgressBar)this.getChildAt(index);
		if (bar == null)
			return;
		
		setColor(bar, color);
	}
	
	private void setWidth(ProgressBar bar, int width)
	{
		ViewGroup.LayoutParams params = bar.getLayoutParams();
		params.width = width;
		bar.setLayoutParams(params);
	}
	
	private void setColor(ProgressBar bar, int color)
	{
		bar.getProgressDrawable().setColorFilter(color, Mode.MULTIPLY);
		invalidate();
		requestLayout();
	}
}
