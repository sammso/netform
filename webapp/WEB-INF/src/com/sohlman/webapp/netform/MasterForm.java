package com.sohlman.webapp.netform;

import com.sohlman.netform.Form;

/**
 * @author Sampsa Sohlman
/*
 * Version: 19.11.2003
 *
 */
public class MasterForm extends Form
{
	/** 
	 * Needs to be implement this method
	 */
	public void init()
	{
	
	}
	 
	/**
	 * @see com.sohlman.netform.Form#isLoginRequired()
	 */
	public static boolean isLoginRequired()
	{
		return true;
	}

}
