package com.sohlman.netform;

/**
 * @author Sampsa Sohlman
/*
 * Version: 12.8.2003
 *
 */
public class DisplayRow
{
	String[] iS_Columns;
	String iS_ID;
	boolean ib_isSelected;
	Component[] i_TableComponents;
	int ii_index = -1;

	DisplayRow()
	{
		iS_Columns = null;
		ib_isSelected = false;
	}

	DisplayRow(int ai_index, String aS_ID, String[] aS_Values, Component[] a_TableComponentModels, boolean ab_isSelected)
	{
		ii_index = ai_index;
		iS_ID = aS_ID;
		iS_Columns = aS_Values;
		ib_isSelected = ab_isSelected;
		i_TableComponents = a_TableComponentModels;
	}

	public String getRowId()
	{
		return iS_ID;
	}

	public boolean hasComponent(int ai_index)
	{
		return i_TableComponents != null && i_TableComponents[ai_index - 1] != null;
	}

	public Component getComponent(int ai_index)
	{
		if(hasComponent(ai_index))
		{
			return (Component) i_TableComponents[ai_index - 1];	
		}
		else
		{
			return null;
		}
	}

	public String getString(int ai_column)
	{
		if (iS_Columns == null)
		{
			return "";
		}

		if (ai_column < 1 || ai_column > iS_Columns.length)
		{
			throw new ArrayIndexOutOfBoundsException("Column number " + ai_column + " is out of range");
		}
		if (iS_Columns[ai_column - 1] == null)
		{
			return "";
		}
		else
		{
			return iS_Columns[ai_column - 1];
		}

	}

	public String[] getColumns()
	{
		return iS_Columns;
	}

	public boolean isSelected()
	{
		return ib_isSelected;
	}

	public boolean hasData()
	{
		return iS_Columns != null;
	}
}
