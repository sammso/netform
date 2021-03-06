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
package com.sohlman.netform.taglib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.component.table.Table;

/**
 * @author Sampsa Sohlman
 */
public abstract class ComponentTag extends MasterTag
{
	private Component i_Component = null;

	private Form i_Form = null;

	private String iS_Component = null;

	private String iS_Name = null;

	private int ii_index = 0;

	// This is used on init() method
	// but it is
	private Stack i_Stack = new Stack();

	public void setComponent(String aS_Component)
	{
		iS_Component = aS_Component;
	}

	/**
	 * This is column index for table
	 * 
	 * @param aS_Index
	 */
	public void setIndex(String aS_Index)
	{
		ii_index = Integer.valueOf(aS_Index).intValue();
	}

	/**
	 * This is a column name for Table
	 * 
	 * @param aS_Name
	 */
	public void setName(String aS_Name)
	{
		iS_Name = aS_Name;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public abstract int doStartTag() throws JspException;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public abstract int doEndTag() throws JspException;

	/**
	 * 
	 * get Component from name
	 * 
	 * @throws JspException
	 */
	protected void init() throws JspException
	{
		Tag l_Tag = getParent();
		String lS_ComponentName = iS_Name;
		Object l_Object = null;
		int li_pos = 0;
		int li_oldPos = 0;
		if(TableTag.class.isInstance(l_Tag))
		{
			TableTag l_TableTag = (TableTag) l_Tag;
			Component l_Component = l_TableTag.getComponentFormThisTag();
			if(Table.class.isInstance(l_Component))
			{
				Table l_Table = (Table) l_Component;
				int li_row = l_TableTag.getCurrentRow();
				if(iS_Name != null)
				{
					i_Component = l_Table.getComponentAt(li_row, iS_Name);
				}
				else
				{
					i_Component = l_Table.getComponentAt(li_row, ii_index);
				}
			}
			else
			{
				throw new JspException("Component in Table tag is not " + Table.class.getName() + ", but "
						+ l_Component.getClass().getName());
			}
		}
		else
		{
			if(ComponentTag.class.isInstance(l_Tag))
			{
				// Parent is component
				ComponentTag l_ComponentTag = (ComponentTag) l_Tag;

				Component l_Component = l_ComponentTag.getComponentFormThisTag();
				
				l_Object = l_Component;
			}
			else
			{
				i_Form = (Form) i_PageContext.getRequest().getAttribute(MasterTag.FORM);

				if(i_Form == null)
				{
					throw new JspException("Form not found");
				}

				l_Object = i_Form;
			}

			i_Component = getComponent(l_Object.getClass(), l_Object , iS_Component);

		}
	}

	private Component getComponent(Class a_Class, Object a_Object, String aS_Component) throws JspException
	{
		Method l_Method = getComponentMethod(a_Class, aS_Component);

		if(l_Method != null)
		{
			try
			{
				return (Component) l_Method.invoke(a_Object, null);
			}
			catch (IllegalAccessException l_IllegalAccessException)
			{
				throw new JspException(l_IllegalAccessException);
			}
			catch (InvocationTargetException l_InvocationTargetException)
			{
				throw new JspException(l_InvocationTargetException);
			}
		}
		else
		{
			Field l_Field = getDeclaredField(a_Class, iS_Component);

			try
			{
				return (Component) l_Field.get(a_Object);
			}
			catch (IllegalAccessException l_IllegalAccessException)
			{
				throw new JspException(l_IllegalAccessException);
			}
		}
	}

	private Method getComponentMethod(Class a_Class, String aS_Component) throws JspException
	{
		Method[] l_Methods = a_Class.getMethods();

		String lS_MethodName = "get" + aS_Component;

		for (int li_index = 0; li_index < l_Methods.length; li_index++)
		{
			Method l_Method = l_Methods[li_index];
			if(lS_MethodName.equalsIgnoreCase(l_Method.getName()) && l_Method.getParameterTypes().length == 0
					&& Component.class.isAssignableFrom(l_Method.getReturnType()))
			{
				return l_Method;
			}
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @param a_Class
	 * @param aS_Component
	 * @return
	 */
	private Field getDeclaredField(Class a_Class, String aS_Component) throws JspException
	{

		try
		{
			Field l_Field = a_Class.getField(aS_Component);
			return l_Field;
		}
		catch (NoSuchFieldException l_NoSuchFieldException)
		{
			Field[] l_Fields = a_Class.getFields();

			StringBuffer lSb_Error = new StringBuffer();
			lSb_Error.append("Fields: \n");
			for (int li_x = 0; li_x < l_Fields.length; li_x++)
			{
				lSb_Error.append("\t\t");
				lSb_Error.append(l_Fields[li_x].getType().getName());
				lSb_Error.append(" ");
				lSb_Error.append(l_Fields[li_x].getName());
				lSb_Error.append("\n");
			}
			lSb_Error.append("Methods: \n");
			Method[] l_Methods = a_Class.getMethods();
			for (int li_index = 0; li_index < l_Methods.length; li_index++)
			{
				Method l_Method = l_Methods[li_index];
				lSb_Error.append("\t\t");
				lSb_Error.append(l_Methods[li_index].getReturnType().getName());
				lSb_Error.append(" ");
				lSb_Error.append(l_Methods[li_index].getName());
				lSb_Error.append("(");
				Class[] l_Classes = l_Method.getParameterTypes();
				for(int li_y = 0 ; li_y < l_Classes.length ; li_y++ )
				{
					if(li_y > 0 )lSb_Error.append(", ");
					lSb_Error.append(l_Classes[li_y].getName());
				}
				lSb_Error.append(")\n");				
			}			

			throw new JspException("Component named " + aS_Component + " not found from " + a_Class
					+ "\n Following fields found:\n" + lSb_Error.toString(), l_NoSuchFieldException);
		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		i_Component = null;
		iS_Component = null;
		i_Form = null;
	}

	/**
	 * @return Component
	 */
	final Component getComponentFormThisTag()
	{
		return i_Component;
	}
}