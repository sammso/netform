package com.sohlman.netform.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.sohlman.netform.component.Button;

/**
 * @author Sampsa Sohlman
 */
public class ButtonTag extends ComponentTag
{
	private Button i_Button = null;
	private String iS_Style = null;
	private String iS_Value = null;
	private String iS_Class = null;
	private String iS_NotValidStyle = null;
	private String iS_NotValidClass = null;
	private String iS_Id = null;

	public void setStyle(String aS_Style)
	{
		iS_Style = aS_Style;
	}

	public void setClass(String aS_Class)
	{
		iS_Class = aS_Class;
	}

	public void setNotValidStyle(String aS_NotValidStyle)
	{
		iS_NotValidStyle = aS_NotValidStyle;
	}

	public void setNotValidClass(String aS_NotValidClass)
	{
		iS_NotValidClass = aS_NotValidClass;
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
		try
		{
			i_PageContext.getOut().print("<input");
			i_PageContext.getOut().print(" type=\"submit\"");
			i_PageContext.getOut().print(" name=\"" + i_Button.getResponseName() + "\"");

			if(i_Button.isValid())
			{
				if(iS_Style!=null)
				{
					i_PageContext.getOut().print(" style=\"" + iS_Style + "\"");
				}		
				if(iS_Class!=null)
				{
					i_PageContext.getOut().print(" class=\"" + iS_Class + "\"");
				}
			}
			else
			{
				if(iS_NotValidStyle == null && iS_Style!=null)
				{
					i_PageContext.getOut().print(" style=\"" + iS_Style + "\"");
				}
				else if(iS_NotValidStyle != null)
				{
					i_PageContext.getOut().print(" style=\"" + iS_NotValidStyle + "\"");
				}
				if(iS_NotValidClass == null || iS_Class!=null)
				{
					i_PageContext.getOut().print(" class=\"" + iS_Class + "\"");
				}
				else if(iS_NotValidClass != null)
				{
					i_PageContext.getOut().print(" class=\"" + iS_NotValidClass + "\"");
				}				
			}
			
			if(iS_Value !=null)
			{
				i_PageContext.getOut().print(" value=\"" + iS_Value + "\"");
			}

			return EVAL_PAGE;
		}
		catch (IOException l_IOException)
		{
			return EVAL_PAGE;
		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		if(init())
		{
			i_Button = (Button) getComponentFormThisTag();
		}
		else
		{
			// ERROR
		}
		return SKIP_BODY;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		super.release();
		i_Button = null;
		iS_Style = null;
		iS_Class = null;
		iS_Value = null;
		iS_NotValidStyle = null;
		iS_NotValidClass = null;
		iS_Id = null;
	}
}