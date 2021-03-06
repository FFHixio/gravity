/** (C) Copyright 2013, Applied Physical Sciences Corp., A General Dynamics Company
 **
 ** Gravity is free software; you can redistribute it and/or modify
 ** it under the terms of the GNU Lesser General Public License as published by
 ** the Free Software Foundation; either version 3 of the License, or
 ** (at your option) any later version.
 **
 ** This program is distributed in the hope that it will be useful,
 ** but WITHOUT ANY WARRANTY; without even the implied warranty of
 ** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 ** GNU Lesser General Public License for more details.
 **
 ** You should have received a copy of the GNU Lesser General Public
 ** License along with this program;
 ** If not, see <http://www.gnu.org/licenses/>.
 **
 */

package com.aphysci.gravity.matlab;

import com.aphysci.gravity.GravitySubscriber;
import com.aphysci.gravity.GravityDataProduct;
import java.util.List;
import java.util.ArrayList;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Ints;


public class MATLABGravitySubscriber implements GravitySubscriber
{
	private int maxBufferSize;
	private List<GravityDataProduct> data = new ArrayList<GravityDataProduct>();

	public MATLABGravitySubscriber()
	{
		this(0);
	}

	public MATLABGravitySubscriber(int maxBufferSize)
	{
		this.maxBufferSize = maxBufferSize;
	}

	public synchronized void subscriptionFilled(final List<GravityDataProduct> dataProducts)
	{
		data.addAll(dataProducts);
		if (maxBufferSize > 0)
		{
			int removeCount = data.size() - maxBufferSize;
			if (removeCount > 0)
			{
				data.subList(0, removeCount).clear();
			}
		}
	}

	public synchronized GravityDataProduct getDataProduct(int timeoutMS)
	{
		GravityDataProduct gdp = null;
		do
		{
			if (!data.isEmpty())
			{
				gdp = data.get(0);
				data.remove(0);
			}
			else if (timeoutMS < 0)
			{
				try {Thread.sleep(10);} catch (InterruptedException ie) {}
			}
			else
			{
				int sleepTime = Math.min(timeoutMS, 10);
				try {Thread.sleep(sleepTime);} catch (InterruptedException ie) {}
				timeoutMS -= sleepTime;
			}
		} while (timeoutMS != 0 && gdp == null);

		return gdp;
	}

	public synchronized List<GravityDataProduct> getAllDataProducts()
	{
		@SuppressWarnings("unchecked")
		List<GravityDataProduct> ret = new ArrayList(data);
		data.clear();
		return ret;
	}

	static public Number[] convertNumberListToNumberArray(List<Number> list)
	{
		Number[] a = new Number[list.size()];
		list.toArray(a);
		return a;
	}

	static public double[] convertNumberListToDoubleArray(List<Number> list)
	{
		double[] d = new double[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			d[i] = list.get(i).doubleValue();
		}
		return d;
	}

	static public List<Double> convertNumberArrayToNumberList(double[] array)
	{
		List<Double> list = Doubles.asList(array);
		return list;
	}

	static public List<Integer> convertNumberArrayToNumberList(int[] array)
	{
		List<Integer> list = Ints.asList(array);
		return list;
	}

	static public List<Long> convertNumberArrayToNumberList(long[] array)
	{
		List<Long> list = Longs.asList(array);
		return list;
	}
}
