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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.sohlman.netform.component.table.Table;

/**
 * This Tag creates &lt;option&gt; part of &lt;select&gt; tag.
 * 
 * @author Sampsa Sohlman
 */
public class SelectItemTag extends MasterTag
{
	private Table i_Table = null;

	private String iS_Style = null;

	private String iS_Class = null;

	private String iS_Id = null;

	private String iS_ColumnName = null;

	private int ii_column = 1;

	private SelectTag i_SelectTag = null;

	public void setIndex(String aS_Column)
	{
		ii_column = Integer.valueOf(aS_Column).intValue();
	}

	public void setName(String aS_Column)
	{
		iS_ColumnName = aS_Column;
	}

	public void setStyle(String aS_Style)
	{
		iS_Style = aS_Style;
	}

	public void setStyleClass(String aS_Class)
	{
		iS_Class = aS_Class;
	}

	public void setId(String aS_Id)
	{
		iS_Id = aS_Id;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
public int doEndTag() throws JspException
	{
		try
		{
			StringBuffer l_StringBuffer = new StringBuffer();
			l_StringBuffer.append("<option value=\"");
			int li_row = i_SelectTag.getCurrentRow();
			l_StringBuffer.append(i_Table.getRowId(li_row));
			l_StringBuffer.append("\"");

			if(i_Table.isRowSelected(li_row))
			{
				l_StringBuffer.append(" selected");
			}

			if(iS_Class != null)
			{
				l_StringBuffer.append(" class=\"");
				l_StringBuffer.append(iS_Class);
				l_StringBuffer.append("\"");
			}
			if(iS_Id != null)
			{
				l_StringBuffer.append(" id=\"");
				l_StringBuffer.append(iS_Id);
				l_StringBuffer.append("\"");
			}
			l_StringBuffer.append(">");			
			if(iS_ColumnName != null)
			{
				l_StringBuffer.append(i_Table.getText(li_row, iS_ColumnName));
			}
			else
			{
				l_StringBuffer.append(i_Table.getText(li_row, ii_column));
			}
			l_StringBuffer.append("</option>");
			i_PageContext.getOut().print(l_StringBuffer.toString());
			return EVAL_PAGE;
		}
		catch (IOException l_IOException)
		{
			throw new JspException(l_IOException);
		}
	}
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		Tag l_Tag = getParent();
		Object l_Object = null;
		if(SelectTag.class.isInstance(l_Tag))
		{
			i_SelectTag = ((SelectTag) getParent());

			i_Table = (Table) i_SelectTag.getComponentFormThisTag();
			
			if(i_Table == null)
			{
				throw new JspException("Page design error! Table component is not set for SELECT tag");
			}
			return SKIP_BODY;
		}
		else
		{
			throw new JspException("Page design error! SELECT tag has to be master for SELECTITEM");
		}

	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		i_Table = null;
		iS_Style = null;
		iS_Class = null;
		iS_Id = null;
		iS_ColumnName = null;
		ii_column = 1;
		i_SelectTag = null;
	}
}