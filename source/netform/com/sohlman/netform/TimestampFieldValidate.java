package com.sohlman.netform;

import java.sql.Timestamp;

/**
 * @author Sampsa Sohlman
 * 
 * @version 2004-02-23
 */
public class TimestampFieldValidate extends Validate
{
	private Timestamp i_Timestamp;
	
	public TimestampFieldValidate(Component a_Component, Timestamp a_Timestamp)
	{
		i_Component = a_Component;
		i_Timestamp = a_Timestamp;
	}
	
	public Timestamp getTimestamp()
	{
		return i_Timestamp;
	}
}
