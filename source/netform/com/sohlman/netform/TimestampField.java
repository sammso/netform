package com.sohlman.netform;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * TimestampField
 *
  @version 2002-03-05
  @author Sampsa Sohlman
 */

public class TimestampField extends TextField
{
	String iS_DateTimeFormat;
	Timestamp i_Timestamp;
	boolean ib_emptyIsValid = true;
	boolean ib_nullAllowed = true;

	ComponentValidator i_ComponentValidator = new ComponentValidator()
	{
		public boolean isValid(Component a_Component)
		{
			if (getText() == null && ib_nullAllowed)
			{
				return true;
			}
			else if (Statics.isTimestamp(getText(), iS_DateTimeFormat))
			{
				return true;
			}
			return false;
		}
	};
	
	public void setFormat(String aS_Format)
	{
		iS_DateTimeFormat = aS_Format;
	}

	public TimestampField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		setComponentValidator(i_ComponentValidator);
	}

	public TimestampField(Form a_Form)
	{
		super(a_Form);
		setComponentValidator(i_ComponentValidator);
	}

	public void setTimestamp(Timestamp a_Timestamp)
	{
		i_Timestamp = a_Timestamp;
		if (iS_DateTimeFormat != null)
		{
			setText(Statics.timestampToString(a_Timestamp, iS_DateTimeFormat));
		}
	}

	private Timestamp getTimestamp(String aS_Timestamp)
	{
		if (Statics.isTimestamp(aS_Timestamp, iS_DateTimeFormat))
		{
			return Statics.getTimestamp(aS_Timestamp, iS_DateTimeFormat);
		}
		else
		{
			return null;
		}
	}

	public Timestamp getTimestamp()
	{
		return getTimestamp(getText());
	}

	public Time getTime()
	{
		Timestamp l_Timestamp = getTimestamp();
		if(l_Timestamp!=null)
		{
			return new Time(l_Timestamp.getTime());
		}
		else
		{
			return null;
		}
	}
	
	public Date getDate()
	{
		Timestamp l_Timestamp = getTimestamp();
		if(l_Timestamp!=null)
		{
			return new Date(l_Timestamp.getTime());
		}
		else
		{
			return null;
		}
	}

	public Object getObject()
	{
		return (Object) getTimestamp();
	}

	public void setObject(Object a_Object)
	{
		setTimestamp((Timestamp) a_Object);
	}

	public TableComponent newInstance()
	{
		TimestampField l_TimestampField = new TimestampField(getParent());
		l_TimestampField.setEmptyIsNull(emptyIsNull());
		l_TimestampField.setVisible(isVisible());
		l_TimestampField.setEnabled(isEnabled());
		//l_TimestampField.setNullIsAllowed(isNullAllowed());
		return l_TimestampField;
	}
}
