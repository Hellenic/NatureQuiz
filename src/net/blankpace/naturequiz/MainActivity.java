package net.blankpace.naturequiz;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * First simple activity of the game.
 * Has a play-button and a help-button.
 * 
 * @author hellenic
 */
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			showSettings(item.getActionView());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void play(View view)
	{
		Intent intent = new Intent(this, CategoryActivity.class);
		startActivity(intent);
	}
	
	public void showSettings(View actionView)
	{
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void showHelp(View view)
	{
		Intent intent = new Intent(this, HelpActivity.class);
		startActivity(intent);
	}
}
