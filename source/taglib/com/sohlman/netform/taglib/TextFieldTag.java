package com.sohlman.netform.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.sohlman.netform.component.TextField;

/**
 * @author Sampsa Sohlman
 */
public class TextFieldTag extends ComponentTag
{
	private TextField i_TextField = null;
	private String iS_Style = null;
	private String iS_Class = null;
	private String iS_NotValidStyle = null;
	private String iS_NotValidClass = null;
	private String iS_Id = null;
	private String iS_Format = null;
	

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

	public void setFormat(String aS_Format)
	{
		iS_Format = aS_Format;
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			i_PageContext.getOut().print("<input");
			i_PageContext.getOut().print(" type=\"text\"");
			i_PageContext.getOut().print(" name=\"" + i_TextField.getResponseName() + "\"");

			i_PageContext.getOut().print(" value=\"" + i_TextField.getText() + "\"");

			if(iS_Style != null)
			{
				if(iS_NotValidStyle == null || i_TextField.isValid())
				{
					i_PageContext.getOut().print(" style=\"" + iS_Style + "\"");
				}
				else
				{
					i_PageContext.getOut().print(" style=\"" + iS_NotValidStyle + "\"");
				}
			}

			if(iS_Class != null)
			{
				if(iS_NotValidClass == null || i_TextField.isValid())
				{
					i_PageContext.getOut().print(" class=\"" + iS_Class + "\"");
				}
				else
				{
					i_PageContext.getOut().print(" class=\"" + iS_NotValidClass + "\"");
				}
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
			i_TextField = (TextField) getComponentFormThisTag();
			
			if(iS_Format!=null)
			{
				i_TextField.setFormat(iS_Format);
			}
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
		i_TextField = null;
		iS_Style = null;
		iS_Class = null;
		iS_NotValidStyle = null;
		iS_NotValidClass = null;
		iS_Id = null;
		iS_Format = null;
	}
}