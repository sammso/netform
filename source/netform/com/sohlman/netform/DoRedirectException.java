package com.sohlman.netform;

/**
 * @author Sampsa Sohlman
/*
 * Version: 2003-11-19
 *
 */
public class DoRedirectException extends Exception
{
	private String iS_Page;
	public DoRedirectException(String aS_Page)
	{
		iS_Page = aS_Page;
	}
	
	public String getPage()
	{
		return iS_Page;
	}
}
