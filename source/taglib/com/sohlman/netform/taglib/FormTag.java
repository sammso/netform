package com.sohlman.netform.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.sohlman.netform.Form;


/**
 * @author Sampsa Sohlman
 */
public class FormTag extends MasterTag
{
	private Form i_Form = null;
	private String iS_Name = null;
	private String iS_Class = null;
	private String iS_Id = null;
	private String iS_Style = null;
	private String iS_Enctype = null;
	
	public void setName(String aS_Name)
	{
		iS_Name = aS_Name;
	}
	
	public void setClass(String aS_Class)
	{
		iS_Class = aS_Class;
	}
	
	public void setStyle(String aS_Style)
	{
		iS_Style = aS_Style;
	}
	
	public void setEnctype(String aS_Enctype)
	{
		iS_Enctype = aS_Enctype;
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			i_PageContext.getOut().print("</form>");
		}
		catch(IOException ioException)
		{
			
		}
		return EVAL_PAGE;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		i_Form = (Form)i_PageContext.getRequest().getAttribute(FORM);
		
		if(i_Form==null)
		{
			throw new JspException("Form is not defined you have to specify that by using init tag");
		}
		
		try
		{
			i_PageContext.getOut().print("<form");
			if(iS_Name!=null)
			{
				i_PageContext.getOut().print(" name=\"" + iS_Name + "\"");
			}
			
			i_PageContext.getOut().print(" action=\"" + i_Form.getPostAction() + "\"");
			
			if(iS_Enctype!=null)
			{
				i_PageContext.getOut().print(" enctype=\"" + iS_Enctype + "\"");
			}
			
			if(iS_Style!=null)
			{
				i_PageContext.getOut().print(" style=\"" + iS_Style + "\"");
			}			
			if(iS_Id!=null)
			{
				i_PageContext.getOut().print(" id=\"" + iS_Id + "\"");
			}	
			
			i_PageContext.getOut().print(" method=\"post\">");
			return EVAL_BODY_INCLUDE;
		}
		catch(IOException ioException)
		{
			
		}
		return 0;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		Form i_Form = null;
		String iS_Name = null;
		String iS_Class = null;
		String iS_Id = null;
		String iS_Style = null;
		String iS_Enctype = null;
	}
}
