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
public class IntegerField extends Component
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
			Integer l_Integer = ((IntegerFieldValidate) a_Validate).getInteger();
			if (i_ComponentValidator == null)
			{
				if (l_Integer != null || ib_nullAllowed)
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
				if (l_Integer != null || ib_nullAllowed)
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

	public IntegerField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		i_DecimalFormat = new DecimalFormat();

		super.setComponentValidator(i_ComponentValidator_Number);
	}

	public IntegerField(Form a_Form)
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

	public void setInteger(Integer a_Integer)
	{
		validate(new IntegerFieldValidate(this, a_Integer));

		if (isValid())
		{
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
		}
	}

	public void setInt(int ai_int)
	{	
		setInteger(new Integer(ai_int));		
	}

	private void setText(String aS_Text)
	{
		if(aS_Text!=null && aS_Text.trim().equals(""))
		{
			aS_Text = null;
		}
		
		iS_Text = aS_Text;
		Integer l_Integer = stringToInteger(aS_Text);
		
		validate(new IntegerFieldValidate(this, l_Integer));

		if (hasComponentData() && isValid())
		{
			if (iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(l_Integer);
			}
		}			
	}
	
	private Integer stringToInteger(String a_String)
	{
		try
		{
			return new Integer(a_String);
		}
		catch (NumberFormatException l_NumberFormatException)
		{
			return null;
		}
	}	
	
	public String getText()
	{
		if(hasComponentData() && isValid())
		{
			Integer l_Integer = (Integer)getData();
			if(l_Integer==null)
			{
				return "";
			}
			else
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				i_DecimalFormat.format(l_Integer.intValue(), l_StringBuffer, new FieldPosition(0));
		
				return  Utils.stringToHTML(l_StringBuffer.toString());				
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
				return Utils.stringToHTML(iS_Text);
			}
		}
	}
	
	public Integer getInteger()
	{
		if(hasComponentData() && isValid())
		{
			Integer l_Integer = (Integer)getData();
			return l_Integer;
		}
		else
		{
			if(iS_Text==null)
			{
				return null;
			}
			else
			{
				try
				{
					return new Integer(iS_Text);
				}
				catch(NumberFormatException l_NumberFormatException)
				{
					return null;
				}
				
			}
		}
	}
	
	public int getInt()
	{
		Integer l_Integer = getInteger();
		
		if(l_Integer==null)
		{
			return 0; 
		}
		else
		{
			return l_Integer.intValue();
		}
	}
	
	public boolean emptyIsValid()
	{
		return ib_emptyIsValid;
	}

	public Component cloneComponent()
	{
		IntegerField l_IntegerField = new IntegerField(getParent());
		l_IntegerField.setVisible(isVisible());
		l_IntegerField.setEnabled(isEnabled());
		l_IntegerField.setNullIsAllowed(isNullAllowed());
		return l_IntegerField;
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
		if(hasComponentData())
		{
			// To be implemented  
		}
	}	
}
