package com.sohlman.webapp.netform.reflection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Sampsa Sohlman
 */
public class Person
{
	private String iS_FirstName = "";
	private String iS_LastName = "";
	private Collection iCo_PersonProperties = new ArrayList();
	/**
	 * @return Returns the iCo_PersonProperties.
	 */
	public Collection getPersonProperties()
	{
		return iCo_PersonProperties;
	}
	/**
	 * @param co_PersonProperties The iCo_PersonProperties to set.
	 */
	public void setPersonProperties(Collection aCo_PersonProperties)
	{
		iCo_PersonProperties = aCo_PersonProperties;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName()
	{
		return iS_FirstName;
	}
	
	/**
	 * @return Returns the firstName.
	 */
	public String getLastName()
	{
		return iS_LastName;
	}	
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String aS_FirstName)
	{
		if(aS_FirstName!=null && aS_FirstName.length() > 0 && aS_FirstName.length() <= 20)
		{
			iS_FirstName = aS_FirstName;
		}
		else
		{
			throw new IllegalArgumentException("Wrong first name");
		}
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String aS_LastName)
	{
		
		if(aS_LastName!=null && aS_LastName.length() > 0 && aS_LastName.length() <= 20)
		{
			iS_LastName = aS_LastName;
		}
		else
		{
			throw new IllegalArgumentException("Wrong last name");
		}		
	}
	
	public PersonProperty addPersonProperty()
	{
		PersonProperty l_PersonProperty = new PersonProperty();
		addPersonProperty(l_PersonProperty);	
		return l_PersonProperty;
	}
	
	public void addPersonProperty(PersonProperty a_PersonProperty)
	{
		a_PersonProperty.setPerson(this);
		
		iCo_PersonProperties.add(a_PersonProperty);
	}
}
