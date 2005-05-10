package com.sohlman.netform.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.sohlman.netform.ComponentData;
import com.sohlman.netform.ComponentDataException;
import com.sohlman.netform.NetFormException;
import com.sohlman.netform.Utils;
import com.sun.corba.se.internal.javax.rmi.CORBA.Util;

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
	 * @param field
	 */
	public ReflectionData(Object objectToBeReflected, String field)
	{
		setObjectToBeReflected(objectToBeReflected);
		String modFieldName= field.substring(0,1).toUpperCase() + field.substring(1);
		
		assignGetMethod("get" + modFieldName );
		assignSetMethod("set" + modFieldName );
	}
	/**
	 * @param objectToBeReflected
	 */
	public void setObjectToBeReflected(Object objectToBeReflected)
	{
		if(objectToBeReflected==null)
		{
			throw new NullPointerException("null cannot be reflected");
		}
		
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
				return;
			}
		}
		throw new NetFormException("Method " + getMethod + " not found form " + reflectionClass.getName());
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
	public void setData(Object a_Object) throws ComponentDataException
	{
		try 
		{
			this.setMethod.invoke(this.object, new Object[] { a_Object });
		}
		catch(IllegalAccessException l_IllegalAccessException)
		{
			StringBuffer l_StringBuffer = new StringBuffer();
			l_StringBuffer.append("IllegalAccessException on ");
			l_StringBuffer.append(Utils.getMethodNameWithClassName(this.getMethod));			
			throw new NetFormException(l_StringBuffer.toString(),l_IllegalAccessException);
		}
		catch(InvocationTargetException l_InvocationTargetException)
		{
			StringBuffer l_StringBuffer = new StringBuffer();
			l_StringBuffer.append("InvocationTargetException for ");
			l_StringBuffer.append(Utils.getMethodNameWithClassName(this.getMethod));						
			throw new ComponentDataException(l_StringBuffer.toString(),l_InvocationTargetException.getCause());
		}
	}

	/**
	 * @see com.sohlman.netform.ComponentData#getData()
	 */
	public Object getData()
	{
		try 
		{
			return this.getMethod.invoke(this.object, null);
		}
		catch(Exception l_Exception)
		{
			StringBuffer l_StringBuffer = new StringBuffer();
			l_StringBuffer.append("Exception on ");
			l_StringBuffer.append(Utils.getMethodNameWithClassName(this.getMethod));
			l_StringBuffer.append(Utils.getMethodName(this.getMethod));
			
			throw new NetFormException(l_StringBuffer.toString(), l_Exception);
		}
	}

}