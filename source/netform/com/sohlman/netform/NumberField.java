package com.sohlman.netform;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;

/**
 * Numberfield for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2002-03-05
 */
public class NumberField extends TextField
{
	boolean ib_emptyIsValid = true;
	boolean ib_nullAllowed = true;
	String iS_Format;
	DecimalFormat i_DecimalFormat;

	ComponentValidator i_ComponentValidator = new ComponentValidator()
	{
		public boolean isValid(Component a_Component)
		{
			String lS_Text = ((NumberField) a_Component).getText();

			if (lS_Text.equals(""))
			{
				lS_Text = null;
			}

			if (lS_Text == null && ib_emptyIsValid)
			{
				return true;
			}

			if (getNumber(lS_Text) != null)
			{
				return true;
			}
			return false;
		}
	};

	public NumberField(Component a_Component_Parent, Class a_Class)
	{
		super(a_Component_Parent);
		i_DecimalFormat = new DecimalFormat();
		if(a_Class==null)
		{
			throw new NullPointerException("Class argument is not allowed to be null");
		}
		
		if (!(a_Class == Integer.class || a_Class == Short.class || a_Class == Long.class || a_Class == Float.class || a_Class == Double.class))
		{
			throw new IllegalStateException(a_Class.getName() + " is not supported Number class");
		}
		setComponentValidator(i_ComponentValidator);
	}

	public NumberField(Form a_Form, Class a_Class)
	{
		super(a_Form);
		i_DecimalFormat = new DecimalFormat();
		if(a_Class==null)
		{
			throw new NullPointerException("Class argument is not allowed to be null");
		}
		
		if (!(a_Class == Integer.class || a_Class == Short.class || a_Class == Long.class || a_Class == Float.class || a_Class == Double.class))
		{
			throw new IllegalStateException(a_Class.getName() + " is not supported Number class");
		}
		
		setComponentValidator(i_ComponentValidator);		
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

	private Number getNumber(String a_String)
	{
		return i_DecimalFormat.parse(a_String, new ParsePosition(0));
	}

	public int getInt()
	{
		return getNumber(getText()).intValue();
	}

	public double getDouble()
	{
		if(hasComponentData())
		{
			Double l_Double = (Double)getData();
			return l_Double.doubleValue();
		}
		return getNumber(getText()).doubleValue();
	}

	public void setLong(long al_long)
	{
		StringBuffer l_StringBuffer = new StringBuffer();
		i_DecimalFormat.format(al_long, l_StringBuffer, new FieldPosition(0));
		setText(l_StringBuffer.toString());
	}

	public void setText()
	{
		
	}

	public void setDouble(double ad_double)
	{
		if(hasComponentData())
		{
			setData(new Double(ad_double));	
		}
		
		StringBuffer l_StringBuffer = new StringBuffer();
		i_DecimalFormat.format(ad_double, l_StringBuffer, new FieldPosition(0));
		setText(l_StringBuffer.toString());
	}

	public void setInt(int ai_int)
	{
		setLong((long)ai_int);
	}

	public void setShort(short as_short)
	{
		setLong((short)as_short);
	}

	public void setFloat(float af_float)
	{
		setDouble((float)af_float);
	}

	private Class i_Class;
	
	public Class getNumberClass()
	{
		return i_Class;
	}

	public Object getValue()
	{
		if (i_Class == null)
		{
			return null;
		}

		Number l_Number = getNumber(getText());
		if (l_Number == null)
		{
			return null;
		}

		if (i_Class == Integer.class)
		{
			return new Integer(l_Number.intValue());
		}
		if (i_Class == Short.class)
		{
			return new Short(l_Number.shortValue());
		}
		if (i_Class == Long.class)
		{
			return new Long(l_Number.longValue());
		}
		if (i_Class == Float.class)
		{
			return new Float(l_Number.floatValue());
		}
		if (i_Class == Double.class)
		{
			return new Double(l_Number.doubleValue());
		}

		return null;
	}

	public void setObject(Object a_Object)
	{
		if(a_Object==null)
		{
			setText(null);
		}
		
		else if (a_Object.getClass() == Integer.class || a_Object.getClass() == Short.class || a_Object.getClass() == Long.class)
		{
			setLong(((Number)a_Object).longValue());
		}
		else if (a_Object.getClass() == Float.class || a_Object.getClass() == Double.class)
		{
			setDouble(((Number)a_Object).longValue());
		}
		else
		{
			throw new ClassCastException(a_Object.getClass() + " is not supported");
		}
				
	}

	public Component cloneComponent()
	{
		NumberField l_NumberField = new NumberField(getParent(), getNumberClass());
		l_NumberField.setEmptyIsNull(emptyIsNull());
		l_NumberField.setVisible(isVisible());
		l_NumberField.setEnabled(isEnabled());
		l_NumberField.setNullIsAllowed(isNullAllowed());
		return l_NumberField;
	}
}
