package com.sohlman.netform.component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.Validate;

/**
 * Validator, which is usign regExp to validate and future other methods to validate String
 * 
 * @author Sampsa Sohlman
 * 
 * @version 2004-04-13
 */
public class TextFieldValidator implements ComponentValidator
{
	private Pattern i_Pattern = null;
	
	public TextFieldValidator(String aS_RegExp)
	{
		i_Pattern = Pattern.compile(aS_RegExp);
	}
	
	public TextFieldValidator(Pattern a_Pattern)
	{
		i_Pattern = a_Pattern;
	}
	
	/**
	 * @see com.sohlman.netform.ComponentValidator#isValid(com.sohlman.netform.Validate)
	 */
	public boolean isValid(Validate a_Validate)
	{
		try
		{
			TextFieldValidate l_TextFieldValidate = (TextFieldValidate)a_Validate;
			String l_String = l_TextFieldValidate.getText();
			Matcher l_Matcher = i_Pattern.matcher(l_String);
			return l_Matcher.matches();
		}
		catch(ClassCastException l_ClassCastException)
		{
			throw new ClassCastException(a_Validate.getClass().getName() + " is not TextFieldValidate and cannot be used with TextFieldValidator");
		}	
	}
}
