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
public class LongField extends TextField
{
	protected Long i_Long = null;
	protected DecimalFormat i_DecimalFormat = null;

	public LongField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		ib_isTrim = true;
	}

	public LongField(Form a_Form)
	{
		super(a_Form);
		ib_isTrim = true;
	}

	public void setFormat(String aS_Format)
	{
		i_DecimalFormat = new DecimalFormat(aS_Format);
	}

	/**
	 * @see #setLong(Long)
	 */
	public boolean setLong(long al_long)
	{
		return setLong(new Long(al_long));
	}

	public Long getLong()
	{
		return i_Long;
	}

	public long getlong()
	{
		return i_Long.longValue();
	}



	/**
	 * Set long value, this causes also value validation, if value is not valid it is not set
	 * 
	 * @param a_Long
	 * @return boolean true if value is valid false if not (depends of validation)
	 */
	public boolean setLong(Long a_Long)
	{
		return setLong(a_Long, true);
	}

	protected boolean setLong(Long a_Long, boolean ab_setData)
	{
		if (ib_isNullAllowed == false && a_Long == null)
		{
			setValid(false);
		}
		else
		{
			validate(new LongFieldValidate(this, a_Long));
		}
		if (isValid())
		{
			i_Long = a_Long;
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
			if (hasComponentData() && ab_setData)
			{
				setData(a_Long);
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
	public boolean setText(String aS_Text)
	{
		iS_Text = formatStringByRules(aS_Text);
		i_Long = null;
		if (aS_Text != null)
		{
			try
			{
				i_Long = new Long(iS_Text);
				validate(new LongFieldValidate(this, i_Long));
			}
			catch (NumberFormatException l_NumberFormatException)
			{
				setValid(false);
			}
		}
		else
		{
			validate(new LongFieldValidate(this, i_Long));
		}

		if (hasComponentData() && isValid())
		{
			if (iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(i_Long);
			}
		}
		return isValid();
	}
	/**
	 * @see TextField#getText()
	 */
	public String getText()
	{
		if (hasComponentData() && isValid())
		{
			i_Long = (Long) getData();
			if (i_Long == null)
			{
				return "";
			}
			else
			{
				if (i_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					i_DecimalFormat.format(i_Long.longValue(), l_StringBuffer, new FieldPosition(0));

					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return String.valueOf(i_Long);
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
		LongField l_LongField = new LongField(getParent());
		l_LongField.setVisible(isVisible());
		l_LongField.setEnabled(isEnabled());
		l_LongField.setNullIsAllowed(isNullAllowed());
		l_LongField.setEmptyIsNull(isEmptyNull());
		l_LongField.setTrim(isTrim());
		l_LongField.setComponentValidator(getComponentValidator());

		return l_LongField;
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{
			setLong((Long)getData(), false);
		}
	}
}
