package com.sohlman.webapp.netform.reflection;

/**
 * @author Sampsa Sohlman
 */
public class PersonProperty
{
	private Person i_Person = null;
	
	public void setPerson(Person a_Person)
	{
		i_Person = a_Person;
	}
	
	public Person getPerson()
	{
		return i_Person;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return iS_Name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String aS_Name)
	{
		iS_Name = aS_Name;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue()
	{
		return iS_Value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String aS_Value)
	{
		iS_Value = aS_Value;
	}
	
	private String iS_Name = null;
	private String iS_Value = null;
}
