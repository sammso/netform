package com.sohlman.webapp.netform;

import com.sohlman.netform.Form;
import com.sohlman.netform.component.Button;
import com.sohlman.netform.component.LongField;
import com.sohlman.netform.component.TextField;
import com.sohlman.netform.component.table.SimpleTableModel;
import com.sohlman.netform.component.table.Table;

/**
 * @author Sampsa Sohlman
 */
public class TagTestForm extends Form
{
	public TextField textField = new TextField(this);
	public Button button = new Button(this);
	public LongField longField = new LongField(this);
	public Table table = new Table(this);
		
	/**
	 * @see com.sohlman.netform.Form#init()
	 */
	public void init()
	{
		SimpleTableModel l_SimpleTableModel = new SimpleTableModel(new Object[] {"First","Second","Third","Fourth","Fifth"});
		table.setTableModel(l_SimpleTableModel);
	}
}