package com.sohlman.netform.component.table;

import java.util.ArrayList;

/**
 * @author Sampsa Sohlman
/*
 * Version: 2003-11-20
 *
 */
public class SimpleTableModel extends TableModel
{
	private ArrayList iAL_ArrayList;

	public SimpleTableModel()
	{
		super(); 
	}
	
	
	/**
	 * Contruct tableModel from array of objects
	 * 
	 * @param a_Objects
	 */
	public SimpleTableModel(Object[] a_Objects)
	{
		super();
		if(a_Objects!=null)
		{
			iAL_ArrayList = new ArrayList();
			for(int li_y = 0 ; li_y < a_Objects.length ; li_y++)
			{
				iAL_ArrayList.add(a_Objects[li_y]);
			}
		}
	}
	
	/**
	 * Possiblity to reset all object from Array of Objects
	 * 
	 * @param a_Objects
	 */
	public void resetAll(Object[] a_Objects)
	{
		if(a_Objects!=null)
		{
			iAL_ArrayList = new ArrayList();
			for(int li_y = 0 ; li_y < a_Objects.length ; li_y++)
			{
				iAL_ArrayList.add(a_Objects[li_y]);
			}
			fireUpdateAll();
		}
	}
	
	/**
	 * @see com.sohlman.netform.TableModel#add()
	 */
	public int add()
	{
		return insert(getRowCount() + 1);
	}

	/** (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#insert(int)
	 */
	public int insert(int ai_before)
	{	
		if(iAL_ArrayList==null)
		{
			iAL_ArrayList = new ArrayList();
		}
		
		if(ai_before < 1 || ai_before  > ( iAL_ArrayList.size() + 1) )
		{
			throw new ArrayIndexOutOfBoundsException("Tried to insert row out of range");
		}
		iAL_ArrayList.add(ai_before - 1, null);
		fireInsert(ai_before);
		return ai_before;
	}

	/**
	 * @see com.sohlman.netform.TableModel#delete(int)
	 */
	public int delete(int ai_row)
	{
		if(ai_row < 1 || ( ai_row - 1) >= iAL_ArrayList.size())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to delete row from out of range");
		}
		iAL_ArrayList.remove(ai_row - 1);
		fireDelete(ai_row);
		return ai_row;		
	}

	/**
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

	/**
	 * @see com.sohlman.netform.TableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return 1;
	}

	/**
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

	/**
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
		
		fireColumnChanged(ai_row, ai_column);
		return true;
	}
	
	/**
	 * Adds object end of list in SimpleTableModel
	 * 
	 * @param a_Object to put end of List
	 */
	public void addValue(Object a_Object)
	{
		int li_row = add();
		setValueAt(a_Object,li_row, 1);
	}
	/**
	 * All object in array
	 *  
	 * @return Array of objects null if none
	 */
	public Object[] toArray()
	{
		if(iAL_ArrayList!=null)
		{
			return iAL_ArrayList.toArray();
		}
		else
		{
			return null;
		}	
	}

	/**
	 * @see com.sohlman.netform.TableModel#getColumnName(int)
	 */
	public String getColumnName(int ai_index)
	{
		if(ai_index!=1)
		{
			throw new ArrayIndexOutOfBoundsException("Tried to getColumnName to out of column range. Column range is always 1");
		}		
		return "";
	}

	/**
	 * @see com.sohlman.netform.TableModel#search(java.lang.Object, int)
	 */
	public int search(Object a_Object, int ai_column)
	{
		if(iAL_ArrayList!=null)
		{
			int li_row = iAL_ArrayList.indexOf(a_Object);
			if(li_row>=0)
			{
				return li_row + 1;
			}
			else
			{
				return -1;
			}
		}
		else
		{
			return -1;
		}
	}


	/**
	 * @return Always 1
	 * 
	 * @see com.sohlman.netform.TableModel#getIndexByName(java.lang.String)
	 */
	public int getIndexByName(String aS_Name)
	{
		return 1;
	}
}