package com.sohlman.webapp.netform;

import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.Form;
import com.sohlman.netform.component.Button;
import com.sohlman.netform.component.Container;
import com.sohlman.netform.component.TextField;

/**
 * 
 * @author Sampsa Sohlman
 * 
 * @version 2004-06-13
 */
public class ContainerForm extends MasterForm
{
	// Note in this example it the order of these declarations are important. They
	// don't have to be same but it may cause bad behavior if they are changed.
	// The reason is java grammar
	
	private ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if(a_Component == firstContainer.toSecondButton)
			{
				firstContainer.setVisible(false);
				secondContainer.setVisible(true);
			}
			if(a_Component == secondContainer.toFirstButton)
			{
				firstContainer.setVisible(true);
				secondContainer.setVisible(false);				
			}
		}
	};
	
	public class FirstContainer extends Container
	{
		public Button toSecondButton = new Button(this);
		public TextField firstTextField = new TextField(this);
		public TextField secondTextField  = new TextField(this);
		
		FirstContainer(Form a_Form)
		{
			super(a_Form);
			toSecondButton.addComponentListener(i_ComponentListener);
		};
	};
	
	public class SecondContainer extends Container
	{
		public Button toFirstButton = new Button(this);
		public TextField firstTextField = new TextField(this);
		public TextField secondTextField  = new TextField(this);
		
		SecondContainer(Form a_Form)
		{
			super(a_Form);			
			setVisible(false);
			toFirstButton.addComponentListener(i_ComponentListener);
		};		
	};

	public FirstContainer firstContainer = new FirstContainer(this);
	public SecondContainer secondContainer = new SecondContainer(this);

}
