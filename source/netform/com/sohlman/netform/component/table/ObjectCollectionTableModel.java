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

import java.lang.reflect.Method;

import com.sohlman.netform.NetFormException;

/**
 * @author Sampsa Sohlman
 */
public class ObjectCollectionTableModel extends CollectionTableModel
{
	private Method i_Method_SetParent = null;
	
	private Method i_Method_Delete = null;
	
	private Method[] i_Method_setMethods;
	private Method[] i_Method_getMethods;
	
	private Class i_Class_CollectionItem;
	private Object iO_Parent = null;
	
	/**
	 * @param a_Class
	 */
	public void setCollectoinItemClass(Class a_Class)
	{
		i_Class_CollectionItem = a_Class;
	}

	/**
	 * @param ai_count
	 */
	public void setColummnCount(int ai_count)
	{
		i_Method_getMethods = new Method[ai_count];
		i_Method_setMethods = new Method[ai_count];
	}

	/**
	 * To set parent object for collection item. This is required if 
	 * two way link is required for parent and child relation ship.
	 * 
	 * @param aO_Parent parent object
	 */
	public void setParentObjectForCollectionItem(Object aO_Parent)
	{
		iO_Parent = aO_Parent;
	}
	
	/**
	 * Add method can 
	 * 
	 * @param aO_Create
	 * @param aS_MethodName
	 */
	public void assignSetParentMethodForItem(String aS_MethodName)
	{
		if(i_Class_CollectionItem==null)
		{
			throw new IllegalStateException("setCollectoinItemClass has to be called before assignSetParentMethodForItem");
		}
		
		if(iO_Parent==null)
		{
			throw new IllegalStateException("setParentObjectForCollectionItem has to be called before assignSetParentMethodForItem");
		}
		
		try
		{
			i_Method_SetParent = i_Class_CollectionItem.getMethod(aS_MethodName, new Class[] { iO_Parent.getClass() });
			
		}
		catch(NoSuchMethodException l_NoSuchMethodException)
		{
			Method[] l_Methods = i_Class_CollectionItem.getMethods();
			StringBuffer lSb_Methods = new StringBuffer();
			for(int li_x = 0 ; li_x < l_Methods.length ; li_x++ )
			{
				
				lSb_Methods.append("\t\t" + l_Methods[li_x].getReturnType().getName() + " ");
				lSb_Methods.append(l_Methods[li_x].getName() + "(");
				Class[] l_Classes = l_Methods[li_x].getParameterTypes();
				for(int li_y = 0; li_y < l_Classes.length ; li_y++)
				{
					lSb_Methods.append( ( li_y==0 ? "" : "," ) + l_Classes[li_y].getName());
				}
				lSb_Methods.append(")\n");
			}
			
			throw new NoSuchMethodError(aS_MethodName + "(" + iO_Parent.getClass().getName() + ")\n Following methods found for class " + i_Class_CollectionItem.getName() +  " :\n" + lSb_Methods);
		}
	}
	
	/**
	 * @param ai_index
	 * @param aS_Name
	 */
	public void assignCollectionColumn(String aS_Name, int ai_index )
	{
		if(i_Class_CollectionItem == null)
		{
			throw new IllegalStateException(
					"No class set. Before using setMethods(int,String, String) use setListItemClass(Class) and setColummnCount(int) ");
		}
		
		if(i_Method_getMethods == null || i_Method_setMethods == null)
		{
			throw new IllegalStateException(
					"No column count set. Before using setMethods(int,String, String) use setColummnCount(int) ");
		}
		
		String lS_SetMethod = "set" + aS_Name;
		String lS_GetMethod = "get" + aS_Name;
		setColumnName(aS_Name, ai_index);

		Method[] l_Methods = i_Class_CollectionItem.getMethods();
		
		for(int li_index = 0 ; li_index < l_Methods.length ; li_index++ )
		{
			if(l_Methods[li_index].getName().equalsIgnoreCase(lS_SetMethod))
			{
				i_Method_setMethods[ai_index - 1] = l_Methods[li_index];
			}
			else if(l_Methods[li_index].getName().equalsIgnoreCase(lS_GetMethod))
			{
				i_Method_getMethods[ai_index - 1] = l_Methods[li_index];
			}
		}
	}	
	
	/**
	 * @see com.sohlman.netform.component.table.CollectionTableModel#createRow()
	 */
	public Object createRow()
	{
		try
		{
			Object l_Object = i_Class_CollectionItem.newInstance();
			
			if(i_Method_SetParent!=null)
			{
				i_Method_SetParent.invoke(l_Object, new Object[] { iO_Parent });
			}
			
			return l_Object;
		}
		catch(Exception l_Exception)
		{
			throw new NetFormException("Couln't setParent() on createRow() for " + iO_Parent.getClass().getName(), l_Exception);
		}	
	}
	
	/**
	 * @see com.sohlman.netform.component.table.CollectionTableModel#mapObjectFromRow(java.lang.Object, int)
	 */
	protected Object mapObjectFromRow(Object aO_row, int ai_columnIndex)
	{	
		try 
		{
			return i_Method_getMethods[ai_columnIndex - 1].invoke(aO_row, null);
		}
		catch(Exception l_Exception)
		{
			throw new NetFormException(l_Exception);
		}
	}
	
	/**
	 * @see com.sohlman.netform.component.table.CollectionTableModel#mapObjectToRow(java.lang.Object, java.lang.Object, int)
	 */
	protected void mapObjectToRow(Object a_Object, Object aO_row, int ai_columnIndex)
	{
		try 
		{
			i_Method_setMethods[ai_columnIndex - 1].invoke(aO_row, new Object[] {a_Object});
		}
		catch(Exception l_Exception)
		{
			throw new NetFormException(l_Exception);
		}		
	}
	
	
	/**
	 * @see com.sohlman.netform.component.table.CollectionTableModel#rowDeleted(int, java.lang.Object)
	 */
	public void rowDeleted(int ai_row, Object aO_Row)
	{
		if(i_Method_SetParent!=null)
		{
			try
			{
				i_Method_SetParent.invoke(aO_Row, new Object[] { null });
			}
			catch(Exception l_Exception)
			{
				throw new NetFormException("Couln't setParent to null for " + iO_Parent.getClass().getName(), l_Exception);
			}
		}
	}
	
	/**
	 * @see com.sohlman.netform.component.table.TableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		if(i_Method_getMethods==null)
		{
			throw new NetFormException("Couln't getColumnCount() because setColumnCount() is not called");
		}
		return i_Method_getMethods.length;
	}
}
