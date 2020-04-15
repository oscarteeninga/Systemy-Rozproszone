package sr.ice.server;
// **********************************************************************
//
// Copyright (c) 2003-2019 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import Smart.*;
import com.zeroc.Ice.*;

import java.lang.Exception;

public class Server
{
	public void t1(String[] args)
	{
		int status = 0;
		Communicator communicator = null;

		try
		{
			// 1. Inicjalizacja ICE - utworzenie communicatora
			communicator = Util.initialize(args);

			// 2. Konfiguracja adaptera w kodzie źródłowym
			ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p 10000:udp -h localhost -p 10000");

			// 3. Stworzenie serwantów
			SwitchI switchServant = new SwitchI();
			LightI lightServant = new LightI();
			FridgeI fridgeServant = new FridgeI();
			LedStripColorI stripServant = new LedStripColorI(5);


			
						    
			// 4. Wpisy ASM
	        adapter.add(switchServant, new Identity("switch", "smartHome"));
			adapter.add(lightServant, new Identity("light", "smartHome"));
			adapter.add(stripServant, new Identity("ledstriprgb", "smartHome"));
			adapter.add(fridgeServant, new Identity("ledstriprgb", "smartHome"));


			// 5. Aktywacja adaptera
			adapter.activate();
			
			System.out.println("Entering event processing loop...");
			
			communicator.waitForShutdown(); 		
			
		}
		catch (Exception e)
		{
			System.err.println(e);
			status = 1;
		}
		if (communicator != null)
		{
			// Clean up
			//
			try
			{
				communicator.destroy();
			}
			catch (Exception e)
			{
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}


	public static void main(String[] args)
	{
		Server app = new Server();
		app.t1(args);
	}
}
