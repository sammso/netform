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
		if(aS_Name!=null && aS_Name.length() > 5  && aS_Name.length() < 20 )
		{
			iS_Name = aS_Name;
		}
		else
		{
			throw new IllegalArgumentException("Size should be 6 - 19");
		}		
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
		if(aS_Value!=null && aS_Value.length() > 5  && aS_Value.length() < 20 )
		{
			iS_Value = aS_Value;
		}
		else
		{
			throw new IllegalArgumentException("Size should be 6 - 19");
		}
	}
	
	private String iS_Name = null;
	private String iS_Value = null;
}
