package com.sohlman.netform;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-02-23
 */
public class LongFieldValidate extends Validate
{
	private Long i_Long;
	
	public LongFieldValidate(Component a_Component, Long a_Long)
	{
		i_Component = a_Component;
		i_Long = a_Long;
	}
	
	public Long getLong()
	{
		return i_Long;
	}
}
