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
package com.sohlman.netform;

/** This connector to Model in MVC design.
 * 
 * @author Sampsa Sohlman
 * 
 * @version 2004-01-13
 */
public interface ComponentData
{	
	/**
	 * Set Data To DataModel<br>
	 * example {@link com.sohlman.netform.component.table.TableModel TableModel}
	 * @param a_Object to be put to DataModel
	 */
	public void setData(Object a_Object);

	/**
	 * Get Data from DataModel<br>
	 * example {@link com.sohlman.netform.component.table.TableModel TableModel}
	 * @param a_Object Object in DataModel
	 */
	public Object getData();
}
