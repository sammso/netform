package com.sohlman.netform;

/**
 * @author Sampsa Sohlman
 * 
 * @version Jan 13, 2004
 */
public class TableComponentData implements ComponentData
{
	private int ii_column;
	private int ii_row;
	private TableModel i_TableModel;
	
	//
	// Component which is handling data
	//
	private Component i_Component;
	
	public TableComponentData(TableModel a_TableModel)
	{
		i_TableModel = a_TableModel;
	}
	
	
	/**
	 * @see com.sohlman.netform.ComponentData#setData(java.lang.Object)
	 */
	public void setData(Object a_Object)
	{
		i_TableModel.setValueAt(a_Object, ii_row, ii_column);
	}
	
	public Object getData()
	{
		return i_TableModel.getValueAt(ii_row, ii_column);
	}
	
	public void setColumn(int ai_column)
	{
		ii_column = ai_column;
	}

	public void setRow(int ai_row)
	{
		ii_row = ai_row;
	}

	public int getRow()
	{
		return ii_row;
	}
	
	public int getColumn()
	{
		return ii_column;
	}
}
