package com.sohlman.netform.component;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.NotSupportedException;

/**
 * Label is only showing data, but still having compnent properties.
 * 
 * @author Sampsa Sohlman
 */
public class Label extends Component
{
	private String iS_Text;

	public Label(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public Label(Form a_Form)
	{
		super(a_Form);
	}	
	
	/**
	 * @see com.sohlman.netform.Component#addComponent(com.sohlman.netform.Component)
	 */
	protected void addComponent(Component a_Component)
	{
		throw new NotSupportedException("Label doesn't support sub components");
	}

	/**
	 * @see com.sohlman.netform.Component#cloneComponent()
	 */
	public Component cloneComponent()
	{
		
		Label l_Label = new Label(getParent());
		l_Label.iS_Text = iS_Text;
	
		return l_Label;
	}

	/**
	 * @param aS_Text
	 */
	public void setText(String aS_Text)
	{
		iS_Text = aS_Text;
	}
	
	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if(hasComponentData())
		{			
			iS_Text = getData().toString();
		}
	}

}
