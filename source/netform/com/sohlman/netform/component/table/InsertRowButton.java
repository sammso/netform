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
package com.sohlman.netform.component.table;

import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.Form;
import com.sohlman.netform.component.Button;

/**
 * Connect this to table to add rows, no extra code is required.
 * <code>
 * 	public class MyForm ..
 * 	{
 * 		public Table table = new Table(this);
 * 		public InsertRowButton insertButton = new InsertRowButton(this, table, InsertRowButton.NOLIMIT);
 * 	} 
 * </code>
 * 
 * @author Sampsa Sohlman
 */
public class InsertRowButton extends Button implements ComponentListener, TableModelListener 
{
	public final static int NOLIMIT = -1;
	private Table i_Table;
	private TableModel i_TableModel;
	private int ii_maxRows;
	
	/**
	 * @param a_Form Form object
	 * @param a_Table Table to be connected, which it will work with.
	 * @param ai_maxRows maximum number of rows. If maximum number of rows
	 * is reached then component goes disabled.
	 */
	public InsertRowButton(Form a_Form, Table a_Table, int ai_maxRows)
	{
		super(a_Form);
		addComponentListener(this);
		i_Table = a_Table;
		ii_maxRows = ai_maxRows;
	}
	
	
	/**
	 * @see com.sohlman.netform.ComponentListener#eventAction(com.sohlman.netform.Component)
	 */
	public void eventAction(Component a_Component)
	{
		int li_count = i_Table.getTableModel().getRowCount();
		if(a_Component==this)
		{
			if(li_count < ii_maxRows || ii_maxRows == NOLIMIT)
			{
				i_Table.insertRowBeforeFirstSelection();
			}
		}
	}
	
	/**
	 * @see com.sohlman.netform.component.table.TableModelListener#tableModelChanged(int, int, int)
	 */
	public void tableModelChanged(int ai_row, int ai_column, int ai_action)
	{
		if(ai_action == TableModelListener.INSERTROW && i_TableModel != null)
		{
			// Cache rowcount. Should be fast, but never know
			int li_rowCount = i_TableModel.getRowCount(); 
			if( ii_maxRows != NOLIMIT && ii_maxRows > li_rowCount )
			{
				this.setEnabled(false);
			}
			else
			{
				this.setEnabled(true);
			}
		}
		else if(ai_action == TableModelListener.UPDATEALL )
		{
			i_TableModel = i_Table.getTableModel();
		}
	}	
}
