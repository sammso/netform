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
	
	boolean ib_emptyIsValid = true;
	boolean ib_nullAllowed = true;
	String iS_Format;
	DecimalFormat i_DecimalFormat;

	private ComponentValidator i_ComponentValidator;

	private ComponentValidator i_ComponentValidator_Number = new ComponentValidator()
	{
		public boolean isValid(Component a_Component)
		{
			if (iS_Text.equals(""))
			{
				iS_Text = null;
			}

			if (iS_Text == null && ib_emptyIsValid)
			{
				return true;
			}
			
			try
			{
				// parse succeed then it is valid
				Long.parseLong(iS_Text);
				if(i_ComponentValidator!=null)
				{
					return i_ComponentValidator.isValid(a_Component);
				}
				else
				{
					return true;
				}
			}
			catch(NumberFormatException l_NumberFormatException)
			{
				return false;
			}
		}
	};

	public LongField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		i_DecimalFormat = new DecimalFormat();

		super.setComponentValidator(i_ComponentValidator_Number);
	}

	public LongField(Form a_Form)
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
		StringBuffer l_StringBuffer = new StringBuffer();
		i_DecimalFormat.format(al_long, l_StringBuffer, new FieldPosition(0));
		
		iS_Text = l_StringBuffer.toString();
		
		if(hasComponentData())
		{
			setData(new Long(al_long));
		}
	}

	public void setLong(Long a_Long)
	{
		StringBuffer l_StringBuffer = new StringBuffer();
		i_DecimalFormat.format(a_Long.longValue(), l_StringBuffer, new FieldPosition(0));
		
		iS_Text = l_StringBuffer.toString();
		
		if(hasComponentData())
		{
			setData(a_Long);
		}
	}

	private void setText(String aS_Text)
	{
		if(aS_Text.trim().equals(""))
		{
			aS_Text = null;
		}
		
		iS_Text = aS_Text;
		validate();
		
		if(hasComponentData() && isValid())
		{
			if(iS_Text==null)
			{
				setData(null);
			}
			else
			{
				setData(new Long(aS_Text));
			}
		}		
	}
	
	public String getText()
	{
		if(hasComponentData() && isValid())
		{
			Long l_Long = (Long)getData();
			if(l_Long==null)
			{
				return "";
			}
			else
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				i_DecimalFormat.format(l_Long.longValue(), l_StringBuffer, new FieldPosition(0));
		
				return  Utils.escapeHTML(l_StringBuffer.toString());				
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
				return Utils.escapeHTML(iS_Text);
			}
		}
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

	public boolean emptyIsValid()
	{
		return ib_emptyIsValid;
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
		if(hasComponentData())
		{
			
		}
	}		
}
