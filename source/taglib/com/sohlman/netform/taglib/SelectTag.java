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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;

import com.sohlman.netform.component.table.Table;

/**
 * @author Sampsa Sohlman
 */
public class SelectTag extends ComponentTag implements IterationTag
{
	private Table i_Table = null;

	private String iS_Style = null;

	private String iS_Class = null;

	private String iS_NotValidStyle = null;

	private String iS_NotValidClass = null;

	private String iS_Id = null;

	private int ii_size = 1;

	private int ii_index = 1;

	public void setStyle(String aS_Style)
	{
		iS_Style = aS_Style;
	}

	public void setStyleClass(String aS_Class)
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

	public void setSize(String aS_Size)
	{
		ii_size = Integer.valueOf(aS_Size).intValue();
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		if(i_Table.isVisible())
		{
			try
			{
				i_PageContext.getOut().print("</select>");
			}
			catch (IOException l_IOException)
			{
				throw new JspException(l_IOException);
			}
		}
		return EVAL_PAGE;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		if(init())
		{
			i_Table = (Table) getComponentFormThisTag();
			if(!i_Table.isVisible())
			{
				return SKIP_BODY;
			}

			
			ii_index = 1;
			try
			{
				i_PageContext.getOut().print("<select");
				i_PageContext.getOut().print(" name=\"" + i_Table.getResponseName() + "\"");

				i_PageContext.getOut().print(" size=\"" + String.valueOf(ii_size) + "\"");

				if(iS_Style != null)
				{
					if(iS_NotValidStyle == null || i_Table.isValid())
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
					if(iS_NotValidClass == null || i_Table.isValid())
					{
						i_PageContext.getOut().print(" class=\"" + iS_Class + "\"");
					}
					else
					{
						i_PageContext.getOut().print(" class=\"" + iS_NotValidClass + "\"");
					}
				}
			}
			catch (IOException l_IOException)
			{
				return EVAL_PAGE;
			}
		}
		else
		{
			// ERROR
		}
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * @see javax.servlet.jsp.tagext.IterationTag#doAfterBody()
	 */
	public int doAfterBody() throws JspException
	{
		ii_index++;
		if(ii_index <= i_Table.getTableModel().getRowCount())
		{
			return EVAL_BODY_AGAIN;
		}
		else
		{
			return EVAL_PAGE;
		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		super.release();
		i_Table = null;
		iS_Style = null;
		iS_Class = null;
		iS_NotValidStyle = null;
		iS_NotValidClass = null;
		iS_Id = null;
		ii_size = 1;
	}

	final int getCurrentRow()
	{
		return ii_index;
	}
}