/*
NetForm Library
---------------
Copyright (C) 2001-2005 - Sampsa Sohlman, Teemu Sohlman

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
*/
package com.sohlman.netform.taglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.sohlman.netform.Form;
import com.sohlman.netform.NetFormException;

/**
 * @author Sampsa Sohlman
 */
public class InitParameterTag extends MasterTag
{
	private Form i_Form = null;

	private String iS_Method = null;
	private String iS_Value = null;
	/**
	 * This is for Tag inteface
	 * 
	 * @param aS_Method Field name 
	 */	
	public void setName(String aS_Method)
	{
		iS_Method = aS_Method;
	}
	
	/**
	 * This is for Tag inteface
	 * 
	 * @param aS_Value
	 */
	public void setValue(String aS_Value)
	{
		iS_Value = aS_Value;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{		
		InitTag i_InitTag = (InitTag)i_Tag;
		Form l_Form = i_InitTag.getCurrentForm();
		try
		{
			Method l_Method = l_Form.getClass().getMethod("set" + iS_Method, new Class[]{String.class});
			l_Method.invoke(l_Form, new Object[] {iS_Value});
		}
		catch(NoSuchMethodException l_NoSuchMethodException)
		{
			throw new JspException("Failed to look set" + iS_Method + "() method from form", l_NoSuchMethodException);
		}
		catch(InvocationTargetException l_InvocationTargetException)
		{
			throw new JspException("Failed to invoke set" + iS_Method + "() method from form", l_InvocationTargetException);
		}
		catch(IllegalAccessException l_IllegalAccessException)
		{
			throw new JspException("Failed to access set" + iS_Method + "() method from form", l_IllegalAccessException);
		}
		return EVAL_PAGE;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		return SKIP_BODY;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		iS_Method = null;
		iS_Value = null;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#setParent(javax.servlet.jsp.tagext.Tag)
	 */
	public void setParent(Tag a_Tag)
	{
		if(a_Tag instanceof InitTag)
		{
			i_Tag = a_Tag;
			//i_Form = ((InitTag)a_Tag).getForm();
		}
		else
		{
			throw new NetFormException("InitParameter tag can be only under InitTag");
		}
	}
}