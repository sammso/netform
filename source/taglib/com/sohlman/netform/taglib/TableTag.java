package com.sohlman.netform.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;

import com.sohlman.netform.component.table.Table;

/**
 * @author Sampsa Sohlman
 */
public class TableTag extends ComponentTag implements IterationTag
{
	private int ii_row = 1;

	private Table i_Table = null;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		try
		{
			if(init())
			{
				i_Table = (Table) getComponentFormThisTag();
				ii_row = 1;
				if(!i_Table.isVisible())
				{
					return SKIP_BODY;
				}
			}
			else
			{
				i_PageContext.getOut().println("<b>TableTag Initalization failed<b><br>");
			}
			return EVAL_BODY_INCLUDE;
		}
		catch(IOException l_IOException)
		{
			throw new JspException(l_IOException);

		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;
	}

	/**
	 * @see javax.servlet.jsp.tagext.IterationTag#doAfterBody()
	 */
	public int doAfterBody() throws JspException
	{
		ii_row++;
		if(ii_row <= i_Table.getDisplayRowCount())
		{
			return EVAL_BODY_AGAIN;
		}
		else
		{
			return EVAL_PAGE;
		}
	}	
	
	final int getCurrentRow()
	{
		return ii_row;
	}
}