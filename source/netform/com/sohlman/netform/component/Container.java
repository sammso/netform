package com.sohlman.netform.component;

import java.util.Vector;

import com.sohlman.netform.Component;

/**
 *
 * @author  Sampsa Sohlman
 * @version 2002-12-28
 */
public class Container extends Component
{

	public Container(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}
	
	private Vector iVe_Components;
	    
	protected void addComponent(Component a_Component)    
	{
		if (a_Component != null)
		{
			a_Component.setParent(this);
			if (iVe_Components == null)
			{
				iVe_Components = new Vector();
			}
			if(iVe_Components==null)
			{
				iVe_Components = new Vector();
			}
			
			iVe_Components.add(a_Component);
		}
	}    
	
	public Component cloneComponent()
	{
		throw new IllegalAccessError("At moment clone component is not supported");
	}
		
	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		// Here nothing tobe syncronized 
	}
}