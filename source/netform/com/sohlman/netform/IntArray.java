package com.sohlman.netform;

import java.util.Arrays;

/**
 * 
 * 
 * @author Sampsa Sohlman
 * 
 * @version 2004-01-28
 */
public class IntArray
{
	public static int NOTFOUND = -1;
	public static int ERROR = -1;

	protected int[] ii_array;
	private int ii_size;
	private boolean ib_isSorted = true;

	/**
	 * 
	 */
	public IntArray()
	{
		ii_array = null;
		ii_size = 0;
	}

	/**
	 * @param ai_array
	 */
	public IntArray(int[] ai_array)
	{
		ii_array = ai_array;
		ib_isSorted = false;
	}

	/**
	 * @param ai_index
	 * @return
	 */
	public int getValue(int ai_index)
	{
		return ii_array[ai_index];
	}

	/**
	 * @param ai_value
	 * @return
	 */
	public boolean hasValue(int ai_value)
	{
		return getIndexOf(ai_value, 0) != NOTFOUND;
	}

	/**
	 * @param ai_value
	 * @return
	 */
	public int getIndexOf(int ai_value)
	{
		return getIndexOf(ai_value, 0);
	}

	/**
	 * @param ai_value
	 * @param ai_startIndex
	 * @return
	 */
	public int getIndexOf(int ai_value, int ai_startIndex)
	{
		if(ii_array==null)
		{
			return NOTFOUND;
		}
		
		if (ib_isSorted)
		{
			if(ii_array[ai_startIndex] > ai_value)
			{
				return NOTFOUND;
			}
			else
			{
				return Arrays.binarySearch(ii_array, ai_value);
			}
		}
		else
		{

			for (int li_y = ai_startIndex; li_y < ii_size; li_y++)
			{
				if (ii_array[li_y] == ai_value)
				{
					return li_y;
				}
			}
		}
		return NOTFOUND;
	}

	/**
	 * @param ai_value
	 */
	public void addValue(int ai_value)
	{
		if (ii_array == null)
		{
			ii_array = new int[20];
		}
		else if (ii_array.length <= ii_size)
		{
			int[] li_array = new int[ii_size + 20];
			System.arraycopy(ii_array, 0, li_array, 0, ii_array.length);
			ii_array = li_array;
		}

		if (ii_size > 0 && ii_array[ii_size] > ai_value)
		{
			ib_isSorted = false;
		}

		ii_array[ii_size] = ai_value;

		ii_size++;
	}

	/**
	 * @param ai_value
	 * @return
	 */
	public int removeValue(int ai_value)
	{
		int li_removedCount = 0;

		int li_index = getIndexOf(ai_value);
		while (li_index != NOTFOUND)
		{
			removeValueFromIndex(li_index);
			li_removedCount++;
			li_index = getIndexOf(ai_value, li_index);
		}

		return li_removedCount;
	}

	/**
	 * @param ai_index
	 * @return
	 */
	public boolean removeValueFromIndex(int ai_index)
	{
		int li_removedCount = 0;
		if (ai_index >= ii_size)
		{
			return false;
		}

		if (ii_size == 1)
		{
			ii_array = null;
			ii_size = 0;
		}
		else
		{
			for (int li_y = ai_index; li_y < ii_size - 1; li_y++)
			{
				ii_array[li_y] = ii_array[li_y + 1];
			}
			ii_size--;
		}
		return true;
	}

	/**
	 * @return
	 */
	public int size()
	{
		return ii_size;
	}

	/**
	 * @return
	 */
	public boolean isEmpty()
	{
		return ii_array == null;
	}

	/**
	 * @return
	 */
	public int[] toArray()
	{
		if (ii_array == null)
		{
			return null;
		}
		else
		{
			int[] li_array = new int[ii_size];
			System.arraycopy(ii_array, 0, li_array, 0, ii_size);
			return li_array;
		}
	}

	/**
	 * 
	 */
	public void clear()
	{
		ii_array = null;
		ii_size = 0;
	}

	public void sort()
	{
		if (ii_array != null && !ib_isSorted)
		{
			Arrays.sort(ii_array);
		}
	}

	public boolean isSorted()
	{
		return ib_isSorted;
	}

	/**
	 * @param ai_array
	 */
	public void setArray(int[] ai_array)
	{
		ii_array = ai_array;
		ii_size = ai_array.length;
		ib_isSorted = false;
	}
	
	/**
	 * Substract 1 from values that are bigger than given  value
	 * 
	 * @param ai_fromValue Given value
	 */
	public void substractOneFromAll(int ai_fromValue)
	{
		if(ib_isSorted)
		{
			int li_start = getIndexOf(ai_fromValue);
			for(int li_index = li_start + 1 ; li_index < ii_size ; li_index++ )
			{
				ii_array[li_index]--;
			}
		}
		else
		{
			for(int li_index = 0 ; li_index < ii_size ; li_index++ )
			{
				if(ii_array[li_index] > ai_fromValue)
				{
					ii_array[li_index]--;
				}
			}			
		}
	}
	
	/**
	 * Add 1 to values that are bigger than given value
	 * 
	 * @param ai_value Given value
	 */
	public void addOneToAll(int ai_value)
	{
		if(ib_isSorted)
		{
			int li_start = getIndexOf(ai_value);
			for(int li_index = li_start + 1 ; li_index < ii_size ; li_index++ )
			{
				ii_array[li_index]++;
			}
		}
		else
		{
			for(int li_index = 0 ; li_index < ii_size ; li_index++ )
			{
				if(ii_array[li_index] > ai_value)
				{
					ii_array[li_index]++;
				}
			}			
		}
	}	
}