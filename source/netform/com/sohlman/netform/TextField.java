/**
 * Textfield
 *
 * @version 2004-15-01
 * @author Sampsa Sohlman
 */

package com.sohlman.netform;

import javax.servlet.http.HttpServletRequest;

public class TextField extends Component
{
	protected String iS_Text = null;

	private boolean ib_emptyIsNull = false;
	private boolean ib_isTrim = false;

	private boolean ib_imValidating = false;

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
				aS_Text = null;
			}
		}
		if (aS_Text == null && !ib_emptyIsNull)
		{
			aS_Text = "";
		}

		iS_Text = aS_Text;

		ib_imValidating = true;
		validate();
		ib_imValidating = false;

		if (hasComponentData() && isValid())
		{
			setData(iS_Text);
		}
	}

	/**
	 * <b>JSP also</b>  Returns current component text.<br>
	 * Never returns null value
	 * {@link ComponentData ComponentData} is not used
	 * is component is not valid or you are 
	 * calling this method in validation.
	 * 
	 * @return String
	 */
	public String getText()
	{

		if (hasComponentData() && isValid() && !ib_imValidating)
		{
			Object l_Object = getData();
			if (l_Object == null)
			{
				iS_Text = null;
				return "";
			}
			else
			{
				iS_Text = l_Object.toString();
				return iS_Text;
			}
		}
		else
		{
			if (iS_Text == null)
			{
				return "";
			}
			else
			{
				return iS_Text;
			}
		}
	}

	/**
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
			String lS_NewText = Statics.replace(lS_Parameters[0], new String(lc_10), "");
			if (!lS_NewText.equals(iS_Text))
			{
				//System.out.println(iS_NewText +" = " + iS_Text);
				setText(lS_NewText);

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

	/**
	 * @see com.sohlman.netform.Component#addComponent(java.lang.String, com.sohlman.netform.Component)
	 */
	protected void addComponent(Component a_Component)
	{
		throw new NoSuchMethodError("Child components are not supported on TimestampField");
	}

	/**
	 * @see com.sohlman.netform.Component#cloneComponent()
	 */
	public Component cloneComponent()
	{
		TextField l_Textfield = new TextField(getParent());
		l_Textfield.setEmptyIsNull(ib_emptyIsNull);
		l_Textfield.setVisible(isVisible());
		l_Textfield.setEnabled(isEnabled());
		l_Textfield.setComponentValidator(getComponentValidator());
		return l_Textfield;
	}
}
