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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import com.sohlman.netform.component.table.Table;

/**
 * @author Sampsa Sohlman
 */
public class TableSelectionTag extends MasterTag
{
	private String iS_Style = null;
	private String iS_Class = null;
	private String iS_Type = "checkbox";
	private TableTag i_TableTag = null;
	private Table i_Table = null;
	
	public void setType(String aS_Type)
	{
		if(aS_Type.equals("checkbox")||aS_Type.equals("radio"))
		{
			iS_Type = aS_Type;
		}
	}
	
	public void setStyle(String aS_Style)
	{
		iS_Style = aS_Style;
	}

	public void setClass(String aS_Class)
	{
		iS_Class = aS_Class;
	}
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			JspWriter l_JspWriter = i_PageContext.getOut();
			
			int li_row = i_TableTag.getCurrentRow();
			
			l_JspWriter.print("<input type=\"");
			l_JspWriter.print(iS_Type);
			l_JspWriter.print("\"");

			l_JspWriter.print(" name=\"");
			l_JspWriter.print(i_Table.getResponseName());
			l_JspWriter.print("\"");			
			
			l_JspWriter.print(" value=\"");
			l_JspWriter.print(i_Table.getRowId(li_row));
			l_JspWriter.print("\"");				
			
			if(iS_Class!=null)
			{
				l_JspWriter.print(" class=\"");
				l_JspWriter.print(iS_Class);
				l_JspWriter.print("\"");				
			}
			if(iS_Style!=null)
			{
				l_JspWriter.print(" style=\"");
				l_JspWriter.print(iS_Style);
				l_JspWriter.print("\"");				
			}
			
			
			if(i_Table.isRowSelected(li_row))
			{
				l_JspWriter.print(" checked ");
			}
			
			l_JspWriter.print(">");
			return EVAL_PAGE;
		}
		catch(IOException l_IOException)
		{
			throw new JspException(l_IOException);
		}
	}
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		try
		{
			Tag l_Tag = getParent();
			Object l_Object = null;
			if(TableTag.class.isInstance(l_Tag))
			{
				i_TableTag = ((TableTag) getParent());

				i_Table = (Table) i_TableTag.getComponentFormThisTag();
				if(i_Table == null)
				{
					i_PageContext.getOut().println(
							"<b>Page design error! Table component is not set for &lt;nf:select&gt; tag</b><br>");
				}
			}
			else
			{
				i_PageContext.getOut().println(
						"<b>Page design error! &lt;nf:select&gt; has to master for &lt;nf:selectitem&gt;</b><br>");
			}
		}
		catch (IOException l_IOException)
		{
			throw new JspException(l_IOException);
		}
		return SKIP_BODY;		
	}
	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		iS_Style = null;
		iS_Class = null;
	}
}
