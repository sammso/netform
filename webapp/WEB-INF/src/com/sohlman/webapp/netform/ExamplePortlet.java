package com.sohlman.webapp.netform;

import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.Form;
import com.sohlman.netform.Portlet;
import com.sohlman.netform.component.Button;
import com.sohlman.netform.component.IntegerField;

/**
 * @author Sampsa Sohlman
 */
public class ExamplePortlet extends Portlet
{
	public IntegerField integerField = new IntegerField(this);
	public Button addButton = new Button(this);
	public Button decrButton = new Button(this);
	
	private ComponentListener i_ComponentListener = new ComponentListener()
	{
		/**
		 * @see com.sohlman.netform.ComponentListener#eventAction(com.sohlman.netform.Component)
		 */
		public void eventAction(Component a_Component)
		{
			if(addButton==a_Component)
			{
				int li_number = integerField.getInt();
				li_number++;
				integerField.setInt(li_number);
			}
			else if(decrButton==a_Component)
			{
				int li_number = integerField.getInt();
				li_number--;
				integerField.setInt(li_number);				
			}

		}
	};
	
	
	
	/**
	 * @param a_Form
	 */
	public ExamplePortlet(Form a_Form)
	{
		super(a_Form);
		addButton.addComponentListener(i_ComponentListener);
		decrButton.addComponentListener(i_ComponentListener);
	}
/**
	 * @see com.sohlman.netform.Portlet#init()
	 */
	public void init()
	{
		integerField.setInt(12);
	}

	/**
	 * @see com.sohlman.netform.Portlet#startService()
	 */
	public void startService()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.sohlman.netform.Portlet#endService()
	 */
	public void endService()
	{
		// TODO Auto-generated method stub

	}

}
