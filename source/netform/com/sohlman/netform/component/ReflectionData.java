package com.sohlman.netform.component;

import java.lang.reflect.Method;

import com.sohlman.netform.ComponentData;
import com.sohlman.netform.NetFormException;

/**
 * @author Sampsa Sohlman
 */
public class ReflectionData extends Object implements ComponentData
{
	private Class reflectionClass = null;
	private Method getMethod = null;
	private Method setMethod = null;
	private Object object = null;

	/**
	 * @see com.sohlman.netform.ComponentData#setData(java.lang.Object)
	 */
	public ReflectionData()
	{
		
	}
	
	/**
	 * @param objectToBeReflected
	 * @param getMethod
	 * @param setMethod
	 */
	public ReflectionData(Object objectToBeReflected, String getMethod, String setMethod)
	{
		setObjectToBeReflected(objectToBeReflected);
		assignGetMethod(getMethod);
		assignSetMethod(setMethod);
	}

	/**
	 * @param objectToBeReflected
	 */
	public void setObjectToBeReflected(Object objectToBeReflected)
	{
		this.object = objectToBeReflected;
		this.reflectionClass = objectToBeReflected.getClass();
	}
	
	/**
	 * @param getMethod
	 */
	public void assignGetMethod(String getMethod)
	{
		if(reflectionClass == null)
		{
			throw new IllegalStateException(
					"No class set. Before using setMethods(int,String, String) use setListItemClass(Class) and setColummnCount(int) ");
		}

		Method[] methods = reflectionClass.getMethods();

		for (int index = 0; index < methods.length; index++)
		{
			if(methods[index].getName().equals(getMethod))
			{
				this.getMethod = methods[index];
			}
		}
	}

	/**
	 * @param setMethod
	 */
	public void assignSetMethod(String setMethod)
	{
		if(reflectionClass == null)
		{
			throw new IllegalStateException(
					"No class set. Before using setMethods(int,String, String) use setListItemClass(Class) and setColummnCount(int) ");
		}

		Method[] methods = reflectionClass.getMethods();

		for (int index = 0; index < methods.length; index++)
		{
			if(methods[index].getName().equals(setMethod))
			{
				this.setMethod = methods[index];
			}
		}
	}

	
	
	/**
	 * @see com.sohlman.netform.ComponentData#setData(java.lang.Object)
	 */
	public void setData(Object a_Object)
	{
		try 
		{
			this.setMethod.invoke(this.object, new Object[] { a_Object });
		}
		catch(Exception l_Exception)
		{
			throw new NetFormException("Reflection Exception (Set)", l_Exception);
		}

	}

	/**
	 * @see com.sohlman.netform.ComponentData#getData()
	 */
	public Object getData()
	{
		try 
		{
			return this.setMethod.invoke(this.object, null);
		}
		catch(Exception l_Exception)
		{
			throw new NetFormException("Reflection Exception (Get)", l_Exception);
		}
	}

}