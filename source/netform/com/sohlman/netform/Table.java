package com.sohlman.netform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

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

	public Table(Component a_Component_Parent, TableModel a_TableModel)
	{
		super(a_Component_Parent);
		i_TableModel = a_TableModel;
		i_TableModel.setTable(this);
	}

	public Table(Form a_Form, TableModel a_TableModel)
	{
		super(a_Form);
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
	 * Adds new row after 
	 * @return inserted row number
	 */
	public int insertRowBeforeFirstSelection()
	{
		int li_row = getSelectedRow(0);

		return i_TableModel.insert(li_row);
	}
	/**
	 * Wrapper for
	 * @see com.sohlman.netform.TableModel#insert()
	 */
	public int insertRow(int ai_before)
	{
		return i_TableModel.insert(ai_before);
	}

	/**
	 * Wrapper for
	 * @see com.sohlman.netform.TableModel#add()
	 */
	public int addRow()
	{
		return i_TableModel.add();
	}

	/**
	 * Wrapper for
	 * @see com.sohlman.netform.TableModel#delete(int)
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
		return ((ii_displayStart - 1) / ii_displayCount) + 1;
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
	 * @param ai_index page index to be changed
	 * if page index is larger than {@link #pageCount() pageCount()} then
	 * last page is selected
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

	protected boolean checkIfNewValues()
	{
		// Following execution order is important. Do not change it.
		// Change may cause fault on setData method

		boolean lb_newValuesComponents = checkIfNewValuesComponents();
		boolean lb_newValuesSelection = checkNewValuesSelection();

		// Do not these rows to together to one OR clause
		// or other method is not executed

		return lb_newValuesComponents || lb_newValuesSelection;
	}

	private boolean checkIfNewValuesComponents()
	{
		// Put all values to child components
		boolean lb_componentIsModified = false;

		Iterator l_Iterator = getChildComponents();

		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				if (l_Component.parseValues())
				{
					lb_componentIsModified = true;
				}
			}
		}
		return lb_componentIsModified;
	}

	private boolean checkNewValuesSelection()
	{
		HttpServletRequest l_HttpServletRequest = getHttpServletRequest();

		String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName());

		//		ii_oldSelection = ii_currentSelection;

		int[] li_newSelection = null;

		if (lS_Parameters != null)
		{
			int li_max = i_TableModel.getRowCount();
			if (ib_multiSelection)
			{
				li_newSelection = new int[lS_Parameters.length];
				int li_y = 0;
				for (int li_c = 0; li_c < lS_Parameters.length; li_c++)
				{
					int li_value = Utils.stringToInt(lS_Parameters[li_c], -1);
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
				int li_value = Utils.stringToInt(lS_Parameters[0], -1);
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
			validate();

			if (hasComponentData() && isValid() && ii_dataColumn > 0 && ii_dataColumn <= i_TableModel.getColumnCount())
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

		/*
				if (ii_currentSelection == null || !ib_multiSelection || !ib_multiPageSelection)
				{
					ii_currentSelection = ai_indexes;
				}
				else
				{
					int[] li_selection = new int[ii_currentSelection.length + ai_indexes.length];
		
					int li_sIndex = (getPage() - 1) * getPageSize();
					int li_eIndex = (getPage() * getPageSize()) - 1;
		
					int li_s = 0; // Counter for result selection
					for (int li_c = 0; li_c < ii_currentSelection.length; li_c++)
					{
						if (ii_oldSelection[li_c] < li_sIndex || ii_oldSelection[li_c] > li_eIndex)
						{
							li_selection[li_s] = ii_oldSelection[li_c];
							li_s++;
						}
					}
		
					for (int li_c = 0; li_c < ai_indexes.length; li_c++)
					{
						if (ai_indexes[li_c] >= li_sIndex && ai_indexes[li_c] <= li_eIndex)
						{
							li_selection[li_s] = ai_indexes[li_c];
							li_s++;
						}
					}
		
					ii_currentSelection = new int[li_s];
					System.arraycopy(li_selection, 0, ii_currentSelection, 0, li_s);
					Arrays.sort(ii_currentSelection);
				}
		*/

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

	/**
	 * @see com.sohlman.netform.Component#isValid()
	 */
	public boolean isValid()
	{
		boolean lb_isValid = super.isValid();
		if (lb_isValid)
		{
			// Change state to old_value

			Iterator l_Iterator = getChildComponents();
			if (l_Iterator != null)
			{

				while (l_Iterator.hasNext())
				{
					Component l_Component = (Component) l_Iterator.next();
					if (!l_Component.isValid())
					{
						return false;
					}
				}
			}
		}
		return lb_isValid;
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
				if(l_Components[li_x]!=null)
				{
					TableComponentData l_TableComponentData = (TableComponentData) l_Components[li_x].getComponentData();
					l_TableComponentData.setRow(li_y);
				}
			}
		}
		ii_smallesInsertedOrRemovedRow = NO_ROWS_INSERTED_OR_REMOVED;
	}

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

		if (i_TableModel == null)
		{
			throw new IllegalStateException("TableModel has to be defined before TableModel Components");
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
	}

	void updateAllComponents()
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
		if (iAL_TableComponentsInRow != null)
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

	private void updateComponentRow(int ai_index)
	{
		Component[] l_Components = (Component[]) iAL_TableComponentsInRow.get(ai_index - 1);

		if (l_Components != null && i_Component_RowModels != null)
		{
			for (int li_i = 0; li_i < ii_tableComponent_ModelsSize; li_i++)
			{
				if (l_Components[li_i] == null && i_Component_RowModels != null)
				{
					// Component structure has changed

					l_Components[li_i] = i_Component_RowModels[li_i].cloneComponent();
					l_Components[li_i].setComponentData(new TableComponentData(i_TableModel));
					TableComponentData l_TableComponentData = (TableComponentData) l_Components[li_i].getComponentData();
					l_TableComponentData.setRow(ai_index);
					l_TableComponentData.setColumn(li_i + 1);
					l_Components[li_i].syncronizeData();
				}
				else if (l_Components[li_i] != null && i_Component_RowModels[li_i] == null)
				{
					// Component structure has changed

					l_Components[li_i].dispose();
					l_Components[li_i] = null;
				}
				else if (l_Components[li_i].getClass().equals(i_Component_RowModels[li_i].getClass()))
				{
					TableComponentData l_TableComponentData = (TableComponentData) l_Components[li_i].getComponentData();
					l_TableComponentData.setRow(ai_index);
					l_TableComponentData.setColumn(li_i + 1);
					l_Components[li_i].syncronizeData();
				}
				else // Component column type has been changed
					{
					TableComponentData l_TableComponentData = new TableComponentData(i_TableModel);
					l_TableComponentData.setRow(ai_index);
					l_TableComponentData.setColumn(li_i + 1);

					l_Components[li_i] = i_Component_RowModels[li_i].cloneComponent();
					l_Components[li_i].setComponentData(l_TableComponentData);
					l_Components[li_i].syncronizeData();
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
	 * @see com.sohlman.netform.Component#addComponent(java.lang.String, com.sohlman.netform.Component)
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

	/**
	 * For JSP use
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
			return Utils.stringToHTML(l_Object.toString());
		}
	}

	/**
	 * JSP use
	 * 
	 * @param ai_displayRow
	 * @param ai_column
	 * @return
	 */
	public Component getComponentAt(int ai_displayRow, int ai_column)
	{
		int li_row = translateDisplayRowToRealRow(ai_displayRow);

		if (li_row > 0 && li_row <= i_TableModel.getRowCount() && ai_column > 0)
		{
			Component[] l_Components = (Component[]) iAL_TableComponentsInRow.get(li_row - 1);

			if (l_Components == null)
			{
				return null;
			}

			if ((ai_column - 1) >= l_Components.length)
			{
				return null;
			}

			return l_Components[ai_column - 1];
		}
		else
		{
			return null;
		}
	}

	public boolean isRowSelected(int ai_row)
	{
		return isSelected(translateDisplayRowToRealRow(ai_row));
	}

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

		return String.valueOf(li_row);
	}

	/**
	 * @see com.sohlman.netform.Component#cloneComponent()
	 */
	public Component cloneComponent()
	{
		// On developement

		Table l_Table = new Table(getParent(), getTableModel());

		l_Table.ii_dataColumn = ii_dataColumn;
		l_Table.ib_multiSelection = ib_multiSelection;

		return l_Table;
	}

	/**
	 * @see com.sohlman.netform.Component#lastComponentIteration()
	 */
	protected void lastComponentIteration()
	{
		super.lastComponentIteration();

		if (ib_componentsHasBeenChanged)
		{
			updateAllComponents();
		}

		synchRowNumbers();

		// Handle status of page buttons.

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
			Object l_Object = getData();
			int li_row = i_TableModel.search(l_Object, ii_dataColumn);
			if (li_row > 0 && li_row <= i_TableModel.getRowCount())
			{
				selectItem(li_row);
			}
		}
	}
}
