package com.sohlman.netform;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-02-23
 */
public class IntegerFieldValidate extends Validate
{
	private Integer i_Integer;
	
	public IntegerFieldValidate(Component a_Component, Integer a_Integer)
	{
		i_Component = a_Component;
		i_Integer = a_Integer;
	}
	
	public Integer getInteger()
	{
		return i_Integer;
	}
}
