package com.sohlman.netform.component.table;

import com.sohlman.netform.Validate;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-04-20
 */
public class TableValidate extends Validate
{
	private int[] ii_selectedItems;
	
	public TableValidate(Table a_Table, int[] ai_selectedItems)
	{
		i_Component = a_Table;
		ii_selectedItems = ai_selectedItems;
	}
	
	public int[] getSelectedItems()
	{
		return ii_selectedItems;
	}
	
	public boolean hasSelectedItems()
	{
		return ii_selectedItems!=null;
	}
}
