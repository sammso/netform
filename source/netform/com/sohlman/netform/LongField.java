package com.sohlman.netform;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import javax.servlet.http.HttpServletRequest;

/**
 * Numberfield for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2004-01-15
 */
public class LongField extends Component
{
	private String iS_Text;

	boolean ib_nullAllowed = true;
	String iS_Format;
	DecimalFormat i_DecimalFormat = null;

	private ComponentValidator i_ComponentValidator;

	private ComponentValidator i_ComponentValidator_Number = new ComponentValidator()
	{
		public boolean isValid(Validate a_Validate)
		{
			Long l_Long = ((LongFieldValidate) a_Validate).getLong();
			if (i_ComponentValidator == null)
			{
				if (l_Long != null || ib_nullAllowed)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				if (l_Long != null || ib_nullAllowed)
				{
					return i_ComponentValidator.isValid(a_Validate);
				}
				else
				{
					return false;
				}
			}
		}
	};

	private Long stringToLong(String a_String)
	{
		try
		{
			return new Long(a_String);
		}
		catch (NumberFormatException l_NumberFormatException)
		{
			return null;
		}
	}

	public LongField(Component a_Component_Parent)
	{
		super(a_Component_Parent);

		super.setComponentValidator(i_ComponentValidator_Number);
	}

	public LongField(Form a_Form)
	{
		super(a_Form);

		super.setComponentValidator(i_ComponentValidator);
	}

	/**
	 * @see com.sohlman.netform.Component#setComponentValidator(com.sohlman.netform.ComponentValidator)
	 * 
	 * This component validator is called only when value is valid number
	 */
	public void setComponentValidator(ComponentValidator a_ComponentValidator)
	{
		i_ComponentValidator = a_ComponentValidator;
	}

	public void setFormat(String aS_Format)
	{
		i_DecimalFormat = new DecimalFormat(aS_Format);
	}

	public void setNullIsAllowed(boolean ab_nullAllowed)
	{
		ib_nullAllowed = ab_nullAllowed;
	}

	public boolean isNullAllowed()
	{
		return ib_nullAllowed;
	}

	public void setLong(long al_long)
	{
		setLong(new Long(al_long));
	}

	public void setLong(Long a_Long)
	{
		validate(new LongFieldValidate(this, a_Long));

		if (isValid())
		{
			if (i_DecimalFormat != null)
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				i_DecimalFormat.format(a_Long.longValue(), l_StringBuffer, new FieldPosition(0));

				iS_Text = l_StringBuffer.toString();
			}
			else
			{
				iS_Text = String.valueOf(a_Long);
			}
			if (hasComponentData())
			{
				setData(a_Long);
			}
		}
	}

	private void setText(String aS_Text)
	{
		if (aS_Text.trim().equals(""))
		{
			aS_Text = null;
		}

		iS_Text = aS_Text;
		Long l_Long = stringToLong(aS_Text);
		validate(new LongFieldValidate(this, l_Long));

		if (hasComponentData() && isValid())
		{
			if (iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(l_Long);
			}
		}
	}

	public String getText()
	{
		if (hasComponentData() && isValid())
		{
			Long l_Long = (Long) getData();
			if (l_Long == null)
			{
				return "";
			}
			else
			{
				if (i_DecimalFormat == null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					i_DecimalFormat.format(l_Long.longValue(), l_StringBuffer, new FieldPosition(0));

					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return String.valueOf(l_Long);
				}
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
				return Utils.stringToHTML(iS_Text);
			}
		}
	}

	public void setDouble(double ad_double)
	{
		if (hasComponentData())
		{
			setData(new Double(ad_double));
		}

		StringBuffer l_StringBuffer = new StringBuffer();
		i_DecimalFormat.format(ad_double, l_StringBuffer, new FieldPosition(0));
		setText(l_StringBuffer.toString());
	}


	public Component cloneComponent()
	{
		LongField l_LongField = new LongField(getParent());
		l_LongField.setVisible(isVisible());
		l_LongField.setEnabled(isEnabled());
		l_LongField.setNullIsAllowed(isNullAllowed());
		return l_LongField;
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

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{

		}
	}
}
