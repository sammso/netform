package com.sohlman.netform.component;

import java.lang.reflect.Method;

import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.Validate;

/**
 * @author Sampsa Sohlman
 */
public class ReflectionValidate implements ComponentValidator
{
	private Component[] i_Component_Related = null; 
	private Class i_Class = null;
	private Method i_Method_Validate = null;
	
	public ReflectionValidate(Class a_Class, String aS_Method)
	{
	}
	
	public void setClass(Class a_Class)
	{
		i_Class = a_Class;
	}
	
	
	
	public void setRelatedComponents(Component[] a_Components)
	{
		i_Component_Related = a_Components;
	}
	
	public void setMethod(String aS_MethodName)
	{
		
	}
	
	/**
	 * @see com.sohlman.netform.ComponentValidator#isValid(com.sohlman.netform.Validate)
	 */
	public boolean isValid(Validate validate)
	{
		return false;
	}
}