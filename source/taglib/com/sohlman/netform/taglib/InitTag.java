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
	
	
	public void setForm(String aS_FormClassName)
	{ 
		System.out.println(aS_FormClassName);
		
		iS_FormClassName = aS_FormClassName; 
	}	
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			i_Form.execute();
			i_PageContext.setAttribute(FORM, i_Form);
			return EVAL_PAGE;
		}
		catch(DoRedirectException l_DoRedirectException)
		{
			try
			{
				
				
				((HttpServletResponse)i_PageContext.getResponse()).sendRedirect(l_DoRedirectException.getPage());
			}
			catch(IOException l_IOException)
			{
				System.out.println("IOException Exception");
				System.out.println("  Message : " + l_IOException.getMessage());				
			}
		}
		return SKIP_PAGE;
	}
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		try
		{	
			Class l_Class = Class.forName(iS_FormClassName);
			
			i_Form = FormManager.getForm((HttpServletRequest)i_PageContext.getRequest(), (HttpServletResponse)this.i_PageContext.getResponse(), i_PageContext.getServletContext(),l_Class);
			
			if(i_Form.isInitialized())
			{
				return SKIP_BODY;
			}
			else
			{
				return EVAL_BODY_INCLUDE;
			}
		}
		catch(DoRedirectException l_DoRedirectException)
		{
			try
			{
				((HttpServletResponse)i_PageContext.getResponse()).sendRedirect(l_DoRedirectException.getPage());
			}
			catch(IOException ioException)
			{
				
			}
		}
		catch(ClassNotFoundException l_ClassNotFoundException)
		{
			System.out.println("ClassNotFound Exception");
			System.out.println("  Message : " + l_ClassNotFoundException.getMessage());
		}		
		return SKIP_PAGE;
	}
	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		i_Form = null;
		iS_FormClassName = null;
	}
	
	final Form get()
	{
		return i_Form;
	}
}
