package com.sohlman.netform;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import javax.servlet.http.HttpServletRequest;

/**
 * DoubleField for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2004-02-19
 */
public class DoubleField extends Component
{
	private String iS_Text;

	boolean ib_emptyIsValid = true;
	boolean ib_nullAllowed = true;
	String iS_Format;
	DecimalFormat i_DecimalFormat = null;

	private ComponentValidator i_ComponentValidator;

	private ComponentValidator i_ComponentValidator_Number = new ComponentValidator()
	{
		public boolean isValid(Component a_Component)
		{
			String lS_Text = iS_Text;

			if (lS_Text != null && lS_Text.equals(""))
			{
				lS_Text = null;
			}

			if (iS_Text == null && ib_emptyIsValid)
			{
				return true;
			}

			try
			{
				// parse succeed then it is valid
				Double.parseDouble(iS_Text);
				if (i_ComponentValidator != null)
				{
					return i_ComponentValidator.isValid(a_Component);
				}
				else
				{
					return true;
				}
			}
			catch (NumberFormatException l_NumberFormatException)
			{
				return false;
			}
		}
	};

	public DoubleField(Component a_Component_Parent)
	{
		super(a_Component_Parent);

		super.setComponentValidator(i_ComponentValidator_Number);
	}

	public DoubleField(Form a_Form)
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

	public void setDouble(Double a_Double)
	{
		if (i_DecimalFormat == null)
		{
			iS_Text = String.valueOf(a_Double);
		}
		else
		{
			StringBuffer l_StringBuffer = new StringBuffer();
			i_DecimalFormat.format(a_Double.doubleValue(), l_StringBuffer, new FieldPosition(0));
			iS_Text = l_StringBuffer.toString();
		}

		if (hasComponentData())
		{
			setData(a_Double);
		}
	}

	public void setDouble(double a_double)
	{

		if (i_DecimalFormat == null)
		{
			iS_Text = String.valueOf(a_double);
		}
		else
		{
			StringBuffer l_StringBuffer = new StringBuffer();
			i_DecimalFormat.format(a_double, l_StringBuffer, new FieldPosition(0));
			iS_Text = l_StringBuffer.toString();
		}

		if (hasComponentData())
		{
			setData(new Double(a_double));
		}
	}

	private void setText(String aS_Text)
	{
		if (aS_Text.trim().equals(""))
		{
			aS_Text = null;
		}

		iS_Text = aS_Text;
		validate();

		if (hasComponentData() && isValid())
		{
			if (iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(new Double(aS_Text));
			}
		}
	}

	public String getText()
	{
		if (hasComponentData() && isValid())
		{
			Double l_Double = (Double) getData();
			if (l_Double == null)
			{
				return "";
			}
			else
			{
				if (i_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					i_DecimalFormat.format(l_Double.doubleValue(), l_StringBuffer, new FieldPosition(0));
					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return Utils.stringToHTML(String.valueOf(l_Double));
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

	public Double getDouble()
	{
		if (hasComponentData() && isValid())
		{
			Double l_Double = (Double) getData();
			return l_Double;
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
					return new Double(iS_Text);
				}
				catch (NumberFormatException l_NumberFormatException)
				{
					return null;
				}

			}
		}
	}

	public float getDoubleValue()
	{
		Double l_Double = getDouble();

		if (l_Double == null)
		{
			return 0;
		}
		else
		{
			return l_Double.floatValue();
		}
	}

	public boolean emptyIsValid()
	{
		return ib_emptyIsValid;
	}

	public Component cloneComponent()
	{
		DoubleField l_DoubleField = new DoubleField(getParent());
		l_DoubleField.setVisible(isVisible());
		l_DoubleField.setEnabled(isEnabled());
		l_DoubleField.setNullIsAllowed(isNullAllowed());
		return l_DoubleField;
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
