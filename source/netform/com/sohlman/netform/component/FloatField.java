package com.sohlman.netform.component;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * FloatField for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2004-02-15
 */
public class FloatField extends TextField
{
	protected Float i_Float = null;
	protected DecimalFormat i_DecimalFormat = null;

	public FloatField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public FloatField(Form a_Form)
	{
		super(a_Form);
	}

	public void setFormat(String aS_Format)
	{
		i_DecimalFormat = new DecimalFormat(aS_Format);
	}

	/**
	 * @see #setFloat(Long)
	 */
	public boolean setFloat(float a_float)
	{
		return setFloat(new Float(a_float));
	}

	/**
	 * Set long value, this causes also value validation
	 * 
	 * @param a_Long
	 */
	public boolean setFloat(Float a_Float)
	{
		validate(new FloatFieldValidate(this, a_Float));

		if (isValid())
		{
			i_Float = a_Float;
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
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * @see TextField#getText()
	 */
	public void setText(String aS_Text)
	{
		iS_Text = formatStringByRules(aS_Text); 
		Float l_Float = null;
		if(aS_Text!=null)
		{
			try
			{
				l_Float = new Float(iS_Text);
				validate(new FloatFieldValidate(this, l_Float));
			}
			catch(NumberFormatException l_NumberFormatException)
			{
				setValid(false);
			}
		}
		else
		{
			validate(new FloatFieldValidate(this, l_Float));
		}

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
	/**
	 * @see TextField#getText()
	 */
	public String getText()
	{
		if (hasComponentData() && isValid())
		{
			i_Float = (Float) getData();
			if (i_Float == null)
			{
				return "";
			}
			else
			{
				if (i_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					i_DecimalFormat.format(i_Float.floatValue(), l_StringBuffer, new FieldPosition(0));

					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return String.valueOf(i_Float);
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
	
	/**
	 * @see com.sohlman.netform.Component#cloneComponent()
	 */
	public Component cloneComponent()
	{
		FloatField l_FloatField = new FloatField(getParent());
		l_FloatField.setVisible(isVisible());
		l_FloatField.setEnabled(isEnabled());
		l_FloatField.setNullIsAllowed(isNullAllowed());
		l_FloatField.setEmptyIsNull(isEmptyNull());
		l_FloatField.setTrim(isTrim());
		l_FloatField.setComponentValidator(getComponentValidator());
		
		return l_FloatField;
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{
			//setFloat((Float)getData());
		}
	}
}
