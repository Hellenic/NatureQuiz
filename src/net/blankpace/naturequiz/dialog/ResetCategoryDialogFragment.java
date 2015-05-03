package net.blankpace.naturequiz.dialog;

import net.blankpace.naturequiz.R;
import net.blankpace.naturequiz.manager.ProgressManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class ResetCategoryDialogFragment extends DialogFragment
{
	private ProgressManager progressManager;
	private String categoryName;
	
	public ResetCategoryDialogFragment()
	{
		
	}
	
    public ResetCategoryDialogFragment(ProgressManager progressManager, String categoryName)
    {
    	this.progressManager = progressManager;
    	this.categoryName = categoryName;
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(categoryName);
        builder.setMessage(R.string.quiz_dialog_reset_category);
        
        builder.setPositiveButton(R.string.quiz_dialog_yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       progressManager.resetCategory(categoryName);
                       dismiss();
                       getActivity().recreate();
                   }
               });
        
        builder.setNegativeButton(R.string.quiz_dialog_no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   Log.v("Dialog", "Cancel clicked");
                   }
               });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	
}