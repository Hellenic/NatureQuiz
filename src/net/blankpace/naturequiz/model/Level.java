package net.blankpace.naturequiz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable
{
	private static final long serialVersionUID = -5004047762714314057L;
	
	private int id = 0;
	private int index = 0;
	private String name = "";
	private String hint = "";
	private String description = "";
	private List<String> answers = new ArrayList<>();
	private String answerText = "";
	private List<String> synonyms = new ArrayList<>();
	private String synonymText = "";
	private String imagePath;
	// TODO Sound
//	private Sound sound;
	private String link;
	private List<String> facts = new ArrayList<>();
	
	public Level()
	{
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}
	
	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public String getSynonymText() {
		return synonymText;
	}

	public void setSynonymText(String synonymText) {
		this.synonymText = synonymText;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}

	public List<String> getFacts() {
		return facts;
	}

	public void setFacts(List<String> facts) {
		this.facts = facts;
	}

	@Override
	public String toString()
	{
		return "Level: " + getId() +". " + getName();
	}
}
