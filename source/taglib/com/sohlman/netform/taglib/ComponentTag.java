package com.sohlman.netform.taglib;

import java.lang.reflect.Field;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;

/**
 * @author Sampsa Sohlman
 */
public abstract class ComponentTag extends MasterTag
{
	private Component i_Component = null;
	private Form i_Form = null;
	private String iS_Component;
	
	public void setComponent(String aS_Component)
	{
		iS_Component = aS_Component;
	}
	

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public abstract int doStartTag() throws JspException;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public abstract int doEndTag() throws JspException;	
	
	protected boolean init()
	{
		Tag l_Tag = getParent();
		Object l_Object = null;
		
		if(ComponentTag.class.isInstance(l_Tag)  )
		{
			// Parent is component
			ComponentTag l_ComponentTag = (ComponentTag)l_Tag;
			
			l_Object = l_ComponentTag.getComponentFormThisTag();
		}
		else
		{
			i_Form = (Form)i_PageContext.getAttribute(MasterTag.FORM);
			
			if(i_Form==null)
			{
				System.out.println("Error : <init> tag has to be executed before");
				return false;
			}
			
			l_Object = i_Form;
		}
		
		
		
		Field l_Field = getDeclaredField(l_Object.getClass(), iS_Component);
		
		if(l_Field!=null)
		{
			try
			{
				i_Component = (Component)l_Field.get(i_Form);
			}
			catch(IllegalAccessException l_IllegalAccessException)
			{
				return false;
			}
		}
		return true;
	}
	
	private Field getDeclaredField(Class a_Class, String aS_Component)
	{
		try
		{
			Field l_Field = a_Class.getDeclaredField(iS_Component);
			
			return l_Field;
		}
		catch(NoSuchFieldException l_NoSuchFieldException)
		{
			System.out.println(aS_Component + " not found");
			return null;
		}
	}
	
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		i_Component = null;
		iS_Component = null;
		i_Form = null;
	}
	
	final Component getComponentFormThisTag()
	{
		return i_Component;
	}
}
