package com.sohlman.webapp.netform;

import com.sohlman.dataset.ColumnInfo;
import com.sohlman.dataset.DataSet;
import com.sohlman.dataset.RowInfo;
import com.sohlman.dataset.netform.DataSetTableModel;
import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.component.Button;
import com.sohlman.netform.component.TextField;
import com.sohlman.netform.component.table.SimpleTableModel;
import com.sohlman.netform.component.table.Table;

/**
 * @author Sampsa Sohlman
 */
public class Table2Form extends MasterForm
{

	private ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if (a_Component == addRowButton)
			{
				// Add new row to DataSet and Table
				// Table knows that through TableModel
				// To limit max rows to 50. All the rows are stored to session
				// and should be so big				
				if (i_DataSet.getRowCount() <= 50)
				{
					tableList.addRow();
				}
			}
			else if (a_Component == insertRowButton)
			{
				// Add new row to DataSet and Table
				// Table knows that through TableModel
				// To limit max rows to 50. All the rows are stored to session
				// and should be so big				
				if (i_DataSet.getRowCount() <= 50)
				{
					tableList.insertRowBeforeFirstSelection();
				}
			}
			else if (a_Component == deleteRowButton)
			{
				// Delete Selected Rows from Table
				tableList.deleteSelectedRows();
			}
			else if (a_Component == addTextButton)
			{
				// Add new text to choise list
				// To limit max rows to 50. All the rows are stored to session
				// and should be so big
				if (i_SimpleTableModel.getRowCount() <= 50)
				{
					String lS_Text = textField.getText();
					if (lS_Text != null && !lS_Text.trim().equals(""))
					{
						if (i_SimpleTableModel.search(lS_Text, 1) == -1)
						{
							i_SimpleTableModel.addValue(lS_Text);
						}
					}
				}
			}
			else if (a_Component == insertTextButton)
			{
				// Add new text to choise list
				// To limit max rows to 50. All the rows are stored to session
				// and should be so big
				if (i_SimpleTableModel.getRowCount() <= 50)
				{
					String lS_Text = textField.getText();
					if (lS_Text != null && !lS_Text.trim().equals(""))
					{

						if (tableSelect.search(lS_Text, 1) == -1)
						{
							int li_row = tableSelect.insertRowBeforeFirstSelection();
							if(li_row > 0)
							{
								tableSelect.setValueAt(lS_Text, li_row, 1);
							}
						}
					}
				}
			}
			else if (a_Component == removeTextButton)
			{
				// Remove text from choises

				int[] li_items = tableSelect.getSelectedItems();

				// Only 1 can be removed at once

				if (li_items != null && li_items.length == 1)
				{
					Object l_Object = i_SimpleTableModel.getValueAt(li_items[0], 1);

					// Check that it is not chosen in tableList
					// DataSet is inside of that

					if (i_DataSet.search(l_Object, 1) == 0)
					{
						i_SimpleTableModel.delete(li_items[0]);
					}
				}
			}
		}
	};

	public Button addRowButton = new Button(this);
	public Button insertRowButton = new Button(this);
	public Button deleteRowButton = new Button(this);
	public Button addTextButton = new Button(this);
	public Button insertTextButton = new Button(this);
	public Button removeTextButton = new Button(this);		
		
	public TextField textField = new TextField(this);
		
	public Table tableSelect = new Table(this);

	public Table tableList = new Table(this);

	private DataSet i_DataSet;
	private SimpleTableModel i_SimpleTableModel;
	
	public Table2Form()
	{
		// Here we do component settings
			
		addRowButton.addComponentListener(i_ComponentListener);
		insertRowButton.addComponentListener(i_ComponentListener);
		deleteRowButton.addComponentListener(i_ComponentListener);

		addTextButton.addComponentListener(i_ComponentListener);
		insertTextButton.addComponentListener(i_ComponentListener);
		removeTextButton.addComponentListener(i_ComponentListener);
			
		tableList.setTableModelComponent(new Table(tableList, 1), 1);
		tableList.setMultiSelection(true);
	}

	public void init()
	{
		// At init we set data
		
		// Add component listener for compnents that we want to listen

		i_SimpleTableModel = new SimpleTableModel();

		// Create Memory Only DataSet

		i_DataSet = new DataSet();
		ColumnInfo[] l_ColumnInfos = { new ColumnInfo("Choise", String.class), new ColumnInfo("Text", String.class)};

		i_DataSet.setRowInfo(new RowInfo(l_ColumnInfos));

		// Connect DataSet through DataSetTableModel to Table
		tableList.setTableModel(new DataSetTableModel(i_DataSet));

		// Make choise list
		// Table list is using i_SimpleTableModel as
		// data container  

		Table l_Table = tableList.getTableModelTable(1);
		l_Table.setTableModel(i_SimpleTableModel);

		// And connect that to first column of Table

		tableList.setTableModelComponent(l_Table, 1);

		// Add second editable column to table

		TextField l_Textfield = new TextField(tableList);
		l_Textfield.setEmptyIsNull(true);
		tableList.setTableModelComponent(l_Textfield, 2);

		// Make other Table to maintain 
		// Choise List. This is made so that they are
		// using same data container 

		tableSelect.setTableModel(i_SimpleTableModel);
		tableSelect.setMultiSelection(false);

		// Fill choise list 

		String lS_First = "First";

		i_SimpleTableModel.addValue(lS_First);
		i_SimpleTableModel.addValue("Second");
		i_SimpleTableModel.addValue("Third");
		i_SimpleTableModel.addValue("Fourth");

		// Fill table

		int li_row = i_DataSet.addRow();

		i_DataSet.setValueAt(lS_First, li_row, 1);
		i_DataSet.setValueAt("Some Text", li_row, 2);

		li_row = i_DataSet.addRow();

		i_DataSet.setValueAt(lS_First, li_row, 1);
		i_DataSet.setValueAt("More text", li_row, 2);
	}
	
	public Table getTableList()
	{
		return tableList;
	}
}
