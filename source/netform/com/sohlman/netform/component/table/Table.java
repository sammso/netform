/*
NetForm Library
---------------
Copyright (C) 2001-2005 - Sampsa Sohlman, Teemu Sohlman

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
*/
package com.sohlman.netform.component.table;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentData;
import com.sohlman.netform.ComponentDataException;
import com.sohlman.netform.Form;
import com.sohlman.netform.NetFormException;
import com.sohlman.netform.Utils;
import com.sohlman.netform.component.TextField;

/**
 * High level component for handling table form data
 * 
 * @version 2003-03-10
 * @author Sampsa Sohlman 
 */

public class Table extends Component
{
	// Selected
	private static final int NOTFOUND = -1;
	private static final int PAGESIZE_NOT_DEFINED = -1;
	//private int[] ii_currentSelection = null;
	//private int[] ii_oldSelection = null;
	private IntArray i_IntArray_Selection = new IntArray();

	private boolean ib_multiSelection = false;
	private boolean ib_multiPageSelection = false;
	private TableModel i_TableModel;
	private int ii_displayCount = -1;
	private int ii_pageSize = -1;
	private int ii_displayStart = 1;
	private int ii_rowCount = 0;
	private boolean ib_pageSizeFixed = false;
	//private boolean ib_generateRowLevel = false;
	private HashSet iHs_Components = new HashSet(100);
	//private Vector iVe_NotRowComponents;
	private int ii_dataColumn = -1;

	private int ii_visibleRows = 0; // 0 all rows are visible

	/**
	 * @param a_Component_Parent
	 * @param ai_dataColumn to where parent component can optain data
	 */
	public Table(Component a_Component_Parent, int ai_dataColumn)
	{
		super(a_Component_Parent);
		setDataColumn(ai_dataColumn);
	}

	public Table(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		setDataColumn(1);
	}

	/**
	 * @param a_Form
	 */
	public Table(Form a_Form)
	{
		super(a_Form);
	}

	/**
	 * @param a_Component_Parent
	 * @param a_TableModel
	 */
	public Table(Component a_Component_Parent, TableModel a_TableModel)
	{
		super(a_Component_Parent);
		i_TableModel = a_TableModel;
		i_TableModel.setTable(this);
	}

	/**
	 * @param a_Component_Parent
	 * @param a_TableModel
	 * @param ai_dataColumn
	 */
	public Table(Component a_Component_Parent, TableModel a_TableModel, int ai_dataColumn)
	{
		super(a_Component_Parent);
		setTableModel(a_TableModel);
		setDataColumn(ai_dataColumn);
	}

	/**
	 * @param a_Form
	 * @param a_TableModel
	 */
	public Table(Form a_Form, TableModel a_TableModel)
	{
		super(a_Form);
		setTableModel(a_TableModel);
	}

	/**
	 * Adds {@link TableModel TableModel} to Table
	 * <p>
	 * NOTE this is only allowed call once per Table 
	 * 
	 * @param a_TableModel TableModel
	 */
	public void setTableModel(TableModel a_TableModel)
	{
		if (i_TableModel != null)
		{
			throw new IllegalStateException("Table model already defined. It is not allowed to reset table model");
		}

		i_TableModel = a_TableModel;
		i_TableModel.setTable(this);
	}

	/**
	 * Tells if row is selected or not
	 * 
	 * This is not ment to be used on JSP page.
	 * 
	 * @param ai_index row number to checked
	 * @return row number
	 */
	public boolean isSelected(int ai_index)
	{
		i_IntArray_Selection.sort();
		return i_IntArray_Selection.hasValue(ai_index);
	}

	/**
	 * @param ai_after 
	 * user 0 if you want to first
	 * @return first selected row after parameter 
	 * 0 if not found
	 */
	public int getSelectedRow(int ai_after)
	{
		int[] li_currentSelection;

		i_IntArray_Selection.sort();
		li_currentSelection = i_IntArray_Selection.toArray();
		if (li_currentSelection != null)
		{
			for (int li_c = 0; li_c < li_currentSelection.length; li_c++)
			{
				if (li_currentSelection[li_c] > ai_after)
				{
					return li_currentSelection[li_c];
				}
			}
		}

		return 0;
	}

	/**
	 * @return index of first selected row 0 if now row selected
	 */
	public int getSelectedRow()
	{
		return getSelectedRow(0);
	}

