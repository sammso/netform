package com.sohlman.netform;
import java.util.List;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-03-01
 */
public abstract class ListTableModel extends TableModel
{
	private List i_List = null;
	
	public void setList(List a_List)
	{
		i_List = a_List;
	}
	
	/**
	 * @see com.sohlman.netform.TableModel#add()
	 */
	public int add()
	{
		return insert(getRowCount() + 1);
	}

	/**
	 * @see com.sohlman.netform.TableModel#insert(int)
	 */
	public int insert(int ai_before)
	{
		if(i_List==null)
		{
			throw new IllegalStateException("List is not defined");
		}
		
		if(ai_before < 1 || ai_before  > ( i_List.size() + 1) )
		{
			throw new ArrayIndexOutOfBoundsException("Tried to insert row out of range");
		}
		i_List.add(ai_before - 1, null);
		fireInsert(ai_before);
		return ai_before;
	}
	
	public abstract Object createRow();

	/**
	 * @see com.sohlman.netform.TableModel#delete(int)
	 */
	public int delete(int ai_row)
	{
		if(ai_row < 1 || ( ai_row - 1) >= i_List.size())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to delete row from out of range");
		}
		i_List.remove(ai_row - 1);
		fireDelete(ai_row);
		return ai_row;	
	}

	/**
	 * @see com.sohlman.netform.TableModel#getRowCount()
	 */
	public int getRowCount()
	{
		if(i_List==null)
		{
			return 0;
		}
		else
		{
			return i_List.size();
		}
	}

	/**
	 * @see com.sohlman.netform.TableModel#getColumnCount()
	 */
	public abstract int getColumnCount();

	/**
	 * @see com.sohlman.netform.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int ai_row, int ai_column)
	{
		if(ai_row < 1 || ( ai_row - 1 ) >= i_List.size())
		{
			throw new ArrayIndexOutOfBoundsException("Tried get from out of row range");
		}	
		if(ai_column < 1 && ai_column > getColumnCount() )
		{
			throw new ArrayIndexOutOfBoundsException("Tried to get column out of column range. Column range is always 1");
		}		
		return mapObjectFromRow(i_List.get(ai_row - 1), ai_column);
	}

	protected abstract Object mapObjectFromRow(Object aO_Row, int ai_columnIndex);

	/**
	 * @see com.sohlman.netform.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public boolean setValueAt(Object a_Object, int ai_row, int ai_column)
	{
		if(ai_row < 1 || ( ai_row - 1 ) > i_List.size())
		{
			throw new ArrayIndexOutOfBoundsException("Tried to set value to out of row range");
		}	
		if(ai_column!=1)
		{
			throw new ArrayIndexOutOfBoundsException("Tried to set value to out of column range. Column range is always 1");
		}
		Object lO_Row = i_List.get(ai_row - 1);
		mapObjectToRow(a_Object, lO_Row, ai_column);
		
		fireColumnChanged(ai_row, ai_column);
		return true;
	}
	
	protected abstract void mapObjectToRow(Object a_Object, Object aO_Row, int ai_columnIndex);

	/**
	 * @see com.sohlman.netform.TableModel#getColumnName(int)
	 */
	public abstract String getColumnName(int ai_index);

	/**
	 * @see com.sohlman.netform.TableModel#search(java.lang.Object, int)
	 */
	public int search(Object a_Object, int ai_column)
	{
		if(i_List!=null)
		{
			for(int li_index = 0 ; li_index <= i_List.size() ; li_index++)
			{
				Object l_Object = getValueAt(li_index, ai_column);
				if(( l_Object == a_Object ) || (l_Object!=null && a_Object!=null && a_Object.equals(a_Object)))
				{
					return li_index + 1;
				}
			}
			return -1;
		}
		else
		{
			return -1;
		}
	}

	/**
	 * @see com.sohlman.netform.TableModel#getIndexByName(java.lang.String)
	 */
	public abstract int getIndexByName(String aS_Name);

}
