package net.blankpace.naturequiz;

import net.blankpace.naturequiz.adapter.LevelCollectionPagerAdapter;
import net.blankpace.naturequiz.fragment.PlayFragment;
import net.blankpace.naturequiz.model.AnswerStatus;
import net.blankpace.naturequiz.model.Category;
import net.blankpace.naturequiz.model.Level;
import net.blankpace.naturequiz.populator.PlayActivityPopulator;
import net.blankpace.naturequiz.service.CategoryService;
import net.blankpace.naturequiz.service.DefaultGameService;
import net.blankpace.naturequiz.service.GameService;
import net.blankpace.naturequiz.utils.StringUtils;
import net.blankpace.naturequiz.utils.UiUtils;
import net.blankpace.naturequiz.validator.AnswerValidator;
import net.blankpace.naturequiz.validator.LooseAnswerValidator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class PlayActivity extends ActionBarActivity
{
	@SuppressWarnings("unused")
	private static final String TAG = PlayActivity.class.getName();
	
	private static final int DEFAULT_INCORRECT_ANSWER = R.string.quiz_default_incorrect_answer_text;
	
	private GameService gameService;
	private AnswerValidator answerValidator;
	
	private ViewSwitcher viewSwitcher;
	private ViewPager viewPager;
	private PlayActivityPopulator populator;
	private AnswerStatus currentAnswerStatus;
	
	private boolean endOfCategory = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		if (savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlayFragment()).commit();
		}
		
		// Load the data saved to intent on category select
		String categoryFile = getIntent().getStringExtra(CategoryService.CURRENT_CATEGORY_FILE_KEY);
		
		// Get the preferences
		SharedPreferences prefs = getSharedPreferences(QuizConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
		
		// Initialize services
		gameService = new DefaultGameService(getAssets(), getResources(), categoryFile, prefs);
		answerValidator = new LooseAnswerValidator();
		populator = new PlayActivityPopulator(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int currentIndex = gameService.getCurrentLevel().getIndex();
		switch (item.getItemId())
		{
		case R.id.game_action_back_fast:
			viewPager.setCurrentItem(currentIndex - 5);
			break;
		case R.id.game_action_back:
			viewPager.setCurrentItem(currentIndex - 1);
			break;
		case R.id.game_action_forward:
			viewPager.setCurrentItem(currentIndex + 1);
			break;
		case R.id.game_action_forward_fast:
			viewPager.setCurrentItem(currentIndex + 5);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		
		viewSwitcher = (ViewSwitcher) findViewById(R.id.play_view_switcher);
		
		viewSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		viewSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		
		Category cat = gameService.getCurrentCategory();
		
		LevelCollectionPagerAdapter pager = new LevelCollectionPagerAdapter(getSupportFragmentManager(), cat.getLevels());
		viewPager = (ViewPager) findViewById(R.id.game_level_pager);
		viewPager.setAdapter(pager);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position)
                {
                    gotoLevel(position);
                }
	    });
		
		// Answer input event listener
		EditText answerText = (EditText)findViewById(R.id.game_quiz_answer_input);
		answerText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
				{
					answer(view);
					getSupportActionBar().hide();
					return true;
				}
		});
		
		// Populate category bar when Activity is opened
		populator.populateProgressBar(cat, gameService.getProgress());
		
		// Populate category info view
		populator.populateInfoView(cat.getName(), "", cat.getDescription(), cat.getLink(), cat.getImagePath());
		// And initially hide the action bar, since info screen is showing
		getSupportActionBar().hide();
	}
	
	private void gotoLevel(int nextLevelIndex)
	{
		Level currentLevel = gameService.getCurrentLevel();
		
		Level nextLevel = gameService.getLevelByIndex(nextLevelIndex);
		gameService.setCurrentLevel(nextLevel.getId());
		
		displayLevel(nextLevel, currentLevel);
	}

	/**
	 * Display given level
	 * @param levelId
	 */
	private void displayLevel(Level nextLevel, Level currentLevel)
	{
		getSupportActionBar().show();
		int levelIndex = nextLevel.getIndex();
		int maxIndex = gameService.getCurrentCategory().getLevels().size();
		
		if (levelIndex < 0)
			levelIndex = 0;
		if (levelIndex > maxIndex)
			levelIndex = maxIndex;
		
		viewPager.setCurrentItem(nextLevel.getIndex());
		populator.populateQuizView(nextLevel, currentLevel, gameService.getProgress());
	}
	
	/**
	 * On answer button click
	 * 
	 * @param view
	 */
	public void answer(View view)
	{
		EditText answerTextView = (EditText)findViewById(R.id.game_quiz_answer_input);
		String userInput = answerTextView.getText().toString();
		if (StringUtils.isEmpty(userInput))
		{
			return;
		}
		
		Level currentLevel = gameService.getCurrentLevel();
		currentAnswerStatus = answerValidator.validate(currentLevel, userInput);
		
		// Incase of incorrect answer, display only the toast
		if (AnswerStatus.INCORRECT_ANSWER.equals(currentAnswerStatus))
		{
			populator.displayWrongAnswerToast(DEFAULT_INCORRECT_ANSWER);
			return;
		}
		
		String label = "";
		if (AnswerStatus.CORRECT_ANSWER.equals(currentAnswerStatus)) {
			label = currentLevel.getAnswerText();
		}
		else if (AnswerStatus.CORRECT_SYNONYM.equals(currentAnswerStatus)) {
			label = currentLevel.getSynonymText();
		}
		
		gameService.setCurrentLevelAsCompleted();
		populator.populateInfoView(currentLevel.getName(), label, currentLevel.getDescription(), currentLevel.getLink(), currentLevel.getImagePath());
		
		// Hide the soft keyboard
		UiUtils.hideSoftwareKeyboard(getCurrentFocus());
	    
	    // Show next view
		viewSwitcher.showNext();
	}
	
	/**
	 * On next view 'okay' button click
	 * 
	 * @param view
	 */
	public void confirmInfo(View view)
	{
		// If tag is set to button, we can quit the category
		if (endOfCategory)
		{
			endOfCategory = false;
			finish();
		}
		
		// If game still has more levels, show the next one
		Level currentLevel = gameService.getCurrentLevel();
		Level nextLevel = gameService.getNextLevel();
		if (nextLevel != null)
		{
			displayLevel(nextLevel, currentLevel);
		}
		// Game completed, no more levels!
		else
		{
			// Populate the category ending
			// And set tag to button, so we know to quit when it's pressed again
			Category cat = gameService.getCurrentCategory();
			// TODO Different text if not totally completed
			populator.populateInfoView(cat.getName(), "",  cat.getCompletedText(),cat.getLink(), cat.getImagePath());
			endOfCategory = true;
			viewSwitcher.showNext();
		}
		
		viewSwitcher.showNext();
	}
}
