package com.sohlman.netform.taglib;

import com.sohlman.netform.component.table.Table;

/**
 * @author Sampsa Sohlman
 */
public class TableRow
{
	private int ii_row = 0;
	private Table i_Table;
	
	TableRow(Table a_Table)
	{
		ii_row = 1;
	}
	
	public int getRowNo()
	{
		return ii_row;
	}
	
	int addRow()
	{
		ii_row++;
		return ii_row;
	}
	
	public String getValue(String aS_ColumnName)
	{
		System.out.println(aS_ColumnName);
		return i_Table.getText(ii_row,aS_ColumnName);
	}
}
