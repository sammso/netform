package com.sohlman.netform;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-02-23
 */
public class DoubleFieldValidate extends Validate
{
	private Double i_Double;
	
	public DoubleFieldValidate(Component a_Component, Double a_Double)
	{
		i_Component = a_Component;
		i_Double = a_Double;
	}
	
	public Double getDouble()
	{
		return i_Double;
	}
}
