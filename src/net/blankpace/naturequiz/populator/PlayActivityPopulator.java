package net.blankpace.naturequiz.populator;

import java.io.IOException;
import java.io.InputStream;

import net.blankpace.naturequiz.R;
import net.blankpace.naturequiz.model.Category;
import net.blankpace.naturequiz.model.GameProgress;
import net.blankpace.naturequiz.model.Level;
import net.blankpace.naturequiz.utils.StringUtils;
import net.blankpace.naturequiz.view.CategoryProgressBarView;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * TODO Cache.
 * 
 * @author hellenic
 *
 */
public class PlayActivityPopulator
{
	private Activity activity;
	
	public PlayActivityPopulator(Activity activity) {
		this.activity = activity;
	}
	
	public void populateQuizView(Level level, Level previousLevel, GameProgress progress)
	{
		EditText answerText = (EditText)activity.findViewById(R.id.game_quiz_answer_input);
		answerText.setText("");
		
		setProgressBarCurrent(level, previousLevel, progress);
	}
	
	public void populateInfoView(String name, String label, String description, String link, String imagePath)
	{
		if (!StringUtils.isEmpty(name))
		{
			TextView headerView = (TextView)activity.findViewById(R.id.game_info_header);
			headerView.setText(name);
		}
		
		if (label != null)
		{
			TextView labelView = (TextView)activity.findViewById(R.id.game_info_label);
			labelView.setText(label);
		}
		
		if (description != null)
		{
			TextView descView = (TextView)activity.findViewById(R.id.game_info_description);
			descView.setText(description);
		}
		
		if (!StringUtils.isEmpty(link))
		{
			TextView descView = (TextView)activity.findViewById(R.id.game_info_link);
			descView.setText(link);
		}
		
		if (!StringUtils.isEmpty(imagePath))
		{
			ImageView descView = (ImageView)activity.findViewById(R.id.game_info_image);
			try {
	        	InputStream is = activity.getAssets().open("images/"+ imagePath);
				Drawable img = Drawable.createFromStream(is, "");
				descView.setImageDrawable(img);
			}
			catch (IOException re) {
				Log.e("LevelFragment", "Resource exception while populating level data... Resource: "+ imagePath);
			}
		}
	}
	
	public void displayWrongAnswerToast(int textRes)
	{
		Toast toast = Toast.makeText(activity.getApplicationContext(), textRes, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(100);
	}

	public void populateProgressBar(Category category, GameProgress progress)
	{
		// Change the activity title to current category's name
		activity.setTitle(category.getName());
		
		// Populate the progress bar
		CategoryProgressBarView barView = (CategoryProgressBarView)activity.findViewById(R.id.category_progressbar);
		
		for (Level level : category.getLevels())
		{
			int colorRes = (progress.getCompleted(level.getId())) ? R.color.level_progressbar_completed : R.color.level_progressbar_empty;
			barView.addBar(activity.getResources().getColor(colorRes));
		}
	}
	
	private void setProgressBarCurrent(Level level, Level previousLevel, GameProgress progress)
	{
		if (progress == null)
			return;
		
		CategoryProgressBarView barView = (CategoryProgressBarView)activity.findViewById(R.id.category_progressbar);
		
		if (previousLevel != null)
		{
			int colorRes = (progress.getCompleted(previousLevel.getId())) ? R.color.level_progressbar_completed : R.color.level_progressbar_empty;
			barView.setColor(previousLevel.getIndex(), activity.getResources().getColor(colorRes));
		}
		
		if (level != null)
		{
			barView.setColor(level.getIndex(), activity.getResources().getColor(R.color.level_progressbar_current));
		}
	}
}
