package com.sohlman.netform.component.table;

import java.lang.reflect.Method;

import com.sohlman.netform.NetFormException;

/**
 * TableModel for List which contains Objects
 * 
 * <b>Usage:</b>
 * <code>
 * l_ObjectListTableModel.setListItemClass(YourListItem.class);
 * l_ObjectListTableModel.setMethods(1, "setName", "getName");
 * l_ObjectListTableModel.setMethods(1, "setAge", "getAge");
 * l_ObjectListTableModel.setColummnCount(.class)
 * </code>
 * 
 * @author Sampsa Sohlman
 */
public class ObjectListTableModel extends ListTableModel
{
	private Method[] i_Method_setMethods;

	private Method[] i_Method_getMethods;

	private Class i_Class_ListItem;

	public void setListItemClass(Class a_Class)
	{
		i_Class_ListItem = a_Class;
	}

	public void setColummnCount(int ai_count)
	{
		i_Method_getMethods = new Method[ai_count];
		i_Method_setMethods = new Method[ai_count];
	}

	public void setMethods(int ai_index, String aS_SetMethod, String aS_GetMethod)
	{
		if(i_Class_ListItem == null)
		{
			throw new IllegalStateException(
					"No class set. Before using setMethods(int,String, String) use setListItemClass(Class) and setColummnCount(int) ");
		}
		if(i_Method_getMethods.length <= 0 || i_Method_setMethods.length <= 0)
		{
			throw new IllegalStateException(
					"No column count set. Before using setMethods(int,String, String) use setListItemClass(Class) and setColummnCount(int) ");
		}

		Method[] l_Methods = i_Class_ListItem.getMethods();
		
		for(int li_index = 0 ; li_index < l_Methods.length ; li_index++ )
		{
			if(l_Methods[li_index].getName().equals(aS_SetMethod))
			{
				i_Method_setMethods[ai_index - 1] = l_Methods[li_index];
			}
			else if(l_Methods[li_index].getName().equals(aS_GetMethod))
			{
				i_Method_getMethods[ai_index - 1] = l_Methods[li_index];
			}
		}
	}

	/**
	 * @see com.sohlman.netform.component.table.ListTableModel#mapObjectFromRow(java.lang.Object,
	 *      int)
	 */
	protected Object mapObjectFromRow(Object aO_row, int ai_columnIndex)
	{	
		try 
		{
			return i_Method_getMethods[ai_columnIndex].invoke(aO_row, null);
		}
		catch(Exception l_Exception)
		{
			throw new NetFormException(l_Exception);
		}
	}
	
	
	/**
	 * @see com.sohlman.netform.component.table.ListTableModel#mapObjectToRow(java.lang.Object, java.lang.Object, int)
	 */
	protected void mapObjectToRow(Object a_Object, Object aO_row, int ai_columnIndex)
	{
		try 
		{
			i_Method_getMethods[ai_columnIndex].invoke(aO_row, new Object[] {a_Object});
		}
		catch(Exception l_Exception)
		{
			throw new NetFormException(l_Exception);
		}		
	}
}