package com.sohlman.webapp.netform;

import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.Validate;
import com.sohlman.netform.component.TextField;
import com.sohlman.netform.component.TextFieldValidate;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-05-06
 */
public class HasToBeValidForm extends MasterForm
{	
	private ComponentValidator i_ComponentValidator = new ComponentValidator()
	{
		/**
		 * @see com.sohlman.netform.ComponentValidator#isValid(com.sohlman.netform.Validate)
		 */
		public boolean isValid(Validate a_Validate)
		{
			if(a_Validate.getSource()==firstTextField || a_Validate.getSource()==secondTextField)
			{
				String lS_Text = ((TextFieldValidate)a_Validate).getText();
				if(lS_Text!=null && lS_Text.length() > 5)
				{
					return true;
				}
			}
			return false;
		}
	};

	public TextField firstTextField = new TextField(this);
	public TextField secondTextField = new TextField(this);

	
	public HasToBeValidForm()
	{
		firstTextField.setComponentValidator(i_ComponentValidator);
		secondTextField.setComponentValidator(i_ComponentValidator);
	}

	/**
	 * @see com.sohlman.netform.Form#init()
	 */
	public void init()
	{
		// No data is set so this is not needed
		// It is not removed, because this is example
	}

	/**
	 * @see com.sohlman.netform.Form#allowFormChange()
	 */
	public boolean allowFormChange()
	{
		// This makes the decision if it is possible to choose other form.
		return isValid();
	}

}
