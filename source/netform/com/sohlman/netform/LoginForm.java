package com.sohlman.netform;


/**
 * @author Sampsa Sohlman
/*
 * Version: 17.11.2003
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
