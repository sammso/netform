package com.sohlman.webapp.netform;

import com.sohlman.dataset.ColumnInfo;
import com.sohlman.dataset.DataSet;
import com.sohlman.dataset.RowInfo;
import com.sohlman.dataset.netform.DataSetTableModel;
import com.sohlman.netform.Button;
import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.Table;
import com.sohlman.netform.TextField;

/**
 * @author Sampsa Sohlman
 */
public class TableForm extends MasterForm
{
	public Button reloadButton = new Button(this);
	public Button addRowButton = new Button(this);
	public Button deleteRowButton = new Button(this);
	public Table table;

	private int ii_counter = 1;
	private DataSet i_DataSet;
	
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
				table.deleteSelectedRows();
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

		i_DataSet = new DataSet();
		ColumnInfo[] l_ColumnInfos =
			{ new ColumnInfo("First Name", String.class), new ColumnInfo("Last Name", String.class), new ColumnInfo("Birthday", String.class)};

		i_DataSet.setRowInfo(new RowInfo(l_ColumnInfos));
		table = new Table(this,new DataSetTableModel(i_DataSet));
		table.setMultiSelection(true);
		
		TextField l_Textfield_FirstName = new TextField(table);
		l_Textfield_FirstName.setEmptyIsNull(true);
		table.setTableModelComponent(l_Textfield_FirstName,1);
		TextField l_Textfield_LastName = new TextField(table);
		table.setTableModelComponent(l_Textfield_LastName,2);

		int li_row = i_DataSet.addRow();

		i_DataSet.setValueAt("Sampsa", li_row, 1);
		i_DataSet.setValueAt("Sohlman", li_row, 2);

		li_row = i_DataSet.addRow();

		i_DataSet.setValueAt("Gabriela", li_row, 1);
		i_DataSet.setValueAt("Ortiz Piña", li_row, 2);
	}
}
