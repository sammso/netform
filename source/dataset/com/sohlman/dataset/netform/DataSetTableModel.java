package com.sohlman.dataset.netform;

import com.sohlman.dataset.DataSet;
import com.sohlman.dataset.DataSetEvent;
import com.sohlman.dataset.DataSetException;
import com.sohlman.dataset.DataSetListener;
import com.sohlman.netform.NetFormException;
import com.sohlman.netform.component.table.TableModel;

/**
 * @author Sampsa Sohlman
 * 
 * @version Feb 23, 2004
 */
/**
 * @author Sampsa Sohlman
/*
 * Version: 26.8.2003
 *
 */
public class DataSetTableModel extends TableModel
{
	protected DataSet i_DataSet; 
	
	private DataSetListener i_DataSetListener = new DataSetListener()
	{
		public void dataSetChanged(DataSetEvent a_DataSetEvent)
		{
			switch(a_DataSetEvent.getAction())
			{
				case DataSetEvent.ROW_INSERTED:
					fireInsert(a_DataSetEvent.getRow());
					break;
				case DataSetEvent.COLUMN_CHANGED:
					fireColumnChanged(a_DataSetEvent.getRow(), a_DataSetEvent.getColumn());
					break;
				case DataSetEvent.ROW_REMOVED :
					fireDelete(a_DataSetEvent.getRow());
					break;
				case DataSetEvent.READ_END :
				case DataSetEvent.RESET :
					fireUpdateAll();					
			}
		}
	};
	
	public DataSetTableModel(DataSet a_DataSet)
	{
		i_DataSet = a_DataSet;
		i_DataSet.addListener(i_DataSetListener);
	}


	/**
	 * @see com.sohlman.netform.TableModel#add()
	 */
	public int add()
	{
		return i_DataSet.addRow();
	}

	/**
	 * @see com.sohlman.netform.TableModel#insert(int)
	 */
	public int insert(int ai_before)
	{
		return i_DataSet.insertRow(ai_before);
	}

	/**
	 * @see com.sohlman.netform.TableModel#delete(int)
	 */
	public int delete(int ai_row)
	{
		return i_DataSet.removeRow(ai_row);
	}

	/**
	 * @see com.sohlman.netform.TableModel#getRowCount()
	 */
	public int getRowCount()
	{
		return i_DataSet.getRowCount() ;
	}

	/**
	 * @see com.sohlman.netform.TableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return i_DataSet.getColumnCount();
	}

	/**
	 * @see com.sohlman.netform.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int ai_row, int ai_column)
	{
		return i_DataSet.getValueAt(ai_row, ai_column);
	}

	/**
	 * @see com.sohlman.netform.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public boolean setValueAt(Object a_Object, int ai_row, int ai_column)
	{
		return i_DataSet.setValueAt(a_Object, ai_row, ai_column);
	}

	/**
	 * @see com.sohlman.netform.TableModel#getColumnName(int)
	 */
	public String getColumnName(int ai_index)
	{
		return i_DataSet.getRowInfo().getColumnName(ai_index);
	}
	
	

	/**
	 * @see com.sohlman.netform.TableModel#search(java.lang.Object, int)
	 */
	public int search(Object a_Object, int ai_column)
	{
		int li_row = i_DataSet.search(a_Object, ai_column);
		
		if(li_row == 0)
		{
			return -1;
		}
		else
		{
			return li_row;	
		}
	}

	/**
	 * This is overriden
	 * returns if row is new.
	 */
	public boolean isNew(int ai_index)
	{
		return i_DataSet.isNew(ai_index);
	}


	/**
	 * @see com.sohlman.netform.TableModel#getIndexByName(java.lang.String)
	 */
	public int getIndexByName(String aS_Name)
	{
		try
		{
			return i_DataSet.getRowInfo().getIndexByColumnName(aS_Name);
		}
		catch(DataSetException l_DataSetException)
		{
			throw new NetFormException(aS_Name + " not found");
		}
	}

}
