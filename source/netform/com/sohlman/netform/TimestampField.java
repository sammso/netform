package com.sohlman.netform;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

/**
 * TimestampField
 *
  @version 2004-01-15
  @author Sampsa Sohlman
 */

public class TimestampField extends Component
{
	private String iS_DateTimeFormat;
	private Timestamp i_Timestamp;
	private String iS_Text = "";

	private boolean ib_emptyIsValid = true;
	private boolean ib_nullAllowed = true;
	private boolean ib_emptyIsNull = true;

	private ComponentValidator i_ComponentValidator;

	private ComponentValidator i_ComponentValidator_Value = new ComponentValidator()
	{
		public boolean isValid(Component a_Component)
		{	
			if (iS_Text == null && ib_nullAllowed)
			{
				return true;
			}
			else if (Utils.isTimestamp(iS_Text, iS_DateTimeFormat))
			{
				if(i_ComponentValidator==null)
				{
					return true;
				}
				else
				{
					return i_ComponentValidator.isValid(a_Component);
				}
				
			}
			return false;
		}
	};

	/**
	 * Set's component validator. Validator is not called if value inside component is not
	 * valid Timestamp
	 * 
	 * @see com.sohlman.netform.Component#setComponentValidator(com.sohlman.netform.ComponentValidator)
	 */
	public void setComponentValidator(ComponentValidator a_ComponentValidator)
	{
		i_ComponentValidator = a_ComponentValidator;	
	}

	public void setFormat(String aS_Format)
	{
		iS_DateTimeFormat = aS_Format;
	}

	public TimestampField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		setComponentValidator(i_ComponentValidator_Value);
	}

	public TimestampField(Form a_Form)
	{
		super(a_Form);
		setComponentValidator(i_ComponentValidator_Value);
	}

	public void setTimestamp(Timestamp a_Timestamp)
	{
		i_Timestamp = a_Timestamp;
		if (iS_DateTimeFormat != null)
		{
			iS_Text = Utils.timestampToString(a_Timestamp, iS_DateTimeFormat);
		}
		if (hasComponentData())
		{
			setData(a_Timestamp);
		}
	}

	private Timestamp stringToTimestamp(String aS_Timestamp)
	{
		if (Utils.isTimestamp(aS_Timestamp, iS_DateTimeFormat))
		{
			return Utils.stringToTimestamp(aS_Timestamp, iS_DateTimeFormat);
		}
		else
		{
			return null;
		}
	}

	public Timestamp getTimestamp()
	{
		if (hasComponentData())
		{
			return (Timestamp) getData();
		}
		else
		{
			return stringToTimestamp(iS_Text);
		}
	}

	public String getText()
	{
		if(hasComponentData() && isValid())
		{
			Timestamp l_Timestamp = (Timestamp)getData();
			
			if(l_Timestamp==null)
			{
				iS_Text = null;
				return "";
			}
			else
			{
				iS_Text = Utils.timestampToString(l_Timestamp, iS_DateTimeFormat);
				return Utils.stringToHTML(iS_Text);
			}			
		}
		else
		{
			if(iS_Text==null)
			{
				return "";
			}
			else
			{
				return Utils.stringToHTML(iS_Text);
			}
		}
	}
	
	private void setText(String aS_Text)
	{
		if(ib_emptyIsNull && aS_Text.trim().equals(""))
		{
			aS_Text = null;
		}
		
		iS_Text = aS_Text;
		validate();
		
		if(hasComponentData() && isValid())
		{
			setData(stringToTimestamp(aS_Text));
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
			String lS_NewText = Utils.replace(lS_Parameters[0], new String(lc_10), "");
			//lS_NewText = Utils.htmlToString(lS_NewText);
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


	public Component cloneComponent()
	{
		TimestampField l_TimestampField = new TimestampField(getParent());
		l_TimestampField.setFormat(iS_DateTimeFormat);
		l_TimestampField.setEmptyIsNull(emptyIsNull());
		l_TimestampField.setVisible(isVisible());
		l_TimestampField.setEnabled(isEnabled());
		l_TimestampField.setNullIsAllowed(isNullAllowed());
		return l_TimestampField;
	}

	public void setNullIsAllowed(boolean ab_nullAllowed)
	{
		ib_nullAllowed = ab_nullAllowed;
	}

	public boolean isNullAllowed()
	{
		return ib_nullAllowed;
	}
	
	public void setEmptyIsNull(boolean ab_value)
	{
		ib_emptyIsNull = ab_value;
	}
	
	public boolean emptyIsNull()
	{
		return ib_emptyIsNull;
	}
	
	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if(hasComponentData())
		{
			
		}
	}	
}
