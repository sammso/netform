/**
 * Textfield
 *
 * @version 2001-10-01
 * @author Sampsa Sohlman
 */

package com.sohlman.netform;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class TextField extends TableComponent
{
	protected String iS_Text = null;
	protected String iS_NewText = null;
	protected String iS_OldText = null;

	private boolean ib_emptyIsNull = false;
	private boolean ib_isTrim = false;

	protected Vector iV_Listeners;

	/** Creates new TextComponent */

	public TextField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public TextField(Form a_Form)
	{
		super(a_Form);
	}

	/**
	 * Set this on if you want that empty "" String is null<br>
	 * If String contains spaces then it not empty.<br>
	 * if not null then null is ""
	 *
	 * @param boolean true if empty allowed to be null false if not
	 */
	public void setEmptyIsNull(boolean ab_emptyIsNull)
	{
		ib_emptyIsNull = ab_emptyIsNull;
	}

	/**
	 * Set trim mode on for textfield.
	 * If trim mode is on then Space are trimmed of from data automaticly
	 * @param ab_isTrimmed boolean which tells if feature is on or off
	 */
	public void setTrim(boolean ab_isTrim)
	{
		ib_isTrim = ab_isTrim;
	}

	public boolean isTrim()
	{
		return ib_isTrim;
	}

	/**
	 * Tells if empty textfield is handled as null
	 * @return boolean true if can be null else false
	 *
	 */

	public boolean emptyIsNull()
	{
		return ib_emptyIsNull;
	}

	/** 
	 * Set text if emptyIsNull on then "" String is handled like empty
	 *
	 *
	 *
	 */
	public void setText(String aS_Text)
	{
		if (aS_Text != null && ib_isTrim)
		{
			aS_Text = aS_Text.trim();
		}

		if (ib_emptyIsNull && aS_Text != null)
		{
			if (aS_Text.equals(""))
			{
				iS_Text = null;
			}
		}
		if (aS_Text == null && !ib_emptyIsNull)
		{
			aS_Text = "";
		}
		iS_Text = aS_Text;
		validate();
	}

	public String getText()
	{
		if(iS_Text==null)
		{
			return "";	
		}
		else
		{
			return iS_Text;
		}
	}

	public boolean checkIfNewValues()
	{
		HttpServletRequest l_HttpServletRequest = getHttpServletRequest();
		iS_NewText = null;
		iS_OldText = iS_Text;
		String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName());

		if (lS_Parameters != null && lS_Parameters.length > 0)
		{
			// this is made because 
			// XSLT processor don't convert 10 at all only 13
			char[] lc_10 = { 10 };
			iS_NewText = Statics.replace(lS_Parameters[0], new String(lc_10), "");
			if (!iS_NewText.equals(iS_Text))
			{
				setText(iS_NewText);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	public TableComponent newInstance()
	{
		TextField l_Textfield = new TextField(getParent());
		l_Textfield.setEmptyIsNull(ib_emptyIsNull);
		l_Textfield.setVisible(isVisible());
		l_Textfield.setEnabled(isEnabled());
		l_Textfield.setComponentValidator(getComponentValidator());
		return l_Textfield;
	}

	public Object getValue()
	{
		return iS_Text;
	}

	public void setObject(Object a_Object)
	{
		setText((String) a_Object);
	}
	/* (non-Javadoc)
	 * @see com.sohlman.netform.Component#addComponent(java.lang.String, com.sohlman.netform.Component)
	 */
	protected void addComponent(Component a_Component)
	{
		throw new NoSuchMethodError("Child components are not supported on Textfield");
	}

}
