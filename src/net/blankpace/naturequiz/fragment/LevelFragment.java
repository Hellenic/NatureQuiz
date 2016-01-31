package net.blankpace.naturequiz.fragment;

import java.io.IOException;
import java.io.InputStream;

import net.blankpace.naturequiz.R;
import net.blankpace.naturequiz.model.Level;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelFragment extends Fragment
{
	private Level level;
	
    public LevelFragment()
    {
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_game_level, container, false);
		
        this.level = (Level)this.getArguments().get("level");
        
        populateView(rootView);
        
        return rootView;
    }
	
	private void populateView(View view)
	{
		TextView hint = (TextView)view.findViewById(R.id.game_quiz_hint);
		hint.setText(level.getHint());
		
		ImageView image = (ImageView)view.findViewById(R.id.game_quiz_image);
		try {
        	InputStream is = getActivity().getAssets().open("images/"+ level.getImagePath());
			Drawable img = Drawable.createFromStream(is, "");
			image.setImageDrawable(img);
		}
		catch (IOException re) {
			Log.e("LevelFragment", "Resource exception while populating level data... Resource: "+ level.getImagePath());
		}
	}
}
