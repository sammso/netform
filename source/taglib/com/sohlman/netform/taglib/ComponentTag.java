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
		
		// TODO:
		// Finish this
		// So it is possible to reference object
		// directly example
		// form.
//		if( (li_pos = iS_Name.indexOf(".",li_pos)) > 0)
//		{
//			i_Stack.clear();
//			while(( li_pos = iS_Name.indexOf(".",li_pos))>0 && li_oldPos < iS_Name.length())
//			{
//				li_pos = li_pos > 0 ? li_pos : iS_Name.length(); 
//				String lS_Name = iS_Name.substring(li_oldPos, li_pos);
//				i_Stack.push(lS_Name);
//				
//				li_oldPos = li_pos;
//			}
//			
//		}
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
				throw new JspException("Component in Table tag is not " + Table.class.getName() + ", but " + l_Component.getClass().getName());
			}
		}
		else
		{
			if(ComponentTag.class.isInstance(l_Tag))
			{
				// Parent is component
				ComponentTag l_ComponentTag = (ComponentTag) l_Tag;

				Component l_Component = l_ComponentTag.getComponentFormThisTag();
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

			Field l_Field = getDeclaredField(l_Object.getClass(), iS_Component);

			if(l_Field != null)
			{
				try
				{
					i_Component = (Component) l_Field.get(i_Form);
				}
				catch (IllegalAccessException l_IllegalAccessException)
				{
					throw new JspException(l_IllegalAccessException);
				}
			}
		}
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
			for(int li_x = 0 ; li_x < l_Fields.length ; li_x++ )
			{
				lSb_Error.append("\t\t");
				lSb_Error.append(l_Fields[li_x].getType().getName());
				lSb_Error.append(" ");
				lSb_Error.append(l_Fields[li_x].getName());
				lSb_Error.append("\n");
			}
			
			throw new JspException("Component named " + aS_Component + " not found from " + a_Class + "\n Following fields found:\n" + lSb_Error.toString(),l_NoSuchFieldException);
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