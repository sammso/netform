/*
NetForm Library
---------------
Copyright (C) 2001-2004 - Sampsa Sohlman, Teemu Sohlman

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
*/
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
