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
	private String iS_Component = null;

	public void setComponent(String aS_Component)
	{
		iS_Component = aS_Component;
		
		System.out.println("setComponent(" + aS_Component + ")");
	}
	
	public void setMethod(String aS_Method)
	{
		iS_Method = aS_Method;

		System.out.println("setParameter(" + aS_Method + ")");
	}
	
	public void setValue(String aS_Value)
	{
		iS_Value = aS_Value;		
		System.out.println("setValue(" + aS_Value + ")");
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
		// TODO Auto-generated method stub

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