package com.sohlman.webapp.netform;

import java.sql.Timestamp;

import com.sohlman.netform.Button;
import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.ComponentValidator;
import com.sohlman.netform.Form;
import com.sohlman.netform.NumberField;
import com.sohlman.netform.TextField;
import com.sohlman.netform.TimestampField;

/**
 * 
 * @author Sampsa Sohlman
 */
public class FieldForm extends Form
{
	TextField i_TextField = new TextField(this);
	NumberField i_NumberField = new NumberField(this, Integer.class);
	TimestampField i_TimestampField = new TimestampField(this);
	Button i_Button_IncreaceInt = new Button(this);
	Button i_Button_DecreaseInt = new Button(this);
	
	
	//Datefield l_Datefield = new Datefield(this);

	ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if(a_Component==i_Button_IncreaceInt)
			{
				i_NumberField.setInt(i_NumberField.getInt() + 1);
			}
			if(a_Component==i_Button_DecreaseInt)
			{
				i_NumberField.setInt(i_NumberField.getInt() - 1);
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

	public void init()
	{
		getTimestampField().setTimestamp(new Timestamp(System.currentTimeMillis()));
		getNumberField().setInt(11);
		i_Button_DecreaseInt.addComponentListener(i_ComponentListener);
		i_Button_IncreaceInt.addComponentListener(i_ComponentListener);
	}

	public void startService()
	{
	};
	
	public void endService()
	{
	};
	
	public TextField getTextField()
	{
		return i_TextField;
	}  

	public NumberField getNumberField()
	{
		return i_NumberField;
	}
	/**
	 * @return
	 */
	public Button getDecreaseInt()
	{
		return i_Button_DecreaseInt;
	}

	/**
	 * @return
	 */
	public Button getIncreaceInt()
	{
		return i_Button_IncreaceInt;
	}
	
	public TimestampField getTimestampField()
	{
		return i_TimestampField;	
	}
}