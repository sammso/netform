package com.sohlman.netform.component.table;

import java.util.Arrays;

/**
 * Wrapper for int array, with some specific functionality.
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
	 * @see IntArray#setArray(int[])
	 * 
	 * @param ai_array
	 */
	public IntArray(int[] ai_array)
	{
		setArray(ai_array);
	}

	/**
	 * @param ai_index
	 * @return int value from index
	 */
	public int getValue(int ai_index)
	{
		return ii_array[ai_index];
	}

	/**
	 * @param ai_value
	 * @return if value exist
	 */
	public boolean hasValue(int ai_value)
	{
		return getIndexOf(ai_value, 0) != NOTFOUND;
	}

	/**
	 * Get index of value
	 * 
	 * @param ai_value value to be searched
	 * @return index of value NOTFOUND if not found
	 */
	public int getIndexOf(int ai_value)
	{
		return getIndexOf(ai_value, 0);
	}

	/**
	 * Get index of value
	 * 
	 * @param ai_value value to be searched
	 * @param ai_startIndex from to be look at.
	 * @return index of value NOTFOUND if not found
	 */
	public int getIndexOf(int ai_value, int ai_startIndex)
	{
		if (ii_array == null)
		{
			return NOTFOUND;
		}

		if (ib_isSorted)
		{
			if (ii_array[ai_startIndex] > ai_value)
			{
				return NOTFOUND;
			}
			else
			{
				int li_index = Arrays.binarySearch(ii_array, ai_value);
				if (li_index >= 0)
				{
					return li_index;
				}
				else
				{
					return NOTFOUND;
				}
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
	 * Add value to end of array
	 * 
	 * @param ai_value value to be added
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
	 * Remove value from. If the value is twice, both are removed
	 * 
	 * @param ai_value
	 * @return how many rows are removed
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
	 * Remove value from index. Remember sorting array is changing indexes.
	 * <p>Don't change order, so if array is sorted it is sorted after this too. 
	 * 
	 * @param ai_index index to be removed
	 * @return true if success false if not
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
			ii_size--;
			for (int li_y = ai_index ; li_y < ii_size ; li_y--)
			{
				ii_array[li_y] = ii_array[li_y + 1];
			}
		}
		return true;
	}

	/**
	 * Size of array
	 * 
	 * @return int containing size of array 0 if empty
	 */
	public int size()
	{
		return ii_size;
	}

	/**
	 * Tells if array is empty
	 * 
	 * @return boolean true if empty false if not
	 */
	public boolean isEmpty()
	{
		return ii_array == null;
	}

	/**
	 * <u>Copies</u> values to int array
	 * 
	 * @return array of ints. If empty then null
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
	 * Clear or emtpy array
	 */
	public void clear()
	{
		ii_array = null;
		ii_size = 0;
	}

	/**
	 * Sort array. If already sorted then does nothing
	 */
	public void sort()
	{
		if (!ib_isSorted && ii_array != null)
		{
			Arrays.sort(ii_array, 0, ii_size);
		}
	}

	/**
	 * Tells if array is sorted or not
	 * 
	 * @return if array is sorted order
	 */
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
		if(ii_array == null)
		{
			ii_size = 0;
		}
		else
		{
			ii_size = ai_array.length;
		}
		ib_isSorted = false;
	}

	/**
	 * Substract 1 from values that are bigger than given  value
	 * 
	 * @param ai_fromValue Given value
	 */
	public void substractValuesThatAreBiggerThan(int ai_fromValue)
	{
		if (ib_isSorted)
		{
			int li_start = getIndexOf(ai_fromValue);
			for (int li_index = li_start + 1; li_index < ii_size; li_index++)
			{
				ii_array[li_index]--;
			}
		}
		else
		{
			for (int li_index = 0; li_index < ii_size; li_index++)
			{
				if (ii_array[li_index] > ai_fromValue)
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
	public void addOneToValuesThatAreBiggerThan(int ai_value)
	{
		if (ib_isSorted)
		{
			int li_start = getIndexOf(ai_value);
			if (li_start >= 0)
			{

				for (int li_index = li_start; li_index < ii_size; li_index++)
				{
					ii_array[li_index]++;
				}
			}
		}
		else
		{
			for (int li_index = 0; li_index < ii_size; li_index++)
			{
				if (ii_array[li_index] >= ai_value)
				{
					ii_array[li_index]++;
				}
			}
		}
	}

	/**
	 * @param ai_array <b>this array might be sorted</b>
	 * @return true if array contest is egual to this
	 */
	public boolean equals(int[] ai_array)
	{

		if (ai_array == null && ii_array != null || ai_array != null && ii_array == null)
		{
			return false;
		}
		else if (ai_array == null && ii_array == null)
		{
			return true;
		}
		else if (ii_size != ai_array.length)
		{
			return false;
		}
		else // ai_array.length == ii_size
			{
			Arrays.sort(ai_array);
			for (int li_y = 0; li_y < ii_size; li_y++)
			{
				if (ii_array[li_y] != ai_array[li_y])
				{
					return false;
				}
			}
			return true;
		}
	}
}