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
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Sampsa Sohlman
 * 
 * @version Apr 13, 2004
 */
/**
 * <p>All the components in NetForm framework are inherited from this class. </p>
 * <p>Component class provides basic functionality for all components. Such as:</p>
 * <ul>
 * <li>Event handling services with {@link ComponentListener ComponentListener}</li>
 * <li>Data validation services with {@link ComponentValidator ComponentValidator}</li>
 * </ul>
 * <p>Component can be created any time, but when it is destroyed or removed 
 *    {@link #dispose() Component.dispose()} method has to be called. If component
 *    is inherited then this method has to considered also.
 * 
 * @author  Sampsa Sohlman
 * @version 2003-11-20
 */
public abstract class Component
{
	private boolean ib_isVisible = true;
	private boolean ib_enabled = true;
	private boolean ib_newIsVisible;
	private boolean ib_newEnabled;
	private boolean ib_isVisibleChanged = false;
	private boolean ib_enabledChanged = false;
	private boolean ib_componentIsModified = false;
	private long il_count = 0;
	private int ii_notValidChildComponentCount = 0; // Component is valid if this is 0
	private String iS_Format = null;
	private boolean ib_isFormatRead = false;

	// For Storing component data
	// Component is also allowed to store it's
	// data by it self.
	// Component data makes posible to create
	// external Datamodels

	private ComponentData i_ComponentData;

	// For validation

	// First run it has to check if it is valid
	// Component assumes that they are 
	private boolean ib_isValid = true;

	private ArrayList iAL_Listeners = null;

	private ComponentValidator i_ComponentValidator = null;

	private Form i_Form;
	private String iS_ResponseName = null;
	private Component i_Component_Parent = null;

	/**
	 * Component constructor. All inherited classes has 
	 * to call super(a_Form) or super(a_Component_Parent)
	 */
	public Component(Form a_Form)
	{
		i_Form = a_Form;
		i_Form.addComponent(this);
		i_Component_Parent = null;
	}

	/**
	 * @see Component#Component(Form) 
	 */
	public Component(Component a_Component_Parent)
	{
		i_Form = a_Component_Parent.getForm();
		i_Component_Parent = a_Component_Parent;
		i_Component_Parent.addComponent(this);
	}

	/**
	 * @return String Response name of component. Unique name which is used to get values from
	 * servlet.
	 */
	public String getResponseName()
	{
		return i_Form.getComponentResponnsePrefix() + "SS" + hashCode();
	}

	/**
	 * Sets ComponentValidator for this Component
	 * <p> Only one validator is posible
	 * @param a_ComponentValidator
	 */
	public void setComponentValidator(ComponentValidator a_ComponentValidator)
	{
		i_ComponentValidator = a_ComponentValidator;
	}

	/**
	 * 
	 * 
	 * @return ComponentValidator
	 */
	public ComponentValidator getComponentValidator()
	{
		return i_ComponentValidator;
	}

	/**
	 * @return HttpServletRequest
	 */
	public final HttpServletRequest getHttpServletRequest()
	{
		return getForm().getHttpServletRequest();
	}

	/** Sets parent component.<br>
	 * Don't use this method in runtime.
	 * Sets Form object to component.
	 * @param a_Component parent component
	 */
	public void setParent(Component a_Component)
	{
		if (i_Component_Parent == null)
		{
			i_Component_Parent = a_Component;
		}
	}

	/**
	 * Gets parent component.
	 * @return reference to parent compnent if it is in root level returns null.
	 */

	public final Component getParent()
	{
		return i_Component_Parent;
	}

