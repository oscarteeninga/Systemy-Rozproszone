package sr.thrift.server;

import org.apache.thrift.TException;

// Generated code
import sr.rpc.thrift.*;

import java.util.Set;

public class CalculatorHandler implements Calculator.Iface {

	int id;

	public CalculatorHandler(int id) {
		this.id = id;
	}

	@Override
	public int add(int n1, int n2) {
		System.out.println("CH#" + id + " add(" + n1 + "," + n2 + ")");
		if(n1 > 1000 || n2 > 1000) { 
			try { Thread.sleep(10000); } catch(java.lang.InterruptedException ex) { }
			System.out.println("DONE");
		}
		return n1 + n2;
	}

	@Override
	public int subtract(int num1, int num2) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double min(Set<Double> val) throws TException {
		if(val.size() == 0) {
			throw new InvalidArguments(0, "no data");
		}
		Double min = null;
		for (Double d : val) {
			if (min == null) {
				min = d;
			}
			if (d < min) {
				min = d;
			}
		}
		return min;
	}

}

