package com.sohlman.netform.component;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * DoubleField for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2004-02-19
 */
public class DoubleField extends TextField
{
	protected String iS_Format;
	protected DecimalFormat i_DecimalFormat = null;

	public DoubleField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public DoubleField(Form a_Form)
	{
		super(a_Form);
	}

	public void setFormat(String aS_Format)
	{
		i_DecimalFormat = new DecimalFormat(aS_Format);
	}

	/**
	 * @see #setLong(Long)
	 */
	public void setDouble(double a_double)
	{
		setDouble(new Double(a_double));
	}

	/**
	 * Set long value, this causes also value validation
	 * 
	 * @param a_Long
	 */
	public void setDouble(Double a_Double)
	{
		validate(new DoubleFieldValidate(this, a_Double));

		if (isValid())
		{
			if (i_DecimalFormat != null)
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				i_DecimalFormat.format(a_Double.doubleValue(), l_StringBuffer, new FieldPosition(0));

				iS_Text = l_StringBuffer.toString();
			}
			else
			{
				iS_Text = String.valueOf(a_Double);
			}
			if (hasComponentData())
			{
				setData(a_Double);
			}
		}
	}
	/**
	 * @see TextField#getText()
	 */
	public void setText(String aS_Text)
	{
		iS_Text = formatStringByRules(aS_Text); 
		Double l_Double = null;
		if(aS_Text!=null)
		{
			try
			{
				l_Double = new Double(iS_Text);
				validate(new DoubleFieldValidate(this, l_Double));
			}
			catch(NumberFormatException l_NumberFormatException)
			{
				setValid(false);
			}
		}
		else
		{
			validate(new DoubleFieldValidate(this, l_Double));
		}

		if (hasComponentData() && isValid())
		{
			if (iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(l_Double); 
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
			Long l_Long = (Long) getData();
			if (l_Long == null)
			{
				return "";
			}
			else
			{
				if (i_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					i_DecimalFormat.format(l_Long.doubleValue(), l_StringBuffer, new FieldPosition(0));

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
	
	/**
	 * @see com.sohlman.netform.Component#cloneComponent()
	 */
	public Component cloneComponent()
	{
		DoubleField l_DoubleField = new DoubleField(getParent());
		l_DoubleField.setVisible(isVisible());
		l_DoubleField.setEnabled(isEnabled());
		l_DoubleField.setNullIsAllowed(isNullAllowed());
		l_DoubleField.setEmptyIsNull(isEmptyNull());
		l_DoubleField.setTrim(isTrim());
		l_DoubleField.setComponentValidator(getComponentValidator());
		
		return l_DoubleField;
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{
			//setDouble((Double)getData());
		}
	}
}
