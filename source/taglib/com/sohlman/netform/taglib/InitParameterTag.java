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
	
	public void setName(String aS_Method)
	{
		iS_Method = aS_Method;
	}
	
	public void setValue(String aS_Value)
	{
		iS_Value = aS_Value;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{		
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