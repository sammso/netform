package com.sohlman.netform.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import com.sohlman.netform.component.table.Table;

/**
 * This Tag creates &lt;option&gt; part of &lt;select&gt; tag.
 * 
 * @author Sampsa Sohlman
 */
public class SelectItemTag extends MasterTag
{
	private Table i_Table = null;

	private String iS_Style = null;

	private String iS_Class = null;

	private String iS_Id = null;

	private String iS_ColumnName = null;

	private int ii_column = 1;

	private SelectTag i_SelectTag = null;

	public void setIndex(String aS_Column)
	{
		ii_column = Integer.valueOf(aS_Column).intValue();
		System.out.println(aS_Column);
	}

	public void setName(String aS_Column)
	{
		iS_ColumnName = aS_Column;
	}

	public void setStyle(String aS_Style)
	{
		iS_Style = aS_Style;
	}

	public void setClass(String aS_Class)
	{
		iS_Class = aS_Class;
	}

	public void setId(String aS_Id)
	{
		iS_Id = aS_Id;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			JspWriter l_JspWriter = i_PageContext.getOut();

			l_JspWriter.print("<option value=\"");
			int li_row = i_SelectTag.getCurrentRow();
			l_JspWriter.print(String.valueOf(li_row));
			l_JspWriter.print("\"");

			if(i_Table.isRowSelected(li_row))
			{
				l_JspWriter.print(" selected");
			}

			if(iS_Class != null)
			{
				l_JspWriter.print(" class=\"");
				l_JspWriter.print(iS_Class);
				l_JspWriter.print("\"");
			}
			if(iS_Id != null)
			{
				l_JspWriter.print(" id=\"");
				l_JspWriter.print(iS_Id);
				l_JspWriter.print("\"");
			}
			l_JspWriter.print(">");			
			if(iS_ColumnName != null)
			{
				l_JspWriter.print(i_Table.getText(li_row, iS_ColumnName));
			}
			else
			{
				l_JspWriter.print(i_Table.getText(li_row, ii_column));
			}

			l_JspWriter.print("</option>");
		}
		catch (IOException l_IOException)
		{
			throw new JspException(l_IOException);
		}
		return EVAL_PAGE;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		try
		{
			Tag l_Tag = getParent();
			Object l_Object = null;
			if(SelectTag.class.isInstance(l_Tag))
			{
				i_SelectTag = ((SelectTag) getParent());

				i_Table = (Table) i_SelectTag.getComponentFormThisTag();
				if(i_Table == null)
				{
					i_PageContext.getOut().println(
							"<b>Page design error! Table component is not set for &lt;nf:select&gt; tag</b><br>");
				}
			}
			else
			{
				i_PageContext.getOut().println(
						"<b>Page design error! &lt;nf:select&gt; has to master for &lt;nf:selectitem&gt;</b><br>");
			}
		}
		catch (IOException l_IOException)
		{
			throw new JspException(l_IOException);
		}
		return SKIP_BODY;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		i_Table = null;
		iS_Style = null;
		iS_Class = null;
		iS_Id = null;
		iS_ColumnName = null;
		ii_column = 1;
		i_SelectTag = null;
	}
}