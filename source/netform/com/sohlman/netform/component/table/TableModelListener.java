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

/**
 * @author Sampsa Sohlman
 */
public interface TableModelListener
{
	// Action
	public final static int INSERTROW = 1;
	public final static int DELETEROW = 2;
	public final static int ROWMODIFIED = 3;
	public final static int UPDATEALL = 4;
	public final static int COLUMNCHANGED = 5;
	/**
	 * This method is called when TableModel is changed.
	 * 
	 * @param ai_row
	 * @param ai_column
	 * @param ai_action
	 */
	public void tableModelChanged(int ai_row, int ai_column, int ai_action);
}
