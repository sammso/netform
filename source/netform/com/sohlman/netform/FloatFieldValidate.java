package com.sohlman.netform;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-02-23
 */
public class FloatFieldValidate extends Validate
{
	private Float i_Float;
	
	public FloatFieldValidate(Component a_Component, Float a_Float)
	{
		i_Component = a_Component;
		i_Float = a_Float;
	}
	
	public Float getFloat()
	{
		return i_Float;
	}
}
