package com.sohlman.netform.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Sampsa Sohlman
 */
abstract class MasterTag implements Tag
{
	public final static String FORM = "form";
	
	protected Tag i_Tag = null;
	protected PageContext i_PageContext = null;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public abstract int doEndTag() throws JspException;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public abstract int doStartTag() throws JspException;

	/**
	 * @see javax.servlet.jsp.tagext.Tag#getParent()
	 */
	public Tag getParent()
	{
		return i_Tag;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public abstract void release();

	/**
	 * @see javax.servlet.jsp.tagext.Tag#setPageContext(javax.servlet.jsp.PageContext)
	 */
	public void setPageContext(PageContext pageContext)
	{
		this.i_PageContext = pageContext;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#setParent(javax.servlet.jsp.tagext.Tag)
	 */
	public void setParent(Tag tag)
	{
		this.i_Tag = tag;

	}
}