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
package com.sohlman.netform.taglib;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

	protected boolean init() throws JspException
	{
		Tag l_Tag = getParent();
		Object l_Object = null;

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
					// TODO: Fix the li_index to column name
					i_Component = l_Table.getComponentAt(li_row, ii_index);
				}
				else
				{
					i_Component = l_Table.getComponentAt(li_row, ii_index);
				}
				return true;
			}
			return false;
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
					try
					{
						i_PageContext.getOut().println("<b>Error : &lt;nf:init&gt; tag has to be executed before</b>");
						return false;
					}
					catch (IOException l_IOException)
					{
						throw new JspException(l_IOException);
					}
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
					return false;
				}
			}
			return true;
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