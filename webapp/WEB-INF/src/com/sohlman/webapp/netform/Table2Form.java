package com.sohlman.webapp.netform;

import com.sohlman.dataset.ColumnInfo;
import com.sohlman.dataset.DataSet;
import com.sohlman.dataset.RowInfo;
import com.sohlman.dataset.netform.DataSetTableModel;
import com.sohlman.netform.Button;
import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.SimpleTableModel;
import com.sohlman.netform.Table;
import com.sohlman.netform.TextField;

/**
 * @author Sampsa Sohlman
 */
public class Table2Form extends MasterForm
{
	public Button reloadButton = new Button(this);
	public Button addRowButton = new Button(this);
	public Button deleteRowButton = new Button(this);
	public Button addButton = new Button(this);
	public Button removeButton = new Button(this);	
	
	public TextField textField = new TextField(this);
	public Table tableList;
	public Table tableSelect;
	
	

	private int ii_counter = 1;
	private DataSet i_DataSet;
	private SimpleTableModel i_SimpleTableModel;
	
	private ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if( a_Component == addRowButton )
			{
				i_DataSet.addRow();
			}
			else if( a_Component == deleteRowButton )
			{
				tableList.deleteSelectedRows();
			}
		}
	};

	ComponentValidator i_ComponentValidator = new ComponentValidator()
	{
		public boolean isValid(Component a_Component)
		{
			return true;
		}
	};

	private String formatNumber(int ai_number)
	{
		StringBuffer lSb_Number = new StringBuffer();
		String lS_Number = "" + ai_number;

		for (int li_c = 0; li_c < 5 - lS_Number.length(); li_c++)
		{
			lSb_Number.append("0");
		}
		lSb_Number.append(lS_Number);
		return lSb_Number.toString();
	}
 
	public void init()
	{
		reloadButton.addComponentListener(i_ComponentListener);
		addRowButton.addComponentListener(i_ComponentListener);
		deleteRowButton.addComponentListener(i_ComponentListener);
		
		i_SimpleTableModel = new SimpleTableModel();
	
		i_DataSet = new DataSet();
		ColumnInfo[] l_ColumnInfos =
			{ new ColumnInfo("Choise", String.class), new ColumnInfo("Text", String.class)};

		i_DataSet.setRowInfo(new RowInfo(l_ColumnInfos));
		tableList = new Table(this,new DataSetTableModel(i_DataSet));
		tableList.setMultiSelection(true);
		
		
		// l_IntegerField.setEmptyIsNull(true);
		// Let's make selection list 
		
		Table l_Table = new Table(tableList, i_SimpleTableModel);
		tableList.setTableModelComponent(l_Table, 1);
		
		TextField l_Textfield = new TextField(tableList);
		l_Textfield.setEmptyIsNull(true);
		tableList.setTableModelComponent(l_Textfield,2);
		
		
		String lS_First = "First";
		String lS_Second = "Second";
		
		i_SimpleTableModel.addValue(lS_First);
		i_SimpleTableModel.addValue(lS_Second);			

		int li_row = i_DataSet.addRow();

		i_DataSet.setValueAt(lS_First, li_row, 1);
		i_DataSet.setValueAt("Some Text", li_row, 2);
		
		li_row = i_DataSet.addRow();
		
		i_DataSet.setValueAt(lS_First, li_row, 1);
		i_DataSet.setValueAt("More text", li_row, 2);
	}
}
