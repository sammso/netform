package com.sohlman.netform.component;

import java.lang.reflect.Method;

import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentData;
import com.sohlman.netform.ComponentDataException;
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

	private String iS_Method = null;

	/**
	 * If this constructor is used then validation is using ReflectionData. Then
	 * POJO's setmethod has to send exception.
	 */
	public ReflectionValidate()
	{

	}

	/**
	 * To use static method of class to do reflection.
	 * <p>
	 * It is false if method is is returning (boolean) false or throwing
	 * exception
	 * 
	 * @param a_Class
	 * @param aS_Method
	 */
	public ReflectionValidate(Class a_Class, String aS_Method)
	{

	}

	/**
	 * To use ReflectionData, but different method for validation
	 * <p>
	 * It is false if method is is returning (boolean) false or throwing
	 * exception
	 * 
	 * @param aS_Method
	 */
	public ReflectionValidate(String aS_Method)
	{

	}

	public void setRelatedComponents(Component[] a_Components)
	{
		i_Component_Related = a_Components;
	}

	/**
	 * @see com.sohlman.netform.ComponentValidator#isValid(com.sohlman.netform.Validate)
	 */
	public boolean isValid(Validate a_Validate)
	{
		Component l_Component = a_Validate.getSource();
		ComponentData l_ComponentData = l_Component.getComponentData();

		if(i_Class == null && iS_Method == null && l_ComponentData != null)
		{
			try
			{
				l_ComponentData.setData(a_Validate.getObject());
				return true;
			}
			catch (ComponentDataException l_ComponentDataException)
			{
				return false;
			}
		}
		else
		{
			return false;
		}

	}
}