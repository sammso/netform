package com.sohlman.dataset.netform;

import com.sohlman.netform.TableModel;
import com.sohlman.dataset.DataSet;
import com.sohlman.dataset.DataSetEvent;
import com.sohlman.dataset.DataSetListener;

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
				case DataSetEvent.COLUMN_CHANGED :
					fireUpdate(a_DataSetEvent.getRow(), a_DataSetEvent.getColumn());
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


	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#add()
	 */
	public int add()
	{
		return i_DataSet.addRow();
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#insert(int)
	 */
	public int insert(int ai_before)
	{
		return i_DataSet.insertRow(ai_before);
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#delete(int)
	 */
	public int delete(int ai_row)
	{
		return i_DataSet.removeRow(ai_row);
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#getRowCount()
	 */
	public int getRowCount()
	{
		return i_DataSet.getRowCount() ;
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return i_DataSet.getColumnCount();
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int ai_row, int ai_column)
	{
		return i_DataSet.getValueAt(ai_row, ai_column);
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public boolean setValueAt(Object a_Object, int ai_row, int ai_column)
	{
		return i_DataSet.setValueAt(a_Object, ai_row, ai_column);
	}

}
