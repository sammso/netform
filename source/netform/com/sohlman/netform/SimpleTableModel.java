package com.sohlman.netform;

import java.util.ArrayList;

/**
 * @author Sampsa Sohlman
/*
 * Version: 28.10.2003
 *
 */
public class SimpleTableModel extends TableModel
{
	private ArrayList iAL_ArrayList;

	public SimpleTableModel()
	{
		super();
	}
	
	public SimpleTableModel(Object[] a_Objects)
	{
		super();
		for(int li_y = 0 ; li_y < a_Objects.length ; li_y++)
		{
			addValue(a_Objects[li_y]);
		}
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#add()
	 */
	public int add()
	{
		return insert(1);
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#insert(int)
	 */
	public int insert(int ai_before)
	{	
		if(iAL_ArrayList==null)
		{
			iAL_ArrayList = new ArrayList();
		}
		
		if(ai_before < 1 || ( ai_before - 1 ) > iAL_ArrayList.size())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to insert row out of range");
		}
		
		iAL_ArrayList.add(ai_before - 1, null);
		
		return ai_before;
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#delete(int)
	 */
	public int delete(int ai_row)
	{
		if(ai_row < 1 || ( ai_row - 1) >= iAL_ArrayList.size())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to delete row from out of range");
		}
		iAL_ArrayList.remove(ai_row - 1);
		return ai_row;		
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#getRowCount()
	 */
	public int getRowCount()
	{
		if(iAL_ArrayList==null)
		{
			return 0;
		}
		else
		{
			return iAL_ArrayList.size();
		}
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int ai_row, int ai_column)
	{
		if(ai_row < 1 || ( ai_row - 1 ) >= iAL_ArrayList.size())
		{
			throw new ArrayIndexOutOfBoundsException("Tried get from out of row range");
		}	
		if(ai_column!=1)
		{
			throw new ArrayIndexOutOfBoundsException("Tried to get column out of column range. Column range is always 1");
		}		
		return iAL_ArrayList.get(ai_row - 1);
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public boolean setValueAt(Object a_Object, int ai_row, int ai_column)
	{
		if(ai_row < 1 || ( ai_row - 1 ) > iAL_ArrayList.size())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to set value to out of row range");
		}	
		if(ai_column!=1)
		{
			throw new ArrayIndexOutOfBoundsException("Tried to set value to out of column range. Column range is always 1");
		}
		iAL_ArrayList.set(ai_row - 1, a_Object);
		
		return true;
	}
	
	public void addValue(Object a_Object)
	{
		int li_row = add();
		setValueAt(a_Object,li_row, 1);
	}

}
