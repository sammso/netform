package com.sohlman.netform;

/**
 * Abstract component for Table
 * 
 * @author Sampsa Sohlman
/*
 * Version: 15.8.2003
 *
 */
public abstract class TableComponent extends Component
{
	private int ii_column;
	private int ii_row;
	private boolean ib_isModel = false;;


	public TableComponent(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public TableComponent(Form a_Form)
	{
		super(a_Form);
	}
	
	void setModel()
	{
		ib_isModel = true;
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableComponentModel#newInstance()
	 */
	public abstract TableComponent newInstance();

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableComponentModel#getValue()
	 */
	public abstract Object getValue();

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableComponentModel#setObject(java.lang.Object)
	 */
	public abstract void setObject(Object a_Object);

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableComponentModel#setColumn(int)
	 */
	public void setColumn(int ai_column)
	{
		ii_column = ai_column;
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableComponentModel#setRow(int)
	 */
	public void setRow(int ai_row)
	{
		ii_row = ai_row;
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableComponentModel#getRow()
	 */
	public int getRow()
	{
		return ii_row;
	}

	/* (non-Javadoc)
	 * @see com.sohlman.netform.TableComponentModel#getColumn()
	 */
	public int getColumn()
	{
		return ii_column;
	}
	
	public boolean isModel()
	{
		return ib_isModel;
	}
}
