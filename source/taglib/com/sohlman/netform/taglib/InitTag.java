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
		System.out.println(aS_FormClassName);

		iS_FormClassName = aS_FormClassName;
	}

	public void setLoginpage(String aS_LoginPage)
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
				i_PageContext.setAttribute(FORM, i_Form);
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