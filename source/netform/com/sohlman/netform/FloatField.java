package com.sohlman.netform;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import javax.servlet.http.HttpServletRequest;

/**
 * FloatField for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2004-02-15
 */
public class FloatField extends Component
{
	private String iS_Text;

	boolean ib_emptyIsValid = true;
	boolean ib_nullAllowed = true;
	String iS_Format;
	DecimalFormat i_DecimalFormat;

	private ComponentValidator i_ComponentValidator;

	private ComponentValidator i_ComponentValidator_Number = new ComponentValidator()
	{
		public boolean isValid(Validate a_Validate)
		{
			Float l_Float = ((FloatFieldValidate) a_Validate).getFloat();
			if (i_ComponentValidator == null)
			{
				if (l_Float != null || ib_nullAllowed)
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
				if (l_Float != null || ib_nullAllowed)
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

	public FloatField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		i_DecimalFormat = new DecimalFormat();

		super.setComponentValidator(i_ComponentValidator_Number);
	}

	public FloatField(Form a_Form)
	{
		super(a_Form);
		i_DecimalFormat = new DecimalFormat();

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
		setFormat(new DecimalFormat(aS_Format));
	}

	public void setFormat(DecimalFormat a_DecimalFormat)
	{
		i_DecimalFormat = a_DecimalFormat;
	}

	public void setNullIsAllowed(boolean ab_nullAllowed)
	{
		ib_nullAllowed = ab_nullAllowed;
	}

	public boolean isNullAllowed()
	{
		return ib_nullAllowed;
	}

	public void setFloat(Float a_Float)
	{
		validate(new FloatFieldValidate(this, a_Float));
		if (isValid())
		{
			if (i_DecimalFormat != null)
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				i_DecimalFormat.format(a_Float.floatValue(), l_StringBuffer, new FieldPosition(0));

				iS_Text = l_StringBuffer.toString();
			}
			else
			{
				iS_Text = String.valueOf(a_Float);
			}
			if (hasComponentData())
			{
				setData(a_Float);
			}
		}
	}

	public void setFloat(float a_float)
	{
		if (i_DecimalFormat != null)
		{
			StringBuffer l_StringBuffer = new StringBuffer();
			i_DecimalFormat.format(a_float, l_StringBuffer, new FieldPosition(0));

			iS_Text = l_StringBuffer.toString();
		}
		else
		{
			iS_Text = String.valueOf(a_float);
		}
		if (hasComponentData())
		{
			setData(new Float(a_float));
		}
	}

	private void setText(String aS_Text)
	{
		if (aS_Text.trim().equals(""))
		{
			aS_Text = null;
		}

		iS_Text = aS_Text;
		Float l_Float = stringToFloat(aS_Text);
		validate(new FloatFieldValidate(this, l_Float));

		if (hasComponentData() && isValid())
		{
			if (iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(l_Float);
			}
		}
	}

	private Float stringToFloat(String a_String)
	{
		try
		{
			return new Float(a_String);
		}
		catch (NumberFormatException l_NumberFormatException)
		{
			return null;
		}
	}

	public String getText()
	{
		if (hasComponentData() && isValid())
		{
			Float l_Float = (Float) getData();
			if (l_Float == null)
			{
				return "";
			}
			else
			{
				if (i_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					i_DecimalFormat.format(l_Float.intValue(), l_StringBuffer, new FieldPosition(0));
					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return String.valueOf(l_Float);
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

	public Float getFloat()
	{
		if (hasComponentData() && isValid())
		{
			Float l_Float = (Float) getData();
			return l_Float;
		}
		else
		{
			if (iS_Text == null)
			{
				return null;
			}
			else
			{
				try
				{
					return new Float(iS_Text);
				}
				catch (NumberFormatException l_NumberFormatException)
				{
					return null;
				}

			}
		}
	}

	public float getFloatValue()
	{
		Float l_Float = getFloat();

		if (l_Float == null)
		{
			return 0;
		}
		else
		{
			return l_Float.floatValue();
		}
	}

	public boolean emptyIsValid()
	{
		return ib_emptyIsValid;
	}

	public Component cloneComponent()
	{
		FloatField l_FloatField = new FloatField(getParent());
		l_FloatField.setVisible(isVisible());
		l_FloatField.setEnabled(isEnabled());
		l_FloatField.setNullIsAllowed(isNullAllowed());
		return l_FloatField;
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

	}
}
