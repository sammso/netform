package com.sohlman.netform.component;

import com.sohlman.netform.*;
import com.sohlman.netform.Component;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-02-23
 */
public class TextFieldValidate extends Validate
{
	private String iS_Text;
	
	public TextFieldValidate(Component a_Component, String aS_Text)
	{
		i_Component = a_Component;
		iS_Text = aS_Text;
	}
	
	public String getText()
	{
		return iS_Text;
	}
}
