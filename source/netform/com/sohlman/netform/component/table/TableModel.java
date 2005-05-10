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

import java.util.HashSet;
import java.util.Iterator;

import com.sohlman.netform.ComponentDataException;
import com.sohlman.netform.NotSupportedException;

/**
 * Table model for Table component <br>
 * 1st row is 1 and last is getRowCount() 1st column is 1 and last column is
 * getColumnCount() <br>
 * Multiple tables may be connected to throuh this table model.
 * 
 * @author Sampsa Sohlman
 * 
 * TODO: Table update through TableModelListener
 * 
 * Version: 13.8.2003
 *  
 */

public abstract class TableModel
{
	public static final int WHOLEROW = -1;
	private HashSet iHS_Tables;
	private Table i_Table;
	private HashSet iHS_TableModelListener;

	/**
	 * Add's new row to end of Table
	 * 
	 * @return new row number
	 */
	public abstract int add();

	/**
	 * Inserts new row before index
	 * 
	 * @param ai_before
	 *            Before which row insert is odne
	 * @return inserted row number
	 */
	public abstract int insert(int ai_before);

	/**
	 * Deletes row
	 * 
	 * @param ai_row
	 * @return deleted row number
	 */
	public abstract int delete(int ai_row);

	/**
	 * @return row count of table model
	 */
	public abstract int getRowCount();

	/**
	 * @return column count of table model
	 */
	public abstract int getColumnCount();

	/**
	 * @param ai_row
	 *            row number, row numbers starts from 1
	 * @param ai_column
	 *            column nubmer, column numbers starts from 1
	 * @return Object value of this position
	 */
	public abstract Object getValueAt(int ai_row, int ai_column);

	/**
	 * @param a_Object
	 *            Object value to be set.
	 * @param ai_row
	 *            row number, row numbers starts from 1
	 * @param ai_column
	 *            column nubmer, column numbers starts from 1
	 * @return true if succeed false if not (this depends also class which is
	 *         implementing it)
	 * @throws ComponentDataException
	 *             TODO
	 */
	public abstract boolean setValueAt(Object a_Object, int ai_row, int ai_column) throws ComponentDataException;

	/**
	 * Should return column name
	 * 
	 * @param ai_index
	 * @return String contaiging column name if it set
	 */
	public abstract String getColumnName(int ai_index);

	/**
	 * @param ai_before
	 *            row inserted before this row
	 */
	public void fireInsert(int ai_before)
	{
		if(iHS_Tables != null)
		{
			Iterator l_Iterator = iHS_Tables.iterator();

			while (l_Iterator.hasNext())
			{
				Table l_Table = (Table) l_Iterator.next();
				l_Table.insertComponentRow(ai_before);
			}
		}
		if(iHS_TableModelListener != null)
		{
			Iterator l_Iterator = iHS_TableModelListener.iterator();

			while (l_Iterator.hasNext())
			{

				TableModelListener l_TableModelListener = (TableModelListener) l_Iterator.next();
				l_TableModelListener.tableModelChanged(ai_before, 0, TableModelListener.INSERTROW);
			}
		}
	}

	/**
	 * @param ai_index
	 *            Index for row which is deleted
	 */
	public void fireDelete(int ai_index)
	{
		if(iHS_Tables != null)
		{
			Iterator l_Iterator = iHS_Tables.iterator();

			while (l_Iterator.hasNext())
			{
				Table l_Table = (Table) l_Iterator.next();
				l_Table.removeComponentRow(ai_index);
			}
		}
		if(iHS_TableModelListener != null)
		{
			Iterator l_Iterator = iHS_TableModelListener.iterator();
			while (l_Iterator.hasNext())
			{
				TableModelListener l_TableModelListener = (TableModelListener) l_Iterator.next();
				l_TableModelListener.tableModelChanged(ai_index, 0, TableModelListener.DELETEROW);
			}
		}
	}

