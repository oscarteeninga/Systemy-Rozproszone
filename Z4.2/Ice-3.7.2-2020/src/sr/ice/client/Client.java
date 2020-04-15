package sr.ice.client;

import Smart.*;
import Demo.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.LocalException;

public class Client 
{
	public static void main(String[] args) 
	{
		int status = 0;
		Communicator communicator = null;

		try {
			// 1. Inicjalizacja ICE
			communicator = Util.initialize(args);

			while(true) {
				ObjectPrx base = communicator.stringToProxy("calc/calc22:tcp -h localhost -p 10000");
			}
			
			// 2. To samo co powy�ej, ale mniej �adnie

			System.out.println(base);

			// 3. Rzutowanie, zaw�anie
			CalcPrx obj = CalcPrx.checkedCast(base);
			System.out.println(obj);
			if (obj == null) throw new Error("Invalid proxy");
			
			// 4. Wywolanie zdalnych operacji
			String line = null;
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			CompletableFuture<Long> cfl = null;
			do
			{
				try
				{
					System.out.print("==> ");
					System.out.flush();
					line = in.readLine();
					
					if (line == null)
					{
						break;
					}
					if (line.equals("avg")) {
						int []tab = {1, 9, 2, 8, 3, 7, 3, 6, 4, 5};
						double r = obj.avg(tab);
						System.out.println("RESULT = " + r);
					}
					if (line.equals("add"))
					{
						long r = obj.add(7, 8);
						System.out.println("RESULT = " + r);
					}
					if (line.equals("add-long")) {
						long r = obj.add(-1, -3);
						System.out.println("RESULT = " + r);
					}
					if (line.equals("subtract"))
					{
						long r = obj.subtract(7, 8);
						System.out.println("RESULT = " + r);
					}
					if (line.equals("add2"))
					{
						long r = obj.add(7000, 8000);
						System.out.println("RESULT = " + r);
					}
					if (line.equals("add-with-ctx")) //wys�anie danych stanowi�cych kontekst wywo�ania
					{
						Map<String,String> map = new HashMap<String, String>();
						map.put("key1", "val1");
						map.put("key2", "val2");
						long r = obj.add(7, 8, map);
						System.out.println("RESULT = " + r);
					}
					if (line.equals("add-asyn1")) //co si� dzieje ze sterowaniem?
					{
						cfl = obj.addAsync(7000, 8000).whenComplete((result, ex) -> 
						{
							 System.out.println("RESULT (asyn) = " + result);
						});
					}
					if (line.equals("add-asyn2-req")) //zlecenie wywo�ania
					{
						cfl = obj.addAsync(7000, 8000);						
					}
					if (line.equals("add-asyn2-res")) //odebranie wyniku
					{
						long r = cfl.join();						
						System.out.println("RESULT = " + r);
					}
					else if (line.equals("x"))
					{
						// Nothing to do
					}
				}
				catch (java.io.IOException ex)
				{
					System.err.println(ex);
				}
			}
			while (!line.equals("x"));


		} catch (LocalException e) {
			e.printStackTrace();
			status = 1;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			status = 1;
		}
		if (communicator != null) {
			// Clean up
			//
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}

}