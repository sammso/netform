package com.sohlman.webapp.netform;

import com.sohlman.netform.Button;
import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.Form;
import com.sohlman.netform.Table;
import com.sohlman.netform.TextField;
import com.sohlman.dataset.ColumnInfo;
import com.sohlman.dataset.DataSet;
import com.sohlman.dataset.RowInfo;
import com.sohlman.dataset.netform.DataSetTableModel;

/**
 * 
 * 
 * @author Sampsa Sohlman
 */
public class TableForm extends Form
{
	Button i_Button_Reload = new Button(this);
	Button i_Button_Save = new Button(this);
	Button i_Button_AddRow = new Button(this);
	Button i_Button_DeleteRow = new Button(this);
	DataSet i_DataSet;
	Table i_Table;

	int ii_counter = 1;

	ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if( a_Component == i_Button_AddRow )
			{
				i_DataSet.addRow();
			}
			else if( a_Component == i_Button_DeleteRow )
			{
				i_Table.deleteSelectedRows();
			}
			else if( a_Component == i_Button_Save )
			{
				
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
		i_Button_Reload.addComponentListener(i_ComponentListener);
		i_Button_Save.addComponentListener(i_ComponentListener);
		i_Button_AddRow.addComponentListener(i_ComponentListener);
		i_Button_DeleteRow.addComponentListener(i_ComponentListener);

		i_DataSet = new DataSet();
		ColumnInfo[] l_ColumnInfos =
			{ new ColumnInfo("First Name", String.class), new ColumnInfo("Last Name", String.class), new ColumnInfo("Birthday", String.class)};

		i_DataSet.setRowInfo(new RowInfo(l_ColumnInfos));
		i_Table = new Table(this,new DataSetTableModel(i_DataSet));
		i_Table.setMultiSelection(true);
		
		TextField l_Textfield_FirstName = new TextField(i_Table);
		l_Textfield_FirstName.setEmptyIsNull(true);
		i_Table.setTableModelComponent(l_Textfield_FirstName,1);
		TextField l_Textfield_LastName = new TextField(i_Table);
		i_Table.setTableModelComponent(l_Textfield_LastName,2);

		int li_row = i_DataSet.addRow();

		i_DataSet.setValueAt("Sampsa", li_row, 1);
		i_DataSet.setValueAt("Sohlman", li_row, 2);

		li_row = i_DataSet.addRow();

		i_DataSet.setValueAt("Gabriela", li_row, 1);
		i_DataSet.setValueAt("Ortiz Piña", li_row, 2);
	}

	public void startService()
	{
	};
	public void endService()
	{
	};
	
	public Button getButtonAddRow()
	{
		return i_Button_AddRow;
	}
	
	public Button getButtonDeleteRow()
	{
		return i_Button_DeleteRow;
	}

	public Button getButtonSave()
	{
		return i_Button_Save;
	}

	public Button getButtonReload()
	{
		return i_Button_Reload;
	}

	public Table getTable()
	{
		return i_Table;
	}
}
