package net.blankpace.naturequiz.model;

import java.util.ArrayList;
import java.util.List;

public class Category extends CategoryView
{
	private String description = "";
	private String link = "";
	private String completedText = "";
	private String progressText = "";
	private List<Level> levels = null;
	
	public Category()
	{
		levels = new ArrayList<Level>();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCompletedText() {
		return completedText;
	}

	public void setCompletedText(String completedText) {
		this.completedText = completedText;
	}

	public String getProgressText() {
		return progressText;
	}

	public void setProgressText(String progressText) {
		this.progressText = progressText;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}
	
	@Override
	public String toString()
	{
		return "Category: "+ getName() + " // File: "+ getFile() + " - Levels: " + getLevels().size() +"\nDescription: " + getDescription();
	}
}
