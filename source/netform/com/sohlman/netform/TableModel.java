package com.sohlman.netform;

/**
 * Table model for Table component<br>
 * 1st row is 1 and last is getRowCount()
 * 1st column is 1 and last column is getColumnCount()
 * @author Sampsa Sohlman
 *
 * Version: 13.8.2003
 *
 */

public abstract class TableModel
{	
	Table i_Table;
	
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
		i_Table.insertComponentRow(ai_before);	
	}
	
	public void fireDelete(int ai_index)
	{
		i_Table.removeComponentRow(ai_index);
	}

	public void fireUpdateAll()
	{
		i_Table.updateAllComponents();
	}
	
	public void setTable(Table a_Table)
	{
		i_Table = a_Table;
	}
}
