package com.sohlman.netform.component;

import java.util.ArrayList;
import java.util.Iterator;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;

/**
 * Container is package of components, it is like subform.
 * @author  Sampsa Sohlman
 * @version 2002-12-28
 */
public class Container extends Component
{
	public Container(Form a_Form)
	{
		super(a_Form);
	}

	public Container(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}
	
	private ArrayList iAL_Components;
	    
	protected void addComponent(Component a_Component)    
	{
		if (a_Component != null)
		{
			a_Component.setParent(this);
			if (iAL_Components == null)
			{
				iAL_Components = new ArrayList();
			}
			if(iAL_Components==null)
			{
				iAL_Components = new ArrayList();
			}
			
			iAL_Components.add(a_Component);
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
	
	
	/**
	 * @see com.sohlman.netform.Component#getChildComponents()
	 */
	protected Iterator getChildComponents()
	{
		if(iAL_Components==null)
		{
			return null;
		}
		else
		{
			return iAL_Components.iterator();
		}
	}

}
