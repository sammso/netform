package com.sohlman.webapp.netform;

import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.component.Button;
import com.sohlman.netform.component.TextField;
import com.sohlman.netform.component.table.SimpleTableModel;
import com.sohlman.netform.component.table.Table;
import com.sohlman.netform.component.table.TableComponentData;

/**
 * @author Sampsa Sohlman
 */
public class Table3Form extends MasterForm
{
	private ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if (a_Component.getParent() == table)
			{
				TableComponentData l_TableComponentData = (TableComponentData) a_Component.getComponentData();
				int li_row = l_TableComponentData.getRow();
				int li_column = l_TableComponentData.getColumn();
				if (li_column == 1)
				{
					SimpleTableModel l_SimpleTableModel = (SimpleTableModel) table.getTableModel();
					textField.setText((String)l_SimpleTableModel.getValueAt(li_row, li_column));
				}
			}
		}
	};
	
	public TextField textField = new TextField(this);
	public Table table = new Table(this);
	
	public Table3Form()
	{
		Button l_Button = new Button(table);
		l_Button.addComponentListener(i_ComponentListener);
		table.setTableModelComponent(l_Button, 1);
		table.setMultiSelection(true);
	}	

	public void init()
	{
		String[] lS_Strings = { "First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh" };
		table.setTableModel(new SimpleTableModel(lS_Strings));
	}
}