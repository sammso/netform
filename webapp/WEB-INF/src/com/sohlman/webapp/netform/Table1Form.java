package com.sohlman.webapp.netform;

import java.sql.Timestamp;

import com.sohlman.dataset.ColumnInfo;
import com.sohlman.dataset.DataSet;
import com.sohlman.dataset.RowInfo;
import com.sohlman.dataset.netform.DataSetTableModel;
import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.Utils;
import com.sohlman.netform.Validate;
import com.sohlman.netform.component.Button;
import com.sohlman.netform.component.IntegerField;
import com.sohlman.netform.component.TextField;
import com.sohlman.netform.component.TextFieldValidate;
import com.sohlman.netform.component.TimestampField;
import com.sohlman.netform.component.table.Table;
import com.sohlman.netform.component.table.TableComponentData;

/**
 * @author Sampsa Sohlman
 */
public class Table1Form extends MasterForm
{
	public Button reloadButton = new Button(this);
	public Button addRowButton = new Button(this);
	public Button insertRowButton = new Button(this);
	public Button deleteRowButton = new Button(this);
	public Table table = new Table(this);

	private int ii_counter = 1;
	private DataSet i_DataSet;  
	
	// Event handler
	
	private ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if( a_Component == addRowButton )
			{
				// Application is on internet so lets limit the 

				if(i_DataSet.getRowCount() <= 50)
				{
					table.addRow();
				}
			}
			else if( a_Component == insertRowButton )
			{
				if(i_DataSet.getRowCount() <= 50)
				{				
					table.insertRowBeforeFirstSelection();
				}
			}			
			else if( a_Component == deleteRowButton )
			{
				table.deleteSelectedRows();
			}
			
		}
	};
	
	public ComponentValidator i_ComponentValidator = new ComponentValidator()
	{
		public boolean isValid(Validate a_Validate)
		{
			// Validate table columns
			if(a_Validate.getSource().getParent() == table )
			{
				TableComponentData l_TableComponentData = (TableComponentData)a_Validate.getSource().getComponentData();
				// Columns are valid if they contains more than 3 charachers
				if(l_TableComponentData.getColumn()==2 || l_TableComponentData.getColumn()==3 ) // First Name
				{
					TextFieldValidate l_TextFieldValidate = (TextFieldValidate) a_Validate;
					String l_String = l_TextFieldValidate.getText();
					if(l_String!=null && l_String.length() > 3)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			return false;
		}
	};

	public void init()
	{
		reloadButton.addComponentListener(i_ComponentListener);
		addRowButton.addComponentListener(i_ComponentListener);
		insertRowButton.addComponentListener(i_ComponentListener);
		deleteRowButton.addComponentListener(i_ComponentListener);
		

		i_DataSet = new DataSet();
		ColumnInfo[] l_ColumnInfos =
			{ new ColumnInfo("Person id", Integer.class), new ColumnInfo("First Name", String.class), new ColumnInfo("Last Name", String.class), new ColumnInfo("Birthday", Timestamp.class)};

		i_DataSet.setRowInfo(new RowInfo(l_ColumnInfos));
		
		table.setTableModel(new DataSetTableModel(i_DataSet));
		table.setMultiSelection(true);

		IntegerField l_IntegerField = new IntegerField(table);
		table.setTableModelComponent(l_IntegerField,1);
		
		TextField l_Textfield_FirstName = new TextField(table);
		l_Textfield_FirstName.setComponentValidator(i_ComponentValidator);
		table.setTableModelComponent(l_Textfield_FirstName,2);
		
		TextField l_Textfield_LastName = new TextField(table);
		l_Textfield_LastName.setComponentValidator(i_ComponentValidator);
		table.setTableModelComponent(l_Textfield_LastName,3);
		
		TimestampField l_TimestampField = new TimestampField(table);
		table.setTableModelComponent(l_TimestampField,4);

		int li_row = i_DataSet.addRow();

		i_DataSet.setValueAt(new Integer(1), li_row, 1);
		i_DataSet.setValueAt("Sampsa", li_row, 2);
		i_DataSet.setValueAt("Sohlman", li_row, 3);
		i_DataSet.setValueAt(Utils.stringToTimestamp("1973-05-22 00:00:00","yyyy-MM-dd hh:mm:ss"), li_row, 4);

		li_row = i_DataSet.addRow();
		
		i_DataSet.setValueAt(new Integer(2), li_row, 1);
		i_DataSet.setValueAt("Gabriela", li_row, 2);
		i_DataSet.setValueAt("Ortiz Piña", li_row, 3);
		i_DataSet.setValueAt(Utils.stringToTimestamp("1979-01-25 00:00:00","yyyy-MM-dd hh:mm:ss"), li_row, 4);
	}
}
