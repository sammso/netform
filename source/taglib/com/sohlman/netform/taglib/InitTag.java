/*
NetForm Library
---------------
Copyright (C) 2001-2004 - Sampsa Sohlman, Teemu Sohlman

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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import com.sohlman.netform.DoRedirectException;
import com.sohlman.netform.Form;
import com.sohlman.netform.FormManager;

/**
 * Init Tag handles NetForm Initialization.
 * 
 * @author Sampsa Sohlman
 */
public class InitTag extends MasterTag
{
	private Form i_Form = null;
	private String iS_FormClassName = null;
	private String iS_LoginPage = null;

	private boolean ib_pageHasBeenRedirected = false;

	public void setForm(String aS_FormClassName)
	{
		iS_FormClassName = aS_FormClassName;
	}

	public void setLoginPage(String aS_LoginPage)
	{
		iS_LoginPage = aS_LoginPage;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		if(!ib_pageHasBeenRedirected)
		{
			try
			{
				i_Form.execute();
				
				i_PageContext.getRequest().setAttribute(Form.FORM, i_Form);
						
				return EVAL_PAGE;
			}
			catch (DoRedirectException l_DoRedirectException)
			{
				try
				{
					ib_pageHasBeenRedirected = true;
					((HttpServletResponse) i_PageContext.getResponse()).sendRedirect(l_DoRedirectException.getPage());
					return SKIP_PAGE;
				}
				catch (IOException l_IOException)
				{
					throw new JspException("Doing redirect and Exception occurred : ", l_IOException);
				}
			}
		}
		else
		{
			return SKIP_PAGE;
		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		try
		{
			Class l_Class = Class.forName(iS_FormClassName);

			i_Form = FormManager.getForm((HttpServletRequest) i_PageContext.getRequest(),
					(HttpServletResponse) this.i_PageContext.getResponse(), i_PageContext.getServletContext(), l_Class,
					iS_LoginPage);

			if(i_Form.isInitialized())
			{
				return SKIP_BODY;
			}
			else
			{
				return EVAL_BODY_INCLUDE;
			}
		}
		catch (DoRedirectException l_DoRedirectException)
		{
			try
			{
				ib_pageHasBeenRedirected = true;
				((HttpServletResponse) i_PageContext.getResponse()).sendRedirect(l_DoRedirectException.getPage());
				return SKIP_PAGE;
			}
			catch (IOException l_IOException)
			{
				throw new JspException(l_IOException);
			}
		}
		catch (ClassNotFoundException l_ClassNotFoundException)
		{
			throw new JspException("Could find Form class \"" + iS_FormClassName + "\"", l_ClassNotFoundException);
		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		i_Form = null;
		iS_FormClassName = null;
		iS_LoginPage = null;
		ib_pageHasBeenRedirected = false;
	}

	final Form get()
	{
		return i_Form;
	}
}