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
import com.sohlman.netform.ComponentData;
import com.sohlman.netform.ComponentDataException;

/**
 * @author Sampsa Sohlman
 * 
 * @version Jan 13, 2004
 */
public class TableComponentData implements ComponentData
{
	private int ii_column;
	private int ii_row;
	private TableModel i_TableModel;
	
	//
	// Component which is handling data
	//
	private Component i_Component;
	
	public TableComponentData(TableModel a_TableModel)
	{
		i_TableModel = a_TableModel;
	}
	
	/**
	 * @see com.sohlman.netform.ComponentData#setData(java.lang.Object)
	 */
	public void setData(Object a_Object) throws ComponentDataException
	{
		i_TableModel.setValueAt(a_Object, ii_row, ii_column);
	}
	
	public Object getData()
	{
		return i_TableModel.getValueAt(ii_row, ii_column);
	}
	
	public void setColumn(int ai_column)
	{
		ii_column = ai_column;
	}

	public void setRow(int ai_row)
	{
		ii_row = ai_row;
	}

	public int getRow()
	{
		return ii_row;
	}
	
	public int getColumn()
	{
		return ii_column;
	}
}
