package com.sohlman.netform;

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
		Iterator l_Iterator = iHS_Tables.iterator(); 
		
		while(l_Iterator.hasNext())
		{
			Table l_Table = (Table)l_Iterator.next();
			l_Table.insertComponentRow(ai_before);	
		}
	}
	
	public void fireDelete(int ai_index)
	{
		Iterator l_Iterator = iHS_Tables.iterator(); 
		
		while(l_Iterator.hasNext())
		{
			Table l_Table = (Table)l_Iterator.next();
			l_Table.removeComponentRow(ai_index);	
		}		
	}

	public void fireUpdateAll()
	{
		Iterator l_Iterator = iHS_Tables.iterator(); 
		
		while(l_Iterator.hasNext())
		{
			Table l_Table = (Table)l_Iterator.next();
			l_Table.updateAllComponents();	
		}			
	}
	
	public void fireUpdate()
	{
		Iterator l_Iterator = iHS_Tables.iterator(); 
		
		while(l_Iterator.hasNext())
		{
			Table l_Table = (Table)l_Iterator.next();
			l_Table.updateAllComponents();	
		}		
	}
	
	public void fireColumnChanged(int ai_row, int ai_column)
	{
		Iterator l_Iterator = iHS_Tables.iterator();
		
		while(l_Iterator.hasNext())
		{
			Table l_Table = (Table)l_Iterator.next();
			l_Table.updateComponent(ai_row, ai_column);
		}
	}
	
	public void setTable(Table a_Table)
	{
		if(iHS_Tables==null)
		{
			iHS_Tables = new HashSet();
		}
		
		if(!iHS_Tables.contains(a_Table))
		{
			// Not allowed to be registed twice
			iHS_Tables.add(a_Table);
		}
	}

	public abstract int search(Object a_Object, int ai_column);
	
	public void removeTable(Table a_Table)
	{
		iHS_Tables.remove(a_Table);
	}
}
