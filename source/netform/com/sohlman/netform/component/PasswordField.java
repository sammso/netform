package com.sohlman.netform.component;

import javax.servlet.http.HttpServletRequest;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * @author Sampsa Sohlman
 * 
 * @version 20-04-2004
 */
public class PasswordField extends TextField
{

	/**
	 * @see com.sohlman.netform.Component#Component(Component)
	 */
	public PasswordField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		ib_isEmptyNull=false;
		ib_isNullAllowed=false;
		ib_isTrim=true;
	}

	/**
	 * @see com.sohlman.netform.Component#Component(Form)
	 */
	public PasswordField(Form a_Form)
	{
		super(a_Form);
		ib_isEmptyNull=false;
		ib_isNullAllowed=false;
		ib_isTrim=true;
	}

	/**
	 * Overriden from TextField
	 * @see com.sohlman.netform.Component#checkIfNewValues()
	 */
	public boolean checkIfNewValues()
	{
		clearModifiedStatus();
		HttpServletRequest l_HttpServletRequest = getHttpServletRequest();
		String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName());

		if (lS_Parameters != null && lS_Parameters.length > 0)
		{
			// this is made because 
			// XSLT processor don't convert 10 at all only 13
			char[] lc_10 = { 10 };
			String lS_NewText = Utils.replace(lS_Parameters[0], new String(lc_10), "");
			//lS_NewText = Utils.htmlToString(lS_NewText);
			
			lS_NewText = formatStringByRules(lS_NewText);
			
			if ( ( lS_NewText==null && iS_Text==null ) || ( lS_NewText!=null && lS_NewText.equals(iS_Text)) || lS_NewText.equals(""))
			{
				//System.out.println(iS_NewText +" = " + iS_Text);
				return false;
			}
			else
			{
				setText(lS_NewText);
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
	public String getText()
	{
		return "";
	}
	
	public String getPassword()
	{
		return super.getText();
	}
}