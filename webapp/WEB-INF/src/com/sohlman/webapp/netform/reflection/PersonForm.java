package com.sohlman.webapp.netform.reflection;

import com.sohlman.netform.Component;
import com.sohlman.netform.ComponentListener;
import com.sohlman.netform.component.Button;
import com.sohlman.netform.component.ReflectionData;
import com.sohlman.netform.component.TextField;
import com.sohlman.netform.component.table.ObjectCollectionTableModel;
import com.sohlman.netform.component.table.Table;
import com.sohlman.webapp.netform.MasterForm;

/**
 * @author Sampsa Sohlman
 */
public class PersonForm extends MasterForm
{
	public TextField firstNameTextField = new TextField(this,true,true,true);
	public TextField lastNameTextField = new TextField(this,true,true,true);
	public Table propertiesTable = new Table(this);
	public Button addButton = new Button(this);
	public Button removeButton = new Button(this);

	
	public ComponentListener i_ComponentListener = new ComponentListener()
	{
		/**
		 * @see com.sohlman.netform.ComponentListener#eventAction(com.sohlman.netform.Component)
		 */
		public void eventAction(Component a_Component)
		{
			if(a_Component==addButton)
			{
				if(propertiesTable.getTableModel().getRowCount() < 10)
				{
					propertiesTable.addRow();
					if(propertiesTable.getTableModel().getRowCount() >= 10)
					{
						addButton.setEnabled(false);
					}
					removeButton.setEnabled(true);
				}
			}
			else if(a_Component==removeButton)
			{
				if(propertiesTable.getTableModel().getRowCount() > 0)
				{
					propertiesTable.deleteSelectedRows();
					if(propertiesTable.getTableModel().getRowCount() >= 10)
					{
						removeButton.setEnabled(false);
					}
					addButton.setEnabled(true);
				}				
			}
		}
	};
	
	
	public PersonForm()
	{
		propertiesTable.setTableModelComponent(new TextField(propertiesTable), 1);
		propertiesTable.setTableModelComponent(new TextField(propertiesTable), 2);
		addButton.addComponentListener(i_ComponentListener);
		removeButton.addComponentListener(i_ComponentListener);	
	}
	
	/**
	 * @see com.sohlman.netform.Form#init()
	 */
	public void init()
	{
		super.init();
		//
		// Get Handle to Person
		//
		Person l_Person = getPerson();
		//
		// Components to Person fields
		//
		firstNameTextField.setComponentData(new ReflectionData(l_Person, "getFirstName", "setFirstName"));
		lastNameTextField.setComponentData(new ReflectionData(l_Person, "getLastName", "setLastName"));
		ObjectCollectionTableModel l_ObjectCollectionTableModel = new ObjectCollectionTableModel();
		l_ObjectCollectionTableModel.setCollection(l_Person.getPersonProperties());
		l_ObjectCollectionTableModel.setColummnCount(2);
		l_ObjectCollectionTableModel.setCollectoinItemClass(PersonProperty.class);
		l_ObjectCollectionTableModel.setParentObjectForCollectionItem(l_Person);
		l_ObjectCollectionTableModel.assignSetParentMethodForItem("setPerson");
		l_ObjectCollectionTableModel.assignCollectionColumn("Name",1);
		l_ObjectCollectionTableModel.assignCollectionColumn("Value",2);
		propertiesTable.setTableModel(l_ObjectCollectionTableModel);
	}
	
	public Person getPerson()
	{
		Person l_Person = (Person)getHttpSession().getAttribute(this.getClass().getName());
		if(l_Person == null)
		{
			l_Person = new Person();
			l_Person.addPersonProperty();
			getHttpSession().setAttribute(this.getClass().getName(), l_Person);
		}		
		return l_Person;
	}
}
