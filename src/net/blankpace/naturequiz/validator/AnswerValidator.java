package net.blankpace.naturequiz.validator;

import net.blankpace.naturequiz.model.AnswerStatus;
import net.blankpace.naturequiz.model.Level;

public interface AnswerValidator {
	public AnswerStatus validate(Level level, String input);
}