	/**<b>NetForm Internal use only</b><br>
	 * Sets Form object to component.
	 * @param Form object
	 */
	final void setForm(Form a_Form)
	{
		i_Form = a_Form;

		// Change state to old_value

		Iterator l_Iterator = getChildComponents();

		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				l_Component.setForm(a_Form);
			}
		}
	}

	/**
	 * @return Form reference to current form object
	 */
	public final Form getForm()
	{
		if (i_Form == null && i_Component_Parent != null)
		{
			i_Form = i_Component_Parent.getForm();
		}
		return i_Form;
	}

	/** Set Component visiblity state.<br>
	 * Note not visible components are imposible render, because DOM tree
	 * is not generated.
	 * @param boolean is visible or not
	 */

	public void setVisible(boolean ab_visible)
	{
		ib_newIsVisible = ab_visible;
		ib_isVisibleChanged = true;
	}

	private void fireEvent()
	{
		if (iAL_Listeners != null)
		{
			Iterator l_Iterator = iAL_Listeners.iterator();
			while (l_Iterator.hasNext())
			{
				((ComponentListener) l_Iterator.next()).eventAction(this);
			}
		}
	}

	public boolean isVisible()
	{
		if (ib_isVisibleChanged)
		{
			return ib_newIsVisible;
		}
		else
		{
			return ib_isVisible;
		}
	}

	private boolean haveNewValues()
	{
		return ib_isVisible && ib_enabled;
	}

	boolean haveEvents()
	{
		return ib_isVisible && ib_enabled && ib_componentIsModified;
	}

	public void setEnabled(boolean ab_enabled)
	{
		ib_newEnabled = ab_enabled;
		ib_enabledChanged = true;
	}

	public boolean isEnabled()
	{
		if (ib_enabledChanged)
		{
			return ib_newEnabled;
		}
		else
		{
			return ib_enabled;
		}
	}

	public void addComponentListener(ComponentListener a_ComponentListener)
	{
		if (iAL_Listeners == null)
		{
			iAL_Listeners = new ArrayList();
		}

		iAL_Listeners.add(a_ComponentListener);
	}

	protected abstract void addComponent(Component a_Component);

	final void generateEvent()
	{
		if (ib_componentIsModified)
		{
			fireEvent();
		}

		Iterator l_Iterator = getChildComponents();

		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{

				Component l_Component = (Component) l_Iterator.next();
				if (l_Component.haveEvents())
				{
					l_Component.generateEvent();
				}
			}
		}
	}

	protected final boolean parseValues()
	{
		clearModifiedStatus();
		boolean lb_isModified = false;
		if (haveNewValues())
		{
			if (checkIfNewValues())
			{
				componentIsModified();
				lb_isModified = true;
			}
		}
		return lb_isModified;
	}

	protected boolean checkIfNewValues()
	{
		// Put all values to child components
		boolean lb_componentIsModified = false;

		Iterator l_Iterator = getChildComponents();

		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				if (l_Component.parseValues()) 
				{
					lb_componentIsModified = true;
				}
			}
		}
		return lb_componentIsModified;		
	}

	final void lastIteration()
	{
		if (ib_isVisibleChanged)
		{
			ib_isVisible = ib_newIsVisible;
			ib_isVisibleChanged = false;
		}
		if (ib_enabledChanged)
		{
			ib_enabled = ib_newEnabled;
			ib_enabledChanged = false;
		}
		// Change state to old_value

		lastComponentIteration();

		Iterator l_Iterator = getChildComponents();
		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				l_Component.lastIteration();
			}
		}
	}

	/**
	 * To be overridden. This last to be called when 
	 * Before form execution
	 */
	protected void lastComponentIteration()
	{
		Iterator l_Iterator = getChildComponents();
		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				l_Component.lastIteration();
			}
		}
	}

	final void clearState()
	{
		Iterator l_Iterator = getChildComponents();
		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				l_Component.lastIteration();
			}
		}
	}

	final void componentIsModified()
	{
		ib_componentIsModified = true;
	}

	public final boolean isComponentModified()
	{
		return ib_componentIsModified;
	}

	protected final void clearModifiedStatus()
	{
		ib_componentIsModified = false;
	}

	/**
	 * Tell if data that component contains is ok.
	 * @return boolean true is no component validator defined or data is ok.
	 * false if data is not ok.
	 */
	public final boolean isValid()
	{
		// return ib_isValid;
		return ib_isValid && ii_notValidChildComponentCount == 0;
	}

	/**
	 * Tells validate status of this component, without child information
	 * 
	 * @return true if component is valid if child are not considered false if not
	 */
	public final boolean isValidWithoutChilds()
	{
		return ib_isValid;
	}

	final void setValid(boolean ab_isValid, Validate a_Validate)
	{
		boolean lb_isValid = ib_isValid;
		if (i_ComponentValidator != null && ab_isValid && a_Validate!=null)
		{
			lb_isValid = i_ComponentValidator.isValid(a_Validate);
		}
		else
		{
			lb_isValid = ab_isValid;
		}
		
		if(lb_isValid != ib_isValid) 
		{		
			// Component valid status has changed so we have to notify parent components
			if (i_Component_Parent != null)
			{
				i_Component_Parent.setChildValid(lb_isValid);
			}
			else
			{
				// we are now on root and parent is Form
				i_Form.setChildValid(lb_isValid);
			}
		}
		ib_isValid = lb_isValid; // Set the componet valid status
	}

	/**
	 * set Component valid status
	 * 
	 * @param ab_isValid
	 */
	public final void setValid(boolean ab_isValid)
	{
		setValid(ab_isValid, null);
	}

	private void setChildValid(boolean ab_isValid)
	{
		if(ab_isValid)
		{
			ii_notValidChildComponentCount--;
		}
		else
		{
			ii_notValidChildComponentCount++;
		}
		
		if (i_Component_Parent != null)
		{
			i_Component_Parent.setChildValid(ib_isValid);
		}
		else
		{
			i_Form.setChildValid(ab_isValid);
		}
	}

	/**
	 * Forces validation. This is usually done by setting data as it would
	 * be coming from browser inside components
	 */
	public void validate()
	{
		Iterator l_Iterator = getChildComponents();

		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{

				Component l_Component = (Component) l_Iterator.next();
				l_Component.validate();
			}
		}
	}

	protected void validate(Validate a_Validate)
	{
		setValid(true, a_Validate);
	}

	protected Iterator getChildComponents()
	{
		return null;
	}

	/**
	 * Set DataContainer to component. DataContainer
	 * Makes posiblity create data models.
	 * 
	 * @param a_ComponentData
	 */
	public void setComponentData(ComponentData a_ComponentData)
	{
		i_ComponentData = a_ComponentData;
	}

	public ComponentData getComponentData()
	{
		return i_ComponentData;
	}
	
	/**
	 * This shares listeners. This is made for Tables, example if table row want to get
	 * events
	 * 
	 * @param a_Component
	 */
	protected void shareComponentListenerFrom(Component a_Component)
	{
		iAL_Listeners = a_Component.iAL_Listeners;
	}

	/**
	 * Set data to this Component's {@link com.sohlman.netform.ComponentData ComponentData} object 
	 * If {@link com.sohlman.netform.ComponentData ComponentData} is not set is ignored
	 * @param a_Object Object
	 */
	protected void setData(Object a_Object)
	{
		if (i_ComponentData != null)
		{
			i_ComponentData.setData(a_Object);
		}
	}

	/** gets data from {@link com.sohlman.netform.ComponentData ComponentData}
	 * @return Object 
	 * @throws IllegalStateException if component data doesn't exists
	 */
	protected Object getData()
	{
		if (i_ComponentData != null)
		{
			return i_ComponentData.getData();
		}
		else
		{
			throw new IllegalStateException("Component object is not defined");
		}
	}

	final public int getCurrentState()
	{
		return i_Form.getCurrentState();
	}

	final protected void checkState(int ai_state, String aS_ErrorMsg)
	{
		int li_state = getCurrentState();
		if ((li_state & ai_state) == 0)
		{
			throw new IllegalStateException(aS_ErrorMsg);
		}
	}

	/** 
	 * Has component data
	 * @return true if component has ComponentData object or false if not
	 */
	protected boolean hasComponentData()
	{
		return i_ComponentData != null;
	}

	/**
	 * This should clone component
	 */
	public abstract Component cloneComponent();

	/**
	 * This cleans the object and all it's child
	 * Child classes has to remember call super if overriding this.
	 */
	public void dispose()
	{
		Iterator l_Iterator = getChildComponents();

		if (l_Iterator != null)
		{
			while (l_Iterator.hasNext())
			{

				Component l_Component = (Component) l_Iterator.next();
				l_Component.dispose();
			}
		}
		// If component was not valid then it status has to be reduced from 
		// parent components
		if(!ib_isValid)
		{
			
			Component l_Component = getParent();
			if(l_Component!=null)
			{
				l_Component.setChildValid(true);
			}
			else
			{
				getForm().setChildValid(true);
			}
			
		}
	}

	/**
	 * <b>JSP</b> Return String if component is valid othervice ""
	 * 
	 * @param a_String String to be returned
	 * @return  String if component is valid othervice ""
	 */
	public String getStringIfIsValid(String a_String)
	{
		return getStringIfIsValid(a_String, "");
	}
	/**
	 * <b>JSP</b> Return String if component is valid othervice ""
	 * 
	 * @param aS_Valid String to be returned when component is valid
	 * @param aS_NotValid String to be returned when component is not valid
	 * @return  String if component is valid othervice ""
	 */	
	public String getStringIfIsValid(String aS_Valid, String aS_NotValid)
	{
		if(isValid())
		{
			return aS_Valid;
		}
		else
		{
			return aS_NotValid;
		}
	}
	/**
	 * <b>JSP</b> Return String if component is not valid othervice ""
	 * 
	 * @param a_String String to be returned
	 * @return  String if component is valid othervice ""
	 */
	public String getStringIfIsNotValid(String a_String)
	{
		return getStringIfIsValid("", a_String);
	}	
	
	/**
	 * This used to syncronize data with ComponentData
	 */
	public abstract void syncronizeData();
	
	
	/**
	 * <b>JSP settings</b>
	 * Set format for component, if component is not supporting formatting
	 * this has no effect
	 * 
	 * @param aS_Format
	 */
	public void setFormat(String aS_Format)
	{
		iS_Format = aS_Format;
	}
	
	/**
	 * It reads column format
	 * <p>
	 * Format can be set with {@link #setFormat(String) setFormat()} and if 
	 * it is not set then it tries to read it form parent component and finally
	 * from Form. This parent search will be done only once. 
	 * 
	 * @param a_Class Class which type format should be for
	 * @return String containing format information
	 */
	final protected String getFormat(Class a_Class)
	{
		if(iS_Format==null && ib_isFormatRead == false)
		{
			if( getParent()!=null)
			{
				iS_Format = getParent().getFormatFromParent(a_Class, getComponentData());
				ib_isFormatRead = true;
			}
			else
			{
				// TODO support form level formatting
				// getParentFormat (Class a_Class)
				iS_Format = null;
				ib_isFormatRead = true;
				return null;// getForm().getFormatFromForm(a_Class);
			}			
		}
		return iS_Format;
	}
	
	/**
	 * 
	 * @param a_Class Class which type format should be for
	 * @param a_ComponentData which may hold information about the which kind of format 
	 * it should be, example in table it holds column number
	 * @return String String containing format information for current 
	 * 	Class or ComponentData, null if nothing found
	 */
	protected String getFormatFromParent(Class a_Class, ComponentData a_ComponentData)
	{
		if( getParent()!=null)
		{
			return getParent().getFormatFromParent(a_Class, getComponentData());
		}
		else
		{
			// TODO support form level formatting
			// getParentFormat (Class a_Class)
			return null;// getForm().getFormatFromForm(a_Class);
		}
	}
}
