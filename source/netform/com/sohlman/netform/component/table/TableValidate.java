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

import com.sohlman.netform.NetFormException;
import com.sohlman.netform.Validate;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-04-20
 */
public class TableValidate extends Validate
{
	private int[] ii_selectedItems;
	
	public TableValidate(Table a_Table, int[] ai_selectedItems)
	{
		i_Component = a_Table;
		ii_selectedItems = ai_selectedItems;
	}
	
	public int[] getSelectedItems()
	{
		return ii_selectedItems;
	}
	
	/**
	 * @see com.sohlman.netform.Validate#getObject()
	 */
	public Object getObject()
	{
		throw new NetFormException("TableValidate doesn't support yet getObject() method.");
	}
	public boolean hasSelectedItems()
	{
		return ii_selectedItems!=null;
	}
}
