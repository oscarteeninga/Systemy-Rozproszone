package sr.ice.server;
// **********************************************************************
//
// Copyright (c) 2003-2019 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import com.zeroc.Ice.Current;
import Demo.Calc;

public class CalcI implements Calc
{
	private static final long serialVersionUID = -2448962912780867770L;

	@Override
	public long add(int a, int b, Current __current) 
	{
		System.out.println("ADD: a = " + a + ", b = " + b + ", result = " + (a+b));
		
		if(a > 1000 || b > 1000) {
			try { Thread.sleep(6000); } catch(java.lang.InterruptedException ex) { } 
		}

		if (a < 0 || b < 0) {
			try { Thread.sleep(15000); } catch(java.lang.InterruptedException ex) { }
		}
		
		if(__current.ctx.values().size() > 0) System.out.println("There are some properties in the context");
		showCurrentClient(__current);
		return a + b;
	}
	
	@Override
	public long subtract(int a, int b, Current __current) {
		if (b == 0) {
			System.out.println("B = 0, illegal argument");
			return 0;
		}
		showCurrentClient(__current);
		System.out.println("SUBSTRACT: a = " + a + ", b = " + b + ", result = " + (a/b));
		return a/b;
	}

	@Override
	public double avg(int[] numbers, Current current) {
		if (numbers.length == 0) {
			throw new IllegalArgumentException("The number list cannot be empty.");
		}
		double sum = 0.0;
		System.out.print("AVG: ");
		for (int i : numbers) {
			sum += i;
			System.out.print(i + " ");
		}
		System.out.println("\tresult = " + sum/numbers.length);
		return sum/numbers.length;
	}

	private void showCurrentClient(Current __current) {
		System.out.println("I'm processing on object: " + __current.id);
	}
}
