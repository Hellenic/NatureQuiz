package net.blankpace.naturequiz.model;

public class CategoryView
{
	private String name = "";
	private String file = "";
	private String imagePath = "";
	
	public CategoryView()
	{
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