	/**
	 * Sorted array of indexes of selected rows
	 * 
	 * @return array of selected rows, null without selection
	 */
	public int[] getSelectedItems()
	{
		if (!i_IntArray_Selection.isEmpty())
		{
			i_IntArray_Selection.sort();
			return i_IntArray_Selection.toArray();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Insert row before first selected row. If no row selected no rows inserted.
	 * @return inserted row number
	 */
	public int insertRowBeforeFirstSelection()
	{
		int li_row = getSelectedRow(0);
		if (li_row > 0)
		{
			return i_TableModel.insert(li_row);
		}
		return -1;

	}
	/**
	 * Wrapper for
	 * @see com.sohlman.netform.component.table.TableModel#insert(int)
	 */
	public int insertRow(int ai_before)
	{
		return i_TableModel.insert(ai_before);
	}

	/**
	 * Wrapper for
	 * @see com.sohlman.netform.component.table.TableModel#add()
	 */
	public int addRow()
	{
		return i_TableModel.add();
	}

	/**
	 * Wrapper for
	 * @see com.sohlman.netform.component.table.TableModel#delete(int)
	 */
	public int deleteRow(int ai_index)
	{
		return i_TableModel.delete(ai_index);
	}

	/**
	 * Clear the selection
	 */
	public void clearSelection()
	{
		i_IntArray_Selection.clear();
	}

	/**
	 * Table model
	 * 
	 * @return assosiated TableModel
	 */
	public TableModel getTableModel()
	{
		return i_TableModel;
	}

	/**
	 * Get visible page number
	 * @return page number
	 */
	public int getPage()
	{
		if(ii_displayCount<=0)
		{
			return 1;
		}
		else
		{
			return ((ii_displayStart - 1) / ii_displayCount) + 1;
		}
	}

	/**
	 * How many rows are selected
	 * 
	 * @return count of selected rows
	 */
	public int getSelectedCount()
	{
		return i_IntArray_Selection.size();
	}
	
	/**
	 * @param a_Object Object to be set at column
	 * @param ai_row
	 * @param ai_column
	 * @return 
	 */
	public boolean setValueAt(Object a_Object, int ai_row, int ai_column)
	{
		try
		{
			return i_TableModel.setValueAt(a_Object, ai_row, ai_column);
		}
		catch(ComponentDataException l_ComponentDataException)
		{
			return false;
		}
	}

	/**
	 * @param ai_index page index to be changed
	 * <b>Form State TEMPLATING</b>
	 */
	public void setPage(int ai_index)
	{
		//IDEA
		//checkFormState(Form.FORM_STATE_SETTINGS, "Table.setPage");

		if (ii_pageSize == -1)
		{
			return;
		}

		if (ai_index < 1)
		{
			ai_index = 1;
		}

		if (ai_index > getPageCount())
		{
			ai_index = getPageCount();
		}

		ai_index--;

		ii_displayStart = 1 + ii_pageSize * ai_index;
	}

	/**
	 * Tells if it is possible to selected multiple rows at once
	 * 
	 * @return true if yes false if not
	 */

	public boolean isMultiSelection()
	{
		return ib_multiSelection;
	}

	/**
	 * Change multiselection status
	 * Default false
	 * 
	 * @param ab_multiSelection true if it is on false if not
	 */
	public void setMultiSelection(boolean ab_multiSelection)
	{
		ib_multiSelection = ab_multiSelection;
	}

	/**
	 * Is this last page
	 * 
	 * @return true if it is last page and false if not
	 */
	public boolean isLastPage()
	{
		return getPage() == getPageCount();
	}

	/**
	 * Is this first page
	 * 
	 * @return true if it is and false if not
	 */
	public boolean isFirstPage()
	{
		return getPage() == 1;
	}

	/**
	 * Retuns current page count. If 
	 * 
	 * @return current page count. 
	 */
	public int getPageCount()
	{
		if (ii_displayCount == -1)
		{
			return 1;
		}
		int li_rows = i_TableModel.getRowCount();

		if (li_rows <= 0)
		{
			return 1;
		}

		int li_return = (li_rows / ii_displayCount);
		if ((li_rows % ii_displayCount) != 0)
		{
			li_return++;
		}
		return li_return;
	}

	/**
	 * Set page size
	 * 
	 * @param ai_count how many rows on one page
	 */
	public void setPageSize(int ai_count)
	{
		ii_pageSize = ai_count;
	}

	/**
	 * Retuns page size
	 * 
	 * @return page size
	 */
	public int getPageSize()
	{
		return ii_displayCount;
	}

	/**
	 * Select's row
	 * 
	 * @param ai_index Index of selected row
	 */
	public boolean selectItem(int ai_index)
	{
		return selectItems(new int[] { ai_index });
	}

	/**
	 * Delete all selected rows. Also selected rows that are other pages
	 */
	public void deleteSelectedRows()
	{
		if (!i_IntArray_Selection.isEmpty())
		{
			int[] li_currentSelection = i_IntArray_Selection.toArray();
			Arrays.sort(li_currentSelection);
			for (int li_c = li_currentSelection.length - 1; li_c >= 0; li_c--)
			{
				i_TableModel.delete(li_currentSelection[li_c]);
				i_IntArray_Selection.removeValue(li_currentSelection[li_c]);
			}
		}

		if (getPageCount() < getPage())
		{
			setPage(getPageCount());
		}
	}

	protected boolean checkIfNewValues(String[] aS_Parameters)
	{
		// Following execution order is important. Do not change it.
		// Change may cause fault on setData method

		boolean lb_newValuesComponents = super.checkIfNewValues(aS_Parameters); // checkIfNewValuesComponents();
		boolean lb_newValuesSelection = checkNewValuesSelection();

		// Do not these rows to together to one OR clause
		// or other method is not executed

		return lb_newValuesComponents || lb_newValuesSelection;
	}

	//	private boolean checkIfNewValuesComponents()
	//	{
	//		// Put all values to child components
	//		boolean lb_componentIsModified = false;
	//
	//		Iterator l_Iterator = getChildComponents();
	//
	//		if (l_Iterator != null)
	//		{
	//			while (l_Iterator.hasNext())
	//			{
	//				Component l_Component = (Component) l_Iterator.next();
	//				if (l_Component.parseValues()) 
	//				{
	//					lb_componentIsModified = true;
	//				}
	//			}
	//		}
	//		return lb_componentIsModified;
	//	}

	private boolean checkNewValuesSelection()
	{
		HttpServletRequest l_HttpServletRequest = getHttpServletRequest();

		String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName());

		//		ii_oldSelection = ii_currentSelection;

		int[] li_newSelection = null;

		if (lS_Parameters != null)
		{
			int li_max = i_TableModel.getRowCount();
			// Getting dot location, doing here for hope to save few milliseconds
			int li_dotLocation = getResponseName().length();
			if (ib_multiSelection)
			{
				li_newSelection = new int[lS_Parameters.length];
				int li_y = 0;
				for (int li_c = 0; li_c < lS_Parameters.length; li_c++)
				{
					int li_value = parameterToValue(lS_Parameters[li_c], li_dotLocation);
					if (li_value >= 1 && li_value <= li_max)
					{
						li_newSelection[li_y] = li_value;
						li_y++;
					}
				}
				Arrays.sort(li_newSelection);
			}
			else
			{
				int li_value = parameterToValue(lS_Parameters[0], li_dotLocation);
				if (li_value >= 1 && li_value <= li_max)
				{
					li_newSelection = new int[1];
					li_newSelection[0] = li_value;
				}
			}

			return selectItems(li_newSelection);
		}
		return false;
	}
	
	private int parameterToValue(String aS_Parameter, int ai_dotLocation)
	{
		return Utils.stringToInt(aS_Parameter.substring(ai_dotLocation + 1),-1);
	}

	/**
	 * Select items
	 * 
	 * @param ai_indexes int array of indexes 
	 * @return boolean if selection has changed
	 */
	public boolean selectItems(int[] ai_indexes)
	{
		// Otro selection makes nothing
		if (!i_IntArray_Selection.equals(ai_indexes))
		{
			i_IntArray_Selection.setArray(ai_indexes);

			validate(new TableValidate(this, ai_indexes));

			if (hasComponentData() && isValidWithoutChilds() && ( ii_dataColumn > 0 || ii_dataColumn == TableModel.WHOLEROW ) && ii_dataColumn <= i_TableModel.getColumnCount())
			{
				if (i_IntArray_Selection.isEmpty())
				{
					setData(null);
				}
				else
				{
					setData(i_TableModel.getValueAt(i_IntArray_Selection.getValue(0), ii_dataColumn));
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Deselect all items. After this method no item is selected.
	 * 
	 * @return boolean if selection has changed
	 */
	public boolean deSelectItems()
	{
		return selectItems(null);
	}
	
	/**
	 * Search if value can be found and then selects it.
	 * Only first value is selected.
	 * 
	 * @param a_Object values to be selected
	 * @param ai_column Column to be searched
	 */
	public boolean selectItemByValue(Object a_Object, int ai_column)
	{
		int li_index = i_TableModel.search(a_Object, ai_column);
		return selectItem(li_index);
	}
	
	/**
	 * Search list of values and select all of them.
	 * 
	 * @param a_Object values to be selected
	 * @param ai_column Column to be searched
	 */
	public void selectItemsByValues(Object[] a_Object, int ai_column)
	{
		// This is made optimized way
		
		IntArray l_IntArray = new IntArray();
		
		for(int li_index = 0; li_index < a_Object.length ; li_index++ )
		{
			int li_selectedIndex = i_TableModel.search(a_Object[li_index], ai_column);
			if(li_selectedIndex > 0)
			{
				l_IntArray.addValue(li_selectedIndex);
			}
		}
		if(!l_IntArray.isEmpty())
		{
			selectItems(l_IntArray.toArray());
		}
	}

	/**
	 * @param ai_displayCount
	 */
	public void setDisplayCount(int ai_displayCount)
	{
		ii_displayCount = ai_displayCount;
	}

	/**
	 * @param ab_isFixed
	 */
	public void setPageSizeFixed(boolean ab_isFixed)
	{
		ib_pageSizeFixed = ab_isFixed;
	}

	/**
	 * @param ab_isMultiPageSelection
	 */
	public void setMultiPageSelection(boolean ab_isMultiPageSelection)
	{
		ib_multiPageSelection = ab_isMultiPageSelection;
	}

	private ArrayList iAL_TableComponentsInRow = null;
	private Component[] i_Component_RowModels;
	private int ii_tableComponent_ModelsSize = 0;

	private int[] ii_vTableCompIndx;
	private int ii_vTableCompIndxSize = 0;

	// Flag if components are changed while data inside 
	// table
	private boolean ib_componentsHasBeenChanged = false;

	private final static int NO_ROWS_INSERTED_OR_REMOVED = -1;
	private int ii_smallesInsertedOrRemovedRow = NO_ROWS_INSERTED_OR_REMOVED;

	private void synchRowNumbers()
	{
		if (ii_smallesInsertedOrRemovedRow == NO_ROWS_INSERTED_OR_REMOVED)
		{
			return;
		}

		for (int li_y = ii_smallesInsertedOrRemovedRow; li_y <= i_TableModel.getRowCount(); li_y++)
		{
			Component[] l_Components = (Component[]) iAL_TableComponentsInRow.get(li_y - 1);
			if (l_Components == null)
			{
				return;
			}
			for (int li_x = 0; li_x < l_Components.length; li_x++)
			{
				if (l_Components[li_x] != null)
				{
					TableComponentData l_TableComponentData = (TableComponentData) l_Components[li_x].getComponentData();
					l_TableComponentData.setRow(li_y);
				}
			}
		}
		ii_smallesInsertedOrRemovedRow = NO_ROWS_INSERTED_OR_REMOVED;
	}

	/**
	 * Add table model component, this component is base for row level component on Table
	 * 
	 * @param a_Component
	 * @param ai_column
	 */
	public void setTableModelComponent(Component a_Component, int ai_column)
	{
		if (a_Component == null)
		{
			throw new NullPointerException("TableComponent is null");
		}

		if (ai_column <= 0 || ai_column > 100)
		{
			throw new IllegalArgumentException("TableComponent is assigned to columns is out of range 1-100");
		}

		if (i_Component_RowModels == null)
		{
			i_Component_RowModels = new Component[100];
			ii_vTableCompIndx = new int[100];
			ii_tableComponent_ModelsSize = 0;
		}

		if (a_Component.getParent() != this)
		{
			throw new IllegalStateException("Parent component of child of table must be this table.");
		}

		//a_Component.setTableModel(i_TableModel);
		TableComponentData l_TableComponentData = new TableComponentData(i_TableModel);
		l_TableComponentData.setColumn(ai_column);
		a_Component.setComponentData(l_TableComponentData);

		// Remove model component these components are only models are should not be connected
		// to Table direcly and receive events.

		iHs_Components.remove(a_Component);

		// Adds item in correct position

		if (i_Component_RowModels[ai_column - 1] != null)
		{
			ii_vTableCompIndx[ii_vTableCompIndxSize] = ai_column - 1;
			ii_vTableCompIndxSize++;
		}

		i_Component_RowModels[ai_column - 1] = a_Component;

		if (ii_tableComponent_ModelsSize < ai_column)
		{
			ii_tableComponent_ModelsSize = ai_column;
		}

		// Update all components when needed

		ib_componentsHasBeenChanged = true;
	}

	/**
	 * <b>JSP use before{@link Form#execute() Form.execute()}</b>
	 * 
	 * @param ai_column
	 * @return Component
	 */
	public Component getTableModelComponent(int ai_column)
	{
		if (i_Component_RowModels == null)
		{
			throw new ArrayIndexOutOfBoundsException("No component at index " + ai_column);
		}

		return i_Component_RowModels[ai_column - 1];
	}

	/**
	 * <b>JSP use before{@link Form#execute() Form.execute()}</b>
	 * <p>
	 * Same as {@link #getTableModelComponent(int) getTableModelComponent()} but returns child Table
	 * 
	 * @param ai_column
	 * @return Table
	 */
	public Table getTableModelTable(int ai_column)
	{
		if (i_Component_RowModels == null)
		{
			throw new ArrayIndexOutOfBoundsException("No component at index " + ai_column);
		}

		return (Table) i_Component_RowModels[ai_column - 1];
	}

	int insertComponentRow(int ai_before)
	{
		if (i_Component_RowModels != null)
		{
			if (iAL_TableComponentsInRow == null)
			{
				iAL_TableComponentsInRow = new ArrayList();
			}

			Component[] l_Components = new Component[ii_tableComponent_ModelsSize];
			for (int li_index = 0; li_index < ii_tableComponent_ModelsSize; li_index++)
			{
				if (i_Component_RowModels[li_index] != null)
				{
					TableComponentData l_TableComponentData = new TableComponentData(i_TableModel);
					l_TableComponentData.setRow(ai_before);
					l_TableComponentData.setColumn(li_index + 1);

					Component l_Component = i_Component_RowModels[li_index].cloneComponent();
					l_Component.setComponentData(l_TableComponentData);
					l_Component.syncronizeData();
					l_Component.validate();
					l_Components[li_index] = l_Component;
				}
			}

			iAL_TableComponentsInRow.add(ai_before - 1, l_Components);
			if (ai_before < ii_smallesInsertedOrRemovedRow || ii_smallesInsertedOrRemovedRow == NO_ROWS_INSERTED_OR_REMOVED)
			{
				ii_smallesInsertedOrRemovedRow = ai_before;
			}
		}

		// Increase values for all which value

		i_IntArray_Selection.addOneToValuesThatAreBiggerThan(ai_before);
		
		
		// TODO: Needs optimization. So validation can be done only once.
		// not effective to validate rows every insert
		
		validate(new TableValidate(this, getSelectedItems()));
		
		return ai_before;
	}



	int addComponentRow()
	{
		return insertComponentRow(iAL_TableComponentsInRow.size() + 1);
	}

	void removeComponentRow(int ai_index)
	{
		// Remove row from selection also
		i_IntArray_Selection.sort();
		i_IntArray_Selection.removeValue(ai_index);
		i_IntArray_Selection.substractValuesThatAreBiggerThan(ai_index);

		if (iAL_TableComponentsInRow != null)
		{
			Component[] l_Components = (Component[]) iAL_TableComponentsInRow.get(ai_index - 1);

			for (int li_i = 0; li_i < l_Components.length; li_i++)
			{
				if (l_Components[li_i] != null)
				{
					iHs_Components.remove(l_Components[li_i]);
					l_Components[li_i].dispose();
				}
			}

			iAL_TableComponentsInRow.remove(ai_index - 1);

			// Update smallest removed row

			if (ai_index < ii_smallesInsertedOrRemovedRow || ii_smallesInsertedOrRemovedRow == NO_ROWS_INSERTED_OR_REMOVED)
			{
				ii_smallesInsertedOrRemovedRow = ai_index;
			}
		}
		validate(new TableValidate(this, getSelectedItems()));
	}

	/**
	 * This updates components so that rows and columns and component types
	 * are sync. It is needed to call this if you want to use component interfaces
	 * directly.
	 */
	public void updateAllComponents()
	{
		if (i_Component_RowModels == null)
		{
			return;
		}

		int li_tableModelCount = i_TableModel.getRowCount();

		if (iAL_TableComponentsInRow == null)
		{
			iAL_TableComponentsInRow = new ArrayList();
		}

		int li_componenRowCount = iAL_TableComponentsInRow.size();

		// Add or remove rows

		if (li_componenRowCount > li_tableModelCount)
		{
			int li_count = li_componenRowCount - li_tableModelCount;

			// Remove from last .. faster
			for (int li_r = 0; li_r < li_count; li_r++)
			{
				removeComponentRow(li_componenRowCount - li_r);
			}
		}
		else if (li_componenRowCount < li_tableModelCount)
		{
			int li_count = li_tableModelCount - li_componenRowCount;
			for (int li_r = 0; li_r < li_count; li_r++)
			{
				addComponentRow();
			}
		}

		// Update all rows
		
		for (int li_index = 1; li_index <= li_tableModelCount; li_index++)
		{
			updateComponentRow(li_index);
		}

		ib_componentsHasBeenChanged = false;
	}

	public void updateComponent(int ai_row, int ai_column)
	{
		if (iAL_TableComponentsInRow != null && iAL_TableComponentsInRow.size() > ai_row)
		{
			Component[] l_Components = (Component[]) iAL_TableComponentsInRow.get(ai_row - 1);
			if (l_Components.length >= ai_column)
			{
				if (l_Components[ai_column - 1] != null)
				{
					l_Components[ai_column - 1].syncronizeData();
				}
			}
		}
	}

	public void updateComponentRow(int ai_index)
	{

		Component[] l_Components = (Component[]) iAL_TableComponentsInRow.get(ai_index - 1);

		if (l_Components != null && i_Component_RowModels != null && l_Components.length > 0 && i_Component_RowModels.length > 0)
		{
			if (l_Components.length != ii_tableComponent_ModelsSize)
			{
				l_Components = new Component[ii_tableComponent_ModelsSize];
				for (int li_index = 0; li_index < ii_tableComponent_ModelsSize; li_index++)
				{
					if (i_Component_RowModels[li_index] != null)
					{
						TableComponentData l_TableComponentData = new TableComponentData(i_TableModel);
						l_TableComponentData.setRow(ai_index);
						l_TableComponentData.setColumn(li_index + 1);

						Component l_Component = i_Component_RowModels[li_index].cloneComponent();
						l_Component.setComponentData(l_TableComponentData);
						l_Component.syncronizeData();
						l_Component.validate();
						l_Components[li_index] = l_Component;

					}
				}
				iAL_TableComponentsInRow.set(ai_index - 1, l_Components);
			}
			else
			{
				for (int li_index = 0; li_index < ii_tableComponent_ModelsSize; li_index++)
				{
					if (l_Components[li_index] == null && i_Component_RowModels[li_index] == null)
					{
						// Do nothing
					}
					else if (l_Components[li_index] == null && i_Component_RowModels[li_index] != null)
					{
						// This columns componend doesn't exists, but component exist in row model
						// =>Add component to this column
						l_Components[li_index] = i_Component_RowModels[li_index].cloneComponent();
						l_Components[li_index].setComponentData(new TableComponentData(i_TableModel));
						TableComponentData l_TableComponentData = (TableComponentData) l_Components[li_index].getComponentData();
						l_TableComponentData.setRow(ai_index);
						l_TableComponentData.setColumn(li_index + 1);
						l_Components[li_index].syncronizeData();
						l_Components[li_index].validate();
					}
					else if (l_Components[li_index] != null && i_Component_RowModels[li_index] == null)
					{
						// This columns componend exists, but component doesn't exist in row model
						// =>Remove component from this column
						l_Components[li_index].dispose();
						l_Components[li_index] = null;
					}
					else if (l_Components[li_index].getClass().equals(i_Component_RowModels[li_index].getClass()))
					{
						// Component is equal.
						// TODO: for this component would need equals method override
						// => Just update row information	
						TableComponentData l_TableComponentData = (TableComponentData) l_Components[li_index].getComponentData();
						l_TableComponentData.setRow(ai_index);
					}
					else 
					{
						//´Component type has been changed
						// Change component and update it
						TableComponentData l_TableComponentData = new TableComponentData(i_TableModel);
						l_TableComponentData.setRow(ai_index);
						l_TableComponentData.setColumn(li_index + 1);

						l_Components[li_index] = i_Component_RowModels[li_index].cloneComponent();
						l_Components[li_index].setComponentData(l_TableComponentData);
						l_Components[li_index].syncronizeData();
						l_Components[li_index].validate();
					}
				}
			}
		}
	}

	protected Iterator getChildComponents()
	{
		if (iHs_Components == null)
		{
			return null;
		}
		else
		{
			return iHs_Components.iterator();
		}
	}
	/**
	 * @see com.sohlman.netform.Component#addComponent(Component)
	 */
	protected void addComponent(Component a_Component)
	{
		iHs_Components.add(a_Component);
	}

	/** 
	 * Column name also JSP use
	 * @param ai_index
	 * @return Name of the column
	 */
	public String getColumnName(int ai_index)
	{
		return i_TableModel.getColumnName(ai_index);
	}

	/**
	 * @param ai_row
	 * @return
	 */
	private int translateDisplayRowToRealRow(int ai_row)
	{
		return ii_displayStart + ai_row - 1;
	}

	private SimpleDateFormat i_SimpleDateFormat = null;
	private DecimalFormat i_DecimalFormat = null;

	/**
	 * <b>For JSP use</b>
	 * 
	 * Returns value of page
	 * @param ai_row
	 * @param ai_column
	 * @return String (never null)
	 */
	public String getText(int ai_row, int ai_column)
	{
		int li_row = translateDisplayRowToRealRow(ai_row);
		Object l_Object = i_TableModel.getValueAt(li_row, ai_column);
		if (l_Object == null)
		{
			return "";
		}
		else
		{
			String l_String = "";
			String lS_Format = getColumnFormat(ai_column, l_Object.getClass());

			if (lS_Format != null)
			{
				if (java.util.Date.class.isAssignableFrom(l_Object.getClass()))
				{
					try
					{
						if (i_SimpleDateFormat == null)
						{
							i_SimpleDateFormat = new SimpleDateFormat(lS_Format);
						}
						l_String = i_SimpleDateFormat.format(l_Object);
					}
					catch (Exception l_Exception)
					{
						l_String = l_Object.toString();
					}
				}
				else if (Number.class.isAssignableFrom(l_Object.getClass()))
				{
					try
					{
						if (i_DecimalFormat == null)
						{
							i_DecimalFormat = new DecimalFormat(lS_Format);
						}
						l_String = i_DecimalFormat.format(((Number) l_Object).doubleValue());
					}
					catch (Exception l_Exception)
					{
						l_String = l_Object.toString();
					}
				}
			}
			else
			{
				l_String = l_Object.toString();
			}
			return Utils.stringToHTML(l_String.toString());
		}
	}

	/**
	 * For JSP use
	 * 
	 * It is better use getText(int ai_row, int ai_column), because it is faster
	 * 
	 * @param ai_row
	 * @param ai_column
	 * @return String (never null)
	 */
	public String getText(int ai_row, String aS_ColumnName)
	{
		return getText(ai_row, i_TableModel.getIndexByName(aS_ColumnName));
	}

	/** 
	 * JSP Evaluates returns true or false depending of following rules.
	 * <p>
	 * If it is null then it is always <b>false</b>
	 * <p>
	 * <u>It is true if in following cases.</u>
	 * </p>
	 * <table>
	 * <tr>
	 * 	<td>Supported class/interface</td><td>How it is evaluated</td>
	 * </tr> 
	 * <tr>
	 * 	<td>Boolean</td><td>Boolean.booleanValue()</td>
	 * </tr> 
	 * <tr>
	 * 	<td>Number</td><td>Number.longValue() != 0</td>
	 * </tr>
	 * <tr>
	 * 	<td>String</td><td>String.trim().equals("")</td>
	 * </tr> 
	 * </table>
	 * <p>
	 * and all other cases it is false
	 *
	 * @param ai_row row number
	 * @param ai_column column number
	 * @return true or false
	 */
	public boolean getBoolean(int ai_row, int ai_column)
	{
		Object l_Object = i_TableModel.getValueAt(ai_row, ai_column);

		if (l_Object == null)
		{
			return false;
		}

		if (Boolean.class == l_Object.getClass())
		{
			return ((Boolean) l_Object).booleanValue();
		}
		if (Number.class.isAssignableFrom(l_Object.getClass()))
		{
			return ((Number) l_Object).longValue() != 0;
		}
		if (String.class == l_Object.getClass())
		{
			return ((String) l_Object).trim().equals("");
		}
		return false;
	}

	/** 
	 * JSP Evaluates according to some rules.
	 * 
	 * @see Table#getBoolean(int, int)
	 *
	 * @param ai_row row number
	 * @param aS_ColumnName Column name
	 * @return true or false see the table
	 */
	public boolean getBoolean(int ai_row, String aS_ColumnName)
	{
		return getBoolean(ai_row, i_TableModel.getIndexByName(aS_ColumnName));
	}

	/**
	 * <b>JSP</b>
	 * <p>
	 * It is based on {@link Table#getBoolean(int, int) getBoolean()} method.
	 *
	 * @param ai_row row number
	 * @param ai_column column number
	 * @param aS_True String to be returned if it is evaluated as true
	 * @param aS_False String to be returned if it is evaluated as false
	 * @return String 
	 */
	public String getStringFromBoolean(int ai_row, int ai_column, String aS_True, String aS_False)
	{
		if (getBoolean(ai_row, ai_column))
		{
			return aS_True;
		}
		else
		{
			return aS_False;
		}
	}
	/**
	 * <b>JSP</b>
	 * <p>
	 * It is based on {@link Table#getBoolean(int, int) getBoolean()} method.
	 *
	 * @param ai_row row number
	 * @param ai_column String containing column name
	 * @param aS_True String to be returned if it is evaluated as true
	 * @param aS_False String to be returned if it is evaluated as false
	 * @return String 
	 */
	public String getStringFromBoolean(int ai_row, String aS_ColumnName, String aS_True, String aS_False)
	{
		if (getBoolean(ai_row, aS_ColumnName))
		{
			return aS_True;
		}
		else
		{
			return aS_False;
		}
	}

	/**
	 * For JSP use
	 * 
	 * Returns first text of selected row.
	 * 
	 * @param ai_column column that want to be shown
	 * @return Text empty text if nothing selected or value is null in TableModel
	 */
	public String getSelectedText(int ai_column)
	{
		int li_row = getSelectedRow();
		Object l_Object = i_TableModel.getValueAt(li_row, ai_column);
		if (l_Object == null)
		{
			return "";
		}
		else
		{
			return Utils.stringToHTML(l_Object.toString());
		}
	}

	/**
	 * JSP use
	 * 
	 * @param ai_displayRow displayRow number
	 * @param aS_ColumnName Name of the column
	 * @return Component
	 */
	public Component getComponentAt(int ai_displayRow, String aS_ColumnName)
	{
		return getComponentAt(ai_displayRow, i_TableModel.getIndexByName(aS_ColumnName));
	}
	
	/**
	 * JSP use
	 * 
	 * @param ai_displayRow
	 * @param ai_column
	 * @return Component
	 */
	public Component getComponentAt(int ai_displayRow, int ai_column)
	{
		return getComponentAt(ai_displayRow, ai_column, true);
	}

	/**
	 * @param ai_row
	 * @param ai_column
	 * @param ab_useDisplayRow if row number is handed as display row
	 * @return Component
	 */
	protected Component getComponentAt(int ai_row, int ai_column, boolean ab_useDisplayRow)
	{	
		if(ab_useDisplayRow)
		{
			ai_row = translateDisplayRowToRealRow(ai_row);
		}
		if (ai_row <= 0 || ai_row > i_TableModel.getRowCount())
		{
			throw new ArrayIndexOutOfBoundsException(ai_row + " is out of range ( 1 - " + i_TableModel.getRowCount() + " )");			
		}
				
		Component[] l_Components = (Component[]) iAL_TableComponentsInRow.get(ai_row -1);

		if (l_Components == null)
		{
			throw new IllegalStateException("No components defined for this table.");
		}

		if(ai_column < 0 || ai_column > l_Components.length)
		{
			throw new ArrayIndexOutOfBoundsException(ai_column + " is out of range ( 1 -" + l_Components.length + " )");
		}
		
		if(l_Components[ai_column - 1]==null)
		{
			throw new NullPointerException("No component defined to column index = " + ai_column );
		}

		return l_Components[ai_column - 1];		
	}	
	
	/**
	 * <b>JSP</p>
	 * <p>
	 * Returns Table compatible control
	 * 
	 * @param ai_displayRow Display row nro, which is translated to real Table row
	 * @param ai_column Column number
	 * @return Table
	 * @throws NullPointerException component is not assigned to this column
	 * @throws ClassCastException if Component is not Table 
	 */
	public Table getTableAt(int ai_displayRow, int ai_column)
	{
		Component l_Component = getComponentAt(ai_displayRow, ai_column);

		if (l_Component == null)
		{
			throw new NullPointerException("Table.getTableAt row=" + ai_displayRow + ", column=" + ai_column + " is returning null instead of Table");
		}
		else if (l_Component.getClass().isAssignableFrom(Table.class))
		{
			return (Table) l_Component;
		}
		else
		{
			throw new ClassCastException(
				"Table.getTableAt row=" + ai_displayRow + ", column=" + ai_column + " is returning " + l_Component.getClass().getName() + " instead of Table");
		}
	}
	/**
	 * <b>JSP</p>
	 * <p>
	 * Returns Table compatible control
	 * 
	 * @param ai_displayRow Display row nro, which is translated to real Table row
	 * @param aS_ColumnName Column name
	 * @return Table
	 * @throws NullPointerException component is not assigned to this column
	 * @throws ClassCastException if Component is not Table 
	 */
	public Table getTableAt(int ai_displayRow, String aS_ColumnName)
	{
		return getTableAt(ai_displayRow, i_TableModel.getIndexByName(aS_ColumnName));
	}

	/**
	 * <b>JSP</p>
	 * <p>
	 * Returns TextField compatible control
	 * 
	 * @param ai_displayRow Display row nro, which is translated to real Table row
	 * @param ai_column Column number
	 * @return TextField
	 * @throws NullPointerException component is not assigned to this column
	 * @throws ClassCastException if Component is not TextField 
	 */
	public TextField getTextFieldAt(int ai_displayRow, int ai_column)
	{
		Component l_Component = getComponentAt(ai_displayRow, ai_column);

		if (l_Component == null)
		{
			throw new NullPointerException("Table.getTextFieldAt row=" + ai_displayRow + ", column=" + ai_column + " is returning null instead of TextField");
		}
		else if (TextField.class.isAssignableFrom(l_Component.getClass()))
		{
			return (TextField) l_Component;
		}
		else
		{
			throw new ClassCastException(
				"Table.getTextFieldAt row="
					+ ai_displayRow
					+ ", column="
					+ ai_column
					+ " is returning "
					+ l_Component.getClass().getName()
					+ " instead of TextField");
		}
	}

	/**
	 * <b>JSP</p>
	 * <p>
	 * Returns TextField compatible control
	 * 
	 * @param ai_displayRow Display row nro, which is translated to real Table row
	 * @param aS_ColumnName Column name
	 * @return TextField
	 * @throws NullPointerException component is not assigned to this column
	 * @throws ClassCastException if Component is not TextField 
	 */
	public TextField getTextFieldAt(int ai_displayRow, String aS_ColumnName)
	{
		return getTextFieldAt(ai_displayRow, i_TableModel.getIndexByName(aS_ColumnName));
	}

	/**
	 * <b>JSP</b>
	 * <p>
	 * Return parameter string only if row is selected
	 * 
	 * @param ai_row
	 * @param a_String String to be returned if row is seleced
	 * @return String if row is selecgted "" if not
	 */
	public String getStringIfRowSelected(int ai_row, String a_String)
	{
		if (isRowSelected(ai_row))
		{
			return a_String;
		}
		else
		{
			return "";
		}
	}

	public boolean isRowSelected(int ai_row)
	{
		return isSelected(translateDisplayRowToRealRow(ai_row));
	}

	/**
	 * JSP usage
	 * @return if rows has been selected 
	 */
	public boolean hasSelectedRows()
	{
		return getSelectedCount() > 0;
	}

	/**
	 * Is there component at this position
	 * 
	 * @param ai_row
	 * @param ai_column
	 * @return boolean Is there component at this position
	 */
	public boolean hasComponent(int ai_row, int ai_column)
	{
		return getComponentAt(ai_row, ai_column) != null;
	}

	/**
	 * JSP use
	 * 
	 * @return row count to be displayed
	 */
	public int getDisplayRowCount()
	{
		return ii_displayCount;
	}

	/**
	 * JSP use
	 * 
	 * This row id defines, which row is which in JSP page and which is selected and which not.
	 * 
	 * @return row id.
	 */
	public String getRowId(int ai_displayRow)
	{
		int li_row = translateDisplayRowToRealRow(ai_displayRow);

		return getResponseName() + "." + String.valueOf(li_row);
	}

	/**
	 * @see com.sohlman.netform.Component#cloneComponent()
	 */
	public Component cloneComponent()
	{
		Table l_Table = new Table(getParent(), getTableModel());

		l_Table.ii_dataColumn = ii_dataColumn;
		l_Table.ib_multiSelection = ib_multiSelection;
		l_Table.iAL_ColumnFormats = iAL_ColumnFormats;
		l_Table.setComponentValidator(this.getComponentValidator());
		l_Table.shareComponentListenerFrom(this);

		return l_Table;
	}

	/**
	 * @see com.sohlman.netform.Component#lastComponentIteration()
	 */
	protected void lastComponentIteration()
	{
		super.lastComponentIteration();
		
		if (i_TableModel == null)
		{
			throw new IllegalStateException("No TableModel defined for Table");
		}

		if (ib_componentsHasBeenChanged)
		{
			updateAllComponents();
		}

		synchRowNumbers();

		// Handle status of page buttons.
		//validate(new TableValidate(this, getSelectedItems()));

		if (ii_pageSize != PAGESIZE_NOT_DEFINED)
		{
			ii_displayCount = ii_pageSize;
			ii_displayStart = (getPage() - 1) * ii_displayCount + ii_displayCount;
		}
		else
		{
			ii_displayCount = i_TableModel.getRowCount();
			ii_displayStart = 1;
		}
	}

	/**
	 * Set's DataColumn. This column number is used to store data to DataContainer 
	 * 
	 * @param ai_index
	 */
	public void setDataColumn(int ai_index)
	{
		ii_dataColumn = ai_index;
	}

	/**
	 * @see com.sohlman.netform.Component#dispose()
	 */
	public void dispose()
	{
		super.dispose();
		if (i_TableModel != null)
		{
			i_TableModel.removeTable(this);
		}
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{
			if (ii_dataColumn == -1)
			{
				throw new NetFormException("Table.setDataColumn() is not set");
			}
			Object l_Object = getData();
			int li_row = i_TableModel.search(l_Object, ii_dataColumn);
			if (li_row > 0 && li_row <= i_TableModel.getRowCount())
			{
				selectItem(li_row);
			}
		}
	}

	/**
	 * @see com.sohlman.netform.Component#validate()
	 */
// TODO: Check if this overridden is necessary
//	public void validate()
//	{
//
//	}

	private ArrayList iAL_ColumnFormats = null;

	/**
	 * <b>JSP</b>
	 * <p>
	 * Set column format for column
	 * 
	 * @param ai_column
	 * @param aS_Format
	 */
	public void setColumnFormat(int ai_column, String aS_Format)
	{
		if (iAL_ColumnFormats == null)
		{
			iAL_ColumnFormats = new ArrayList();
		}
		if (iAL_ColumnFormats.size() < ai_column)
		{
			int li_index = iAL_ColumnFormats.size();
			for (; li_index < ai_column; li_index++)
			{
				iAL_ColumnFormats.add(null);
			}

		}
		iAL_ColumnFormats.set(ai_column - 1, aS_Format);
	}

	/**
	 * Get column format for current column
	 * 
	 * @param ai_column colum index
	 * @return String containing column format
	 */
	protected String getColumnFormat(int ai_column, Class a_Class)
	{
		if (iAL_ColumnFormats == null || ai_column > iAL_ColumnFormats.size())
		{
			if (a_Class == null)
			{
				return null;
			}
			else
			{
				return super.getFormatFromParent(a_Class, null);
			}
		}
		else
		{
			return (String) iAL_ColumnFormats.get(ai_column - 1);
		}
	}

	/**
	 * @see com.sohlman.netform.Component#getFormatFromParent(java.lang.Class, com.sohlman.netform.ComponentData)
	 */
	protected String getFormatFromParent(Class a_Class, ComponentData a_ComponentData)
	{
		if (a_ComponentData == null)
		{
			return super.getFormatFromParent(a_Class, null);
		}
		else
		{
			return getColumnFormat(((TableComponentData) a_ComponentData).getColumn(), a_Class);
		}
	}
	
	/**
	 * Search data from table
	 * {@link TableModel TableModel} has to support search to able to use this method.
	 * 
	 * @param aO_Search 
	 * @param ai_column
	 * @return int row  
	 */
	public int search(Object aO_Search, int ai_column)
	{
		return i_TableModel.search(aO_Search, ai_column);
	}
	
	/**
	 * Set source table where data can be copied/moving to this table.
	 * <p>
	 * Source and destination tables has to has same type  {@link TableModel TableModel}s. 
	 * <p>
	 * Copying/Moving is possible to do also with java script in client side.
	 * Of course ultimate validation is done at server side.
	 * <p>
	 * 
	 * @param a_Table source Table
	 */
	public void setSourceTable(Table a_Table)
	{
		// TOD0: To implement
	}
	
	/**
	 * Override
	 * 
	 * @see com.sohlman.netform.Component#validate()
	 */
	public void validate()
	{
		validate(new TableValidate(this,getSelectedItems()));
	}	
}
