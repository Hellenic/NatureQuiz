package net.blankpace.naturequiz.data;

import net.blankpace.naturequiz.model.CategoryView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CategoryViewHolder
{
	int position;
	TextView textView;
	ProgressBar progressBarView;
	TextView percentView;
	CategoryView category;
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public TextView getTextView() {
		return textView;
	}
	public void setTextView(TextView textView) {
		this.textView = textView;
	}
	public ProgressBar getProgressBarView() {
		return progressBarView;
	}
	public void setProgressBarView(ProgressBar progressBarView) {
		this.progressBarView = progressBarView;
	}
	public TextView getPercentView() {
		return percentView;
	}
	public void setPercentView(TextView percentView) {
		this.percentView = percentView;
	}
	public CategoryView getCategory() {
		return category;
	}
	public void setCategory(CategoryView category) {
		this.category = category;
	}
	
}
