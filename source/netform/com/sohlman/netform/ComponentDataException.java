package com.sohlman.netform;

/**
 * @author Sampsa Sohlman
 */
public class ComponentDataException extends Exception
{
	/**
	 * 
	 */
	public ComponentDataException()
	{
		super();
	}
	/**
	 * @param aS_Message
	 */
	public ComponentDataException(String aS_Message)
	{
		super(aS_Message);
	}
	/**
	 * @param aS_Message
	 * @param a_Throwable
	 */
	public ComponentDataException(String aS_Message, Throwable a_Throwable)
	{
		super(aS_Message, a_Throwable);
	}
	/**
	 * @param a_Throwable
	 */
	public ComponentDataException(Throwable a_Throwable)
	{
		super(a_Throwable);
	}
}
