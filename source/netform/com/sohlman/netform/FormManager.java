package com.sohlman.netform;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Form manager handles all forms.
 * 
 * @author Sampsa Sohlman
/*
 * Version: 27.8.2003
 *
 */
public class FormManager implements HttpSessionListener
{

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent a_HttpSessionEvent)
	{
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent a_HttpSessionEvent)
	{
			
	}
}