	/**
	 * Update all components on all connected tables
	 */
	public void fireUpdateAll()
	{
		if(iHS_Tables != null)
		{
			Iterator l_Iterator = iHS_Tables.iterator();

			while (l_Iterator.hasNext())
			{
				Table l_Table = (Table) l_Iterator.next();
				l_Table.updateAllComponents();
			}
		}
		if(iHS_TableModelListener != null)
		{
			Iterator l_Iterator = iHS_TableModelListener.iterator();
			while (l_Iterator.hasNext())
			{
				TableModelListener l_TableModelListener = (TableModelListener) l_Iterator.next();
				l_TableModelListener.tableModelChanged(0, 0, TableModelListener.UPDATEALL);
			}
		}
	}

	/**
	 * @param ai_row
	 *            Row index which is changed
	 * @param ai_column
	 *            Column index which is changed
	 */
	public void fireColumnChanged(int ai_row, int ai_column)
	{
		if(iHS_Tables != null)
		{
			if(ai_column == WHOLEROW)
			{
				Iterator l_Iterator = iHS_Tables.iterator();
				while (l_Iterator.hasNext())
				{
					Table l_Table = (Table) l_Iterator.next();
					l_Table.updateComponentRow(ai_row);
				}
			}
			else
			{
				Iterator l_Iterator = iHS_Tables.iterator();
				while (l_Iterator.hasNext())
				{
					Table l_Table = (Table) l_Iterator.next();
					l_Table.updateComponent(ai_row, ai_column);
				}
			}
		}
		if(iHS_TableModelListener != null)
		{
			if(ai_column == WHOLEROW)
			{
				Iterator l_Iterator = iHS_TableModelListener.iterator();
				while (l_Iterator.hasNext())
				{
					TableModelListener l_TableModelListener = (TableModelListener) l_Iterator.next();
					l_TableModelListener.tableModelChanged(ai_row, ai_column, TableModelListener.ROWMODIFIED);
				}				
			}
			else
			{
				Iterator l_Iterator = iHS_TableModelListener.iterator();
				while (l_Iterator.hasNext())
				{
					TableModelListener l_TableModelListener = (TableModelListener) l_Iterator.next();
					l_TableModelListener.tableModelChanged(ai_row, ai_column, TableModelListener.COLUMNCHANGED);
				}
			}
		}
	}

	/**
	 * Set table to TableModel
	 * 
	 * @param a_Table
	 *            Table to be set
	 */
	public void setTable(Table a_Table)
	{
		if(iHS_Tables == null)
		{
			iHS_Tables = new HashSet();
		}

		if(!iHS_Tables.contains(a_Table))
		{
			// Not allowed to be registed twice
			iHS_Tables.add(a_Table);
		}
	}

	/**
	 * Search by column index
	 * 
	 * @param a_Object
	 * @param ai_column
	 * @return row number which contains search hed item
	 */
	public abstract int search(Object a_Object, int ai_column);

	/**
	 * Search by column name
	 * 
	 * @param a_Object
	 * @param aS_ColumnName
	 * @return row number which contains search hed item
	 */
	public int search(Object a_Object, String aS_ColumName)
	{
		return search(a_Object, getIndexByName(aS_ColumName));
	}

	/**
	 * get index by name, if not supported
	 * 
	 * @param aS_Name
	 * @return index for name
	 */
	public abstract int getIndexByName(String aS_Name);

	/**
	 * Remove Table from Table model
	 * 
	 * @param a_Table
	 *            Table to be removed
	 */
	public void removeTable(Table a_Table)
	{
		if(iHS_Tables != null)
		{
			iHS_Tables.remove(a_Table);
		}
	}

	/**
	 * Default behavior is to return always false
	 * 
	 * This should be implement on child class
	 * 
	 * @param ai_index
	 * @return false
	 */
	public boolean isNew(int ai_index)
	{
		return false;
	}

	/**
	 * <p>
	 * get's object which implements row item.
	 * <p>
	 * Implementation of this method is optional.
	 * 
	 * @param ai_index
	 * @return Object
	 * @throws NotSupportedException
	 *             if this feature is not supported.
	 */
	public Object getRowItem(int ai_index)
	{
		throw new NotSupportedException(this.getClass() + " doesn't support getRowItem(int) method");
	}
}