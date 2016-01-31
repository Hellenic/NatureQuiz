package net.blankpace.naturequiz.event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Detects left and right swipes across a view.
 */
public class OnSwipeTouchListener implements OnTouchListener
{
//	RelativeLayout quizLayout = (RelativeLayout) findViewById(R.id.game_quiz_layout);
//	quizLayout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
//	    @Override
//	    public void onSwipeLeft() {
//	    	int currentLevelId = gameService.getCurrentLevel().getId();
//	        gotoLevel(currentLevelId+1);
//	    }
//	    @Override
//	    public void onSwipeRight() {
//	    	int currentLevelId = gameService.getCurrentLevel().getId();
//	        gotoLevel(currentLevelId-1);
//	    }
//	});
	
    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void onSwipeLeft()
    {
    	Log.v("Swiper", "Swiped left.");
    }

    public void onSwipeRight()
    {
    	Log.v("Swiper", "Swiped left.");
    }

    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener implements OnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
            {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }
            return false;
        }
    }
}