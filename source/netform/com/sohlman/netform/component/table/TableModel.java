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
	 * row count
	 * 
	 * @return
	 */
	public abstract int getRowCount();
	public abstract int getColumnCount();
	public abstract Object getValueAt(int ai_row, int ai_column);
	public abstract boolean setValueAt(Object a_Object, int ai_row, int ai_column);
	public abstract String getColumnName(int ai_index);

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

	public void fireUpdate()
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
	 * @param ai_index
	 * @return false
	 */
	public boolean isNew(int ai_index)
	{
		return false;
	}
}
