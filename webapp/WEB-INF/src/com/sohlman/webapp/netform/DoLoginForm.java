package com.sohlman.webapp.netform;
import com.sohlman.netform.LoginForm;


/**
 * @author Sampsa Sohlman
/*
 * Version: 19.11.2003
 *
 */
public class DoLoginForm extends LoginForm
{

	/**
	 * @see com.sohlman.netform.LoginForm#doLogin(java.lang.String, java.lang.String)
	 */
	public Object doLogin(String aS_LoginName, String aS_Password)
	{
		return aS_LoginName;
	}

}
