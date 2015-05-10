package net.blankpace.naturequiz.adapter;

import java.util.List;

import net.blankpace.naturequiz.fragment.LevelFragment;
import net.blankpace.naturequiz.model.Level;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * This adapter is used by PlayActivity and it contains all the levels.
 * Adapter makes it easy to change and page the levels.
 * 
 * @author hellenic
 *
 */
public class LevelCollectionPagerAdapter extends FragmentStatePagerAdapter
{
	private List<Level> levels;
	
	public LevelCollectionPagerAdapter(FragmentManager fm, List<Level> levels)
	{
		super(fm);
		
		this.levels = levels;
	}

	@Override
	public Fragment getItem(int index)
	{
		LevelFragment levelFragment = new LevelFragment();
		
		Level level = levels.get(index);
		Bundle bundle = new Bundle();
		bundle.putSerializable("level", level);
		levelFragment.setArguments(bundle);
		
		return levelFragment;
	}

	@Override
	public int getCount()
	{
		return levels.size();
	}
	
    @Override
    public CharSequence getPageTitle(int position)
    {
    	return "Level " + levels.get(position).getId();
    }
}
