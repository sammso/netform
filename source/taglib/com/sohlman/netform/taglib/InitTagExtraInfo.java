package com.sohlman.netform.taglib;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Sampsa Sohlman
 */
public class InitTagExtraInfo extends TagExtraInfo
{

	/**
	 * @see javax.servlet.jsp.tagext.TagExtraInfo#getVariableInfo(javax.servlet.jsp.tagext.TagData)
	 */
	public VariableInfo[] getVariableInfo(TagData a_TagData)
	{
		return super.getVariableInfo(a_TagData);
	}
	/**
	 * @see javax.servlet.jsp.tagext.TagExtraInfo#isValid(javax.servlet.jsp.tagext.TagData)
	 */
	public boolean isValid(TagData a_TagData)
	{
		System.out.println(a_TagData.getAttributeString("init"));
		return super.isValid(a_TagData);
	}
}
