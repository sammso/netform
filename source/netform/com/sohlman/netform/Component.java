package com.sohlman.netform;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>All the components in NetForm framework are inherited from this class. </p>
 * <p>Component class provides basic functionality for all components. Such as:</p>
 * <ul>
 * <li>Event handling services with {@link ComponentListener ComponentListener}</li>
 * <li>Data validation services with {@link ComponentValidator ComponentValidator}</li>
 * <li>Validation services</li>
 * </ul>
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

	// For validation
	// First run it has to check if it is valid
	private boolean ib_isValid = false;

	private ArrayList iAL_Components = null;
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
		return i_Form.getComponentResponnsePrefix() + "SS"  + hashCode();
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
	 * @return HttpServletRequest.
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
		if (iAL_Components != null)
		{
			// Change state to old_value

			Iterator l_Iterator = iAL_Components.iterator();
			Component l_Component;
			while (l_Iterator.hasNext())
			{
				l_Component = (Component) l_Iterator.next();
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
			Iterator l_Enumeration = iAL_Listeners.iterator();
			while (l_Enumeration.hasNext())
			{
				((ComponentListener) l_Enumeration.next()).eventAction(
					this);
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

	public boolean haveEvents()
	{
		return (ib_isVisible || ib_enabled);
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
		if (iAL_Components != null)
		{
			// Change state to old_value

			Iterator l_Iterator = iAL_Components.iterator();
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

	public abstract boolean checkIfNewValues();

	public void changeVisibleEnable()
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
		if (iAL_Components != null)
		{
			// Change state to old_value

			Iterator l_Iterator = iAL_Components.iterator();
			Component l_Component;
			while (l_Iterator.hasNext())
			{
				l_Component = (Component) l_Iterator.next();
				l_Component.changeVisibleEnable();
			}
		}
	}

	final void clearState()
	{
		if (iAL_Components != null)
		{
			// Change state to old_value

			Iterator l_Iterator = iAL_Components.iterator();
			Component l_Component;
			while (l_Iterator.hasNext())
			{
				l_Component = (Component) l_Iterator.next();
				l_Component.changeVisibleEnable();
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

	final void clearModifiedStatus()
	{
		ib_componentIsModified = false;
	}

	/**
	 * Tell if data that component contains is ok.
	 * @return boolean true is no component validator defined or data is ok.
	 * false if data is not ok.
	 */
	public boolean isValid()
	{
		return ib_isValid;
	}

	final void setValid(boolean ab_isValid, boolean ab_useValidator)
	{
		if (i_ComponentValidator != null && ab_isValid && ab_useValidator)
		{
			ib_isValid = i_ComponentValidator.isValid(this) && ab_isValid;
		}
		else
		{
			ib_isValid = ab_isValid;
		}
		if (i_Component_Parent != null)
		{
			i_Component_Parent.setParentValid(ib_isValid);
		}
		else
		{
			// we are now on root and parent is Form
			i_Form.setValid(ib_isValid);
		}
	}

	public final void setValid(boolean ab_isValid)
	{
		setValid(ab_isValid, false);
	}

	private void setParentValid(boolean ab_isValid)
	{
		if (ib_isValid == true && ab_isValid == false)
		{
			ib_isValid = ab_isValid;
			if (i_Component_Parent != null)
			{
				i_Component_Parent.setParentValid(ib_isValid);
			}
		}
		else if (ib_isValid == false && ab_isValid == true)
		{
			if(iAL_Components!=null)
			{
				Iterator l_Enumeration = iAL_Components.iterator();
				boolean lb_isValid = true;
				while (l_Enumeration.hasNext() && lb_isValid)
				{
					Component l_Component = (Component) l_Enumeration.next();
					lb_isValid = l_Component.isValid();
				}		
				ib_isValid = lb_isValid;		
			}

			if (i_Component_Parent != null)
			{
				i_Component_Parent.setParentValid(ib_isValid);
			}
		}
	}

	public final void validate()
	{
		setValid(true, true);
	}
	
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
