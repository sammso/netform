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
package com.sohlman.netform.component.table;
import java.util.List;

import com.sohlman.netform.NotSupportedException;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-03-01
 */
public abstract class ListTableModel extends TableModel
{
	private List i_List = null;
	private String[] iS_ColumnNames = null;
	private int ii_rowCount = 0;
	
	public void setList(List a_List)
	{
		if(a_List==null)
		{
			throw new NullPointerException("Parameter of ListTableModel.setList cannot be null");
		}
		i_List = a_List;
		fireUpdateAll();
	}
	
	/**
	 * Set columnNames to ListTable<br>
	 * <b>This has be called on constructor of inherited class</b>
	 * 
	 * @param aS_ColumnNames
	 */
	protected void setColumnNames(String[] aS_ColumnNames)
	{
		if(aS_ColumnNames==null)
		{
			throw new NullPointerException("Parameter of ListTableModel.setColumnNames cannot be null ");
		}
		iS_ColumnNames = aS_ColumnNames;
	}
	
	/**
	 * @see com.sohlman.netform.component.table.TableModel#add()
	 */
	public int add()
	{
		return insert(getRowCount() + 1);
	}

	/**
	 * @see com.sohlman.netform.component.table.TableModel#insert(int)
	 */
	public int insert(int ai_before)
	{
		if(i_List==null)
		{
			throw new IllegalStateException("List is not defined");
		}
		
		if(ai_before < 1 || ai_before  > ( getRowCount() + 1) )
		{
			throw new ArrayIndexOutOfBoundsException("Tried to insert row out of range");
		}
		Object l_Object = createRow();
		
		if(l_Object==null)
		{
			throw new NullPointerException("Result of create row is not allowed to be null.");
		}
		
		i_List.add(ai_before - 1, l_Object);
		fireInsert(ai_before);
		return ai_before;
	}
	
	/**
	 * This returns object to be crated inside List
	 * 
	 * @return Object to be created, return null is not allowed
	 */
	public Object createRow()
	{
		throw new NotSupportedException(this.getClass().getName() +  " is read only and createRow() is not supported");
	}

	/**
	 * @see com.sohlman.netform.component.table.TableModel#delete(int)
	 */
	public int delete(int ai_row)
	{
		if(ai_row < 1 || ( ai_row - 1) >= getRowCount())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to delete row from out of range");
		}
		i_List.remove(ai_row - 1);
		fireDelete(ai_row);
		return ai_row;	
	}

	/**
	 * @see com.sohlman.netform.component.table.TableModel#getRowCount()
	 */
	public int getRowCount()
	{
		if(i_List==null)
		{
			ii_rowCount = 0;
			return 0;
		}
		else
		{
			int li_size = i_List.size();
			
			if(li_size != ii_rowCount)
			{
				ii_rowCount = li_size;
				fireUpdateAll();
			}
			return li_size;
		}
	}

	/**
	 * @see com.sohlman.netform.component.table.TableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return iS_ColumnNames.length;
	}

	/**
	 * @see com.sohlman.netform.component.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int ai_row, int ai_column)
	{
		if(ai_row < 1 || ( ai_row - 1 ) >= getRowCount())
		{
			throw new ArrayIndexOutOfBoundsException("Tried get from out of row range");
		}	
		if(ai_column < 1 || ai_column > getColumnCount() )
		{
			throw new ArrayIndexOutOfBoundsException("Tried to get column out of column range. Column range is always 1");
		}		
		return mapObjectFromRow(i_List.get(ai_row - 1), ai_column);
	}

	/**
	 * Maps Object from row.
	 * 
	 * @param aO_Row Row where from Object readed
	 * @param ai_columnIndex and which index
	 * @return result object
	 */
	protected abstract Object mapObjectFromRow(Object aO_Row, int ai_columnIndex);

	/**
	 * @see com.sohlman.netform.component.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public boolean setValueAt(Object a_Object, int ai_row, int ai_column)
	{
		if(ai_row < 1 || ( ai_row - 1 ) > getRowCount())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to set value to out of row range");
		}
		
		if(ai_column < 1 || ai_column > getColumnCount())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to set value to out of column range");
		}
		Object lO_Row = i_List.get(ai_row - 1);
		mapObjectToRow(a_Object, lO_Row, ai_column);
		
		fireColumnChanged(ai_row, ai_column);
		return true;
	}
	
	/**
	 * Maps Object to Row, this is to be implement
	 * Row is Object inside list
	 * 
	 * 
	 * @param a_Object Object to be mapped
	 * @param aO_Row Row object
	 * @param ai_columnIndex columnIndex of object
	 */
	protected void mapObjectToRow(Object a_Object, Object aO_Row, int ai_columnIndex)
	{
		throw new NotSupportedException(this.getClass().getName() +  " is read only and mapObjectToRow(Object, Object, int) is not supported");
	}

	/**
	 * @see com.sohlman.netform.component.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int ai_index)
	{
		if(ai_index < 1 || ai_index > getColumnCount())
		{
			throw new ArrayIndexOutOfBoundsException(ai_index + " is too big. Max column index is " + getColumnCount());	
		}
		return iS_ColumnNames[ai_index - 1];
	}

	/**
	 * @see com.sohlman.netform.component.table.TableModel#search(java.lang.Object, int)
	 */
	public int search(Object a_Object, int ai_column)
	{
		if(i_List!=null)
		{
			int li_size = getRowCount();
			for(int li_index = 1 ; li_index <=  li_size ; li_index++)
			{
				Object l_Object = getValueAt(li_index, ai_column);
				if(( l_Object == a_Object ) || (l_Object!=null && a_Object!=null && a_Object.equals(l_Object)))
				{
					return li_index;
				}
			}
			return -1;
		}
		else
		{
			return -1;
		}
	}

	/**
	 * @see com.sohlman.netform.component.table.TableModel#getIndexByName(java.lang.String)
	 */
	public int getIndexByName(String aS_Name)
	{
		for(int li_index = 1 ; li_index <= getColumnCount() ; li_index++ )
		{
			if (aS_Name.equalsIgnoreCase(getColumnName(li_index)))
			{
				return li_index;
			}
		}
		throw new IllegalArgumentException(aS_Name + " is not column name");	
	}

	/**
	 * @return assosiated List object
	 */
	public List getList()
	{
		return i_List;
	}
}
