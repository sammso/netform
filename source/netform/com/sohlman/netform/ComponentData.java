package com.sohlman.netform;

/** This connector to Model in MVC design.
 * 
 * @author Sampsa Sohlman
 * 
 * @version 2004-01-13
 */
public interface ComponentData
{	
	/**
	 * Set Data To DataModel<br>
	 * example {@link TableModel TableModel}
	 * @param a_Object to be put to DataModel
	 */
	public void setData(Object a_Object);

	/**
	 * Get Data from DataModel<br>
	 * example {@link TableModel TableModel}
	 * @param a_Object Object in DataModel
	 */
	public Object getData();
}
