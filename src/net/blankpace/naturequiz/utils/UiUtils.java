package net.blankpace.naturequiz.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UiUtils
{
	public static void hideSoftwareKeyboard(View focusView)
	{
		if (focusView == null)
			return;
		
		InputMethodManager inputManager = (InputMethodManager) focusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	    inputManager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
