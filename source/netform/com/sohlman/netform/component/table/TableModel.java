package com.sohlman.netform.component.table;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Table model for Table component<br>
 * 1st row is 1 and last is getRowCount()
 * 1st column is 1 and last column is getColumnCount()
 * <br>
 * Multiple tables may be connected to throuh this table model.
 * @author Sampsa Sohlman
 *
 * Version: 13.8.2003
 *
 */

public abstract class TableModel
{
	private HashSet iHS_Tables;
	private Table i_Table;

	/**
	 * Add's new row to end of Table
	 * 
	 * @return new row number
	 */
	public abstract int add();
	/**
	 * Inserts new row before index
	 * 
	 * @param ai_before Before which row insert is odne
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
	 * @param ai_row row number, row numbers starts from 1
	 * @param ai_column column nubmer, column numbers starts from 1
	 * @return Object value of this position
	 */
	public abstract Object getValueAt(int ai_row, int ai_column);
	/**
	 * @param a_Object Object value to be set.
	 * @param ai_row row number, row numbers starts from 1
	 * @param ai_column column nubmer, column numbers starts from 1
	 * @return true if succeed false if not (this depends also class which is implementing it)
	 */
	public abstract boolean setValueAt(Object a_Object, int ai_row, int ai_column);
	/**
	 * Should return column name
	 * 
	 * @param ai_index
	 * @return String contaiging column name if it set
	 */
	public abstract String getColumnName(int ai_index);

	/**
	 * @param ai_before row inserted before this row
	 */
	public void fireInsert(int ai_before)
	{
		if (iHS_Tables != null)
		{
			Iterator l_Iterator = iHS_Tables.iterator();

			while (l_Iterator.hasNext())
			{
				Table l_Table = (Table) l_Iterator.next();
				l_Table.insertComponentRow(ai_before);
			}
		}
	}

	/**
	 * @param ai_index Index for row which is deleted
	 */
	public void fireDelete(int ai_index)
	{
		if (iHS_Tables != null)
		{
			Iterator l_Iterator = iHS_Tables.iterator();

			while (l_Iterator.hasNext())
			{
				Table l_Table = (Table) l_Iterator.next();
				l_Table.removeComponentRow(ai_index);
			}
		}
	}

	/**
	 * Update all components on all connected tables
	 */
	public void fireUpdateAll()
	{
		if (iHS_Tables != null)
		{
			Iterator l_Iterator = iHS_Tables.iterator();

			while (l_Iterator.hasNext())
			{
				Table l_Table = (Table) l_Iterator.next();
				l_Table.updateAllComponents();
			}
		}
	}


	/**
	 * @param ai_row Row index which is changed
	 * @param ai_column Column index which is changed
	 */
	public void fireColumnChanged(int ai_row, int ai_column)
	{
		if (iHS_Tables != null)
		{

			Iterator l_Iterator = iHS_Tables.iterator();

			while (l_Iterator.hasNext())
			{
				Table l_Table = (Table) l_Iterator.next();
				l_Table.updateComponent(ai_row, ai_column);
			}
		}
	}

	/**
	 * Set table to TableModel
	 * 
	 * @param a_Table Table to be set
	 */
	public void setTable(Table a_Table)
	{
		if (iHS_Tables == null)
		{
			iHS_Tables = new HashSet();
		}

		if (!iHS_Tables.contains(a_Table))
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
	 * @param a_Table Table to be removed
	 */
	public void removeTable(Table a_Table)
	{
		if(iHS_Tables!=null)
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
}
