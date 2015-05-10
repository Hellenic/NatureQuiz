package net.blankpace.naturequiz.validator;

import net.blankpace.naturequiz.model.AnswerStatus;
import net.blankpace.naturequiz.model.Level;

/**
 * Level answer validator that performs case-insensitive check for the answer.
 * @author hellenic
 *
 */
public class LooseAnswerValidator implements AnswerValidator {

	@Override
	public AnswerStatus validate(Level level, String input)
	{
		if (input == null || input.isEmpty())
			return AnswerStatus.INCORRECT_ANSWER;
		
		String userInput = input.trim();
			
		// Check the level's correct answers against user's input
		for (String possibleAnswer : level.getAnswers())
		{
			if (userInput.equalsIgnoreCase(possibleAnswer))
			{
				return AnswerStatus.CORRECT_ANSWER;
			}
		}
		
		// Check the level's other possible answers against user's input
		for (String synonymAnswer : level.getSynonyms())
		{
			if (userInput.equalsIgnoreCase(synonymAnswer))
			{
				return AnswerStatus.CORRECT_SYNONYM; 
			}
		}
		
		return AnswerStatus.INCORRECT_ANSWER;
	}

}
