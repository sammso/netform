/*
NetForm Library
---------------
Copyright (C) 2001-2005 - Sampsa Sohlman, Teemu Sohlman

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
*/
package com.sohlman.netform;

import com.sohlman.netform.component.Button;
import com.sohlman.netform.component.TextField;


/**
 * @author Sampsa Sohlman
/*
 * Version: 2003-11-17
 *
 */
public abstract class LoginForm extends Form
{
	public Button loginButton = new Button(this);
	public TextField loginTextField = new TextField(this);
	public TextField passwordTextField = new TextField(this);
	private String iS_DefaultNextPage = null;

	ComponentListener i_ComponentListener = new ComponentListener()
	{
		public void eventAction(Component a_Component)
		{
			if(a_Component==loginButton)
			{
				String lS_LoginName = loginTextField.getText();
				String lS_Password = passwordTextField.getText();
				Object l_Object = doLogin(lS_LoginName, lS_Password);
				if(l_Object !=null)
				{
					getFormManager().setLoginInfo(l_Object); 
					setNextPage(iS_DefaultNextPage);
				}
				
			}
		}
	};

	/* (non-Javadoc)
	 * @see com.sohlman.netform.Form#init()
	 */
	public void init()
	{
		loginButton.addComponentListener(i_ComponentListener);
		
	}
	
	public abstract Object doLogin(String aS_LoginName, String aS_Password);
	
	/**
	 * Set's default next page after login is succeeded. It is also posible
	 * This is for JSP use
	 * 
	 * @param aS_Page String containing url of next page
	 */
	public void setDefaultPageAfterLogin(String aS_Page)
	{
		if(aS_Page==null)
		{
			throw new NullPointerException("setDefaultPageAfterLogin parameter cannot be null");
		}
		if(getFormManager().getNextPageAfterLogin()!=null)
		{
			iS_DefaultNextPage = getFormManager().getNextPageAfterLogin();
		}
		else
		{
			iS_DefaultNextPage = aS_Page;
		}
	}
}
