/*
NetForm Library
---------------
Copyright (C) 2001-2004 - Sampsa Sohlman, Teemu Sohlman

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
*/
package com.sohlman.netform;

import java.util.ArrayList;

/**
 * <p>Portlet is part of webpage, which can be part of many webpages. Like form
 * it can be attached to webpage, but it also can be whole application/website
 * wide component.
 * <p>In NetForm Portlet is like independed {@link Form Form} inside
 * form. Portlets makes NetForm also tool for Portal services.  
 * 
 * @author Sampsa Sohlman
 * @version 2004-07-03
 */
public abstract class Portlet extends Component
{
	/**
	 * @param a_Form
	 */
	public Portlet(Form a_Form)
	{
		super(a_Form);
	}

	private ArrayList iAL_Components = null;
    
	protected void addComponent(Component a_Component)    
	{
		if (a_Component != null)
		{
			a_Component.setParent(this);
			if (iAL_Components == null)
			{
				iAL_Components = new ArrayList();
			}
			
			iAL_Components.add(a_Component);
		}
	} 

	/**
	 * Not supported
	 * @see com.sohlman.netform.Component#cloneComponent()
	 * @throws NotSupportedException if called
	 */
	public final Component cloneComponent()
	{
		throw new NotSupportedException("Cloning is not supported for Portlets");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public final void syncronizeData()
	{
		throw new NotSupportedException("SyncronizeData is not supported for Portlets");
	}
	
	public abstract void init();
	public abstract void startService();
	public abstract void endService();
	
	
	/**
	 * Question is asked if 
	 * 
	 * @return true 
	 */
	public boolean allowRemove()
	{
		return true;
	}	
}