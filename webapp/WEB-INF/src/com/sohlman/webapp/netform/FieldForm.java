package com.sohlman.webapp.netform;

import java.sql.Timestamp;
import java.util.Calendar;

import com.sohlman.netform.Button;
import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.IntegerField;
import com.sohlman.netform.SimpleTableModel;
import com.sohlman.netform.Table;
import com.sohlman.netform.TextField;
import com.sohlman.netform.TimestampField;

/**
 * 
 * @author Sampsa Sohlman
 */ 
public class FieldForm extends MasterForm
{   
	public TextField textField = new TextField(this);
	public IntegerField integerField = new IntegerField(this);
	public TimestampField timestampField = new TimestampField(this);
	public Button increaseIntButton = new Button(this);
	public Button decreaseIntButton = new Button(this);
	public Button tomorrowButton = new Button(this);
	public Button yesterdayButton = new Button(this);
	public Button nextMonthButton = new Button(this);
	public Button previousMonthButton = new Button(this);
	public Button todayButton = new Button(this);
	public Button textToTableButton = new Button(this);
	public Button timestampToTableButton = new Button(this);
	public Button numberToTableButton = new Button(this);
	public Button deleteSelectedFromTableButton = new Button(this);
	public Table table = new Table(this, new SimpleTableModel());
	//Table i_Table_Second = new Table(this, new SimpleTableModel());

	//Datefield l_Datefield = new Datefield(this);

	ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if (a_Component == increaseIntButton) 
			{
				if (integerField.isValid())
				{ 
					integerField.setInt(integerField.getInt() + 1);
				}
 
			}
			else if (a_Component == decreaseIntButton)
			{
				if (integerField.isValid())
				{
					integerField.setInt(integerField.getInt() - 1);
				}
			}
			else if (a_Component == tomorrowButton)
			{
				if (timestampField.isValid())
				{
					Timestamp l_Timestamp = timestampField.getTimestamp();
					Calendar l_Calendar = Calendar.getInstance();
					l_Calendar.setTimeInMillis(l_Timestamp.getTime());
					l_Calendar.add(Calendar.DATE, 1);
					timestampField.setTimestamp(new Timestamp(l_Calendar.getTimeInMillis()));
				}
			}
			else if (a_Component == yesterdayButton)
			{
				if (timestampField.isValid())
				{
					Timestamp l_Timestamp = timestampField.getTimestamp();
					Calendar l_Calendar = Calendar.getInstance();
					l_Calendar.setTimeInMillis(l_Timestamp.getTime());
					l_Calendar.add(Calendar.DATE, -1);
					timestampField.setTimestamp(new Timestamp(l_Calendar.getTimeInMillis()));
				}
			}
			else if (a_Component == nextMonthButton)
			{
				if (timestampField.isValid())
				{
					Timestamp l_Timestamp = timestampField.getTimestamp();
					Calendar l_Calendar = Calendar.getInstance();
					l_Calendar.setTimeInMillis(l_Timestamp.getTime());
					l_Calendar.add(Calendar.MONTH, 1);
					timestampField.setTimestamp(new Timestamp(l_Calendar.getTimeInMillis()));
				}
			}
			else if (a_Component == previousMonthButton)
			{
				if (timestampField.isValid())
				{
					Timestamp l_Timestamp = timestampField.getTimestamp();
					Calendar l_Calendar = Calendar.getInstance();
					l_Calendar.setTimeInMillis(l_Timestamp.getTime());
					l_Calendar.add(Calendar.MONTH, -1);
					timestampField.setTimestamp(new Timestamp(l_Calendar.getTimeInMillis()));
				}
			}
			else if (a_Component == deleteSelectedFromTableButton)
			{
				table.deleteSelectedRows();
			}
			else if (a_Component == numberToTableButton)
			{
				if (integerField.isValid())
				{
					SimpleTableModel l_SimpleTableModel = (SimpleTableModel) table.getTableModel();
					String lS_Text = integerField.getText();
					l_SimpleTableModel.addValue(lS_Text);
				}
			}
			else if (a_Component == timestampToTableButton)
			{
				if (timestampField.isValid())
				{
					SimpleTableModel l_SimpleTableModel = (SimpleTableModel) table.getTableModel();
					l_SimpleTableModel.addValue(timestampField.getText());
				}
			}
			else if (a_Component == textToTableButton)
			{
				if (textField.isValid())
				{
					SimpleTableModel l_SimpleTableModel = (SimpleTableModel) table.getTableModel();
					l_SimpleTableModel.addValue(textField.getText());
				}
			}
		}
	};

	ComponentValidator i_ComponentValidator = new ComponentValidator()
	{
		public boolean isValid(Component a_Component)
		{
			if (a_Component == textField)
			{
				String lS_Text = textField.getText();

				if (lS_Text.trim().length() > 0 && lS_Text.trim().length() <= 12)
				{
					return true;
				}
			}
			return false;

		}
	};

	public void init()
	{
		// Set first dafault values
		// To timestamp field
		timestampField.setTimestamp(new Timestamp(System.currentTimeMillis()));

		// To NumberField
		integerField.setInt(11);

		textField.setComponentValidator(i_ComponentValidator);

		// Set multiselection true so that it is posible select
		// multiple items also server side
		table.setMultiSelection(true);
		// Register listeners
		decreaseIntButton.addComponentListener(i_ComponentListener);
		increaseIntButton.addComponentListener(i_ComponentListener);
		nextMonthButton.addComponentListener(i_ComponentListener);
		previousMonthButton.addComponentListener(i_ComponentListener);
		todayButton.addComponentListener(i_ComponentListener);
		tomorrowButton.addComponentListener(i_ComponentListener);
		yesterdayButton.addComponentListener(i_ComponentListener);
		textToTableButton.addComponentListener(i_ComponentListener);
		timestampToTableButton.addComponentListener(i_ComponentListener);
		numberToTableButton.addComponentListener(i_ComponentListener);
		deleteSelectedFromTableButton.addComponentListener(i_ComponentListener);
	}
	
	
	/**
	 * @see com.sohlman.netform.Form#formDestroyed()
	 */
	public void formDestroyed()
	{
		super.formDestroyed(); 
	}

	/**
	 * @see com.sohlman.netform.Form#allowFormChange()
	 */
	public boolean allowFormChange()
	{
		return super.allowFormChange();
	}

}