package com.sohlman.netform.component;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * Numberfield for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2004-01-15
 */
public class IntegerField extends TextField
{
	protected Integer i_Integer = null;
	protected DecimalFormat i_DecimalFormat = null;

	public IntegerField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public IntegerField(Form a_Form)
	{
		super(a_Form);
	}

	public void setFormat(String aS_Format)
	{
		i_DecimalFormat = new DecimalFormat(aS_Format);
	}

	/**
	 * @see #setInteger(Integer)
	 */
	public boolean setInt(int a_int)
	{
		return setInteger(new Integer(a_int));
	}

	/** 
	 * @return Integer value null if contains null
	 */
	public Integer getInteger()
	{
		return i_Integer;
	}
	
	/**
	 * @return int value if null then 0
	 */
	public int getInt()
	{
		if(i_Integer==null)
		{
			return 0; 
		}
		else
		{
			return i_Integer.intValue();
		}
	}

	/**
	 * Set int value, this causes also value validation
	 * 
	 * @param a_Integer
	 * @return true if set is success, false if not (propably validation failed)
	 */
	public boolean setInteger(Integer a_Integer)
	{
		validate(new IntegerFieldValidate(this, a_Integer));

		if (isValid())
		{
			i_Integer = a_Integer;
			if (i_DecimalFormat != null)
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				i_DecimalFormat.format(a_Integer.intValue(), l_StringBuffer, new FieldPosition(0));

				iS_Text = l_StringBuffer.toString();
			}
			else
			{
				iS_Text = String.valueOf(a_Integer);
			}
			if (hasComponentData())
			{
				setData(a_Integer);
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
		i_Integer = null;
		if(aS_Text!=null)
		{
			try
			{
				i_Integer = new Integer(iS_Text);
				validate(new IntegerFieldValidate(this, i_Integer));
			}
			catch(NumberFormatException l_NumberFormatException)
			{
				setValid(false);
			}
		}
		else
		{
			validate(new IntegerFieldValidate(this, i_Integer));
		}

		if (hasComponentData() && isValid())
		{
			if (iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(i_Integer); 
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
			i_Integer = (Integer) getData();
			if (i_Integer == null)
			{
				return "";
			}
			else
			{
				if (i_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					i_DecimalFormat.format(i_Integer.intValue(), l_StringBuffer, new FieldPosition(0));

					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return String.valueOf(i_Integer);
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
		IntegerField l_IntegerField = new IntegerField(getParent());
		l_IntegerField.setVisible(isVisible());
		l_IntegerField.setEnabled(isEnabled());
		l_IntegerField.setNullIsAllowed(isNullAllowed());
		l_IntegerField.setEmptyIsNull(isEmptyNull());
		l_IntegerField.setTrim(isTrim());
		l_IntegerField.setComponentValidator(getComponentValidator());
		
		return l_IntegerField;
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{
			//setInteger((Integer)getData());
		}
	}
}
