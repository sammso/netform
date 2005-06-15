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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;

import com.sohlman.netform.component.table.Table;
import com.sohlman.netform.component.table.TableModel;

/**
 * @author Sampsa Sohlman
 */
public class TableTag extends ComponentTag implements IterationTag
{
	private int ii_row = 1;

	private Table i_Table = null;
	private TableRow i_TableRow = null;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		init();
		i_Table = (Table) getComponentFormThisTag();
		
		if(!i_Table.isVisible())
		{
			return SKIP_BODY;
		}
		if(i_Table.getTableModel().getRowCount() <= 0)
		{
			return SKIP_BODY;
		}
		i_TableRow = new TableRow(i_Table);
		ii_row = 1;
		TableModel l_TableModel = i_Table.getTableModel();
		for(int li_index = 1 ; li_index <= l_TableModel.getColumnCount() ; li_index++ )
		{
			String lS_Name = i_Table.getTableModel().getColumnName(li_index);
			i_PageContext.setAttribute(lS_Name,i_Table.getText(ii_row,li_index));
		}
		return EVAL_BODY_INCLUDE;
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
		i_TableRow.addRow();
		if(i_TableRow.getRowNo() <= i_Table.getDisplayRowCount())
		{
			TableModel l_TableModel = i_Table.getTableModel();
			for(int li_index = 1 ; li_index <= l_TableModel.getColumnCount() ; li_index++ )
			{
				String lS_Name = i_Table.getTableModel().getColumnName(li_index);
				i_PageContext.setAttribute(lS_Name, i_Table.getText(ii_row,li_index));
			}			
			return EVAL_BODY_AGAIN;
		}
		else
		{
			TableModel l_TableModel = i_Table.getTableModel();
			
			for(int li_index = 1 ; li_index <= l_TableModel.getColumnCount() ; li_index++ )
			{
				String lS_Name = i_Table.getTableModel().getColumnName(li_index);
				i_PageContext.removeAttribute(lS_Name);
			}
			return EVAL_PAGE;
		}
	}

	final int getCurrentRow()
	{
		return i_TableRow.getRowNo();
	}
}