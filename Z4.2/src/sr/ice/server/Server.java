package sr.ice.server;

import Smart.Device;
import com.zeroc.Ice.*;

import java.lang.Exception;
import java.util.ArrayList;

public class Server {
	public void t1(String[] args) {
		int status = 0;
		Communicator communicator = null;

		try {
			// 1. Inicjalizacja ICE
			communicator = Util.initialize(args);

			// 2. Konfiguracja adaptera w kodzie źródłowym
			ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p 10000:udp -h localhost -p 10000");

			// 3. Stworzenie serwantów
			ArrayList<Device> devList = new ArrayList();

			SwitchI switchServant1 = new SwitchI("switch1");
			devList.add(switchServant1);
			SwitchI switchServant2 = new SwitchI("switch2");
			devList.add(switchServant2);
			SwitchI switchServant3 = new SwitchI("switch3");
			devList.add(switchServant3);
			LightI lightServant1 = new LightI("light1");
			devList.add(lightServant1);
			LightI lightServant2 = new LightI("light2");
			devList.add(lightServant2);
			FridgeI fridgeServant = new FridgeI("fridge");
			devList.add(fridgeServant);
			LedStripColorI stripServant2 = new LedStripColorI("strip2", 2);
			devList.add(stripServant2);
			LedStripColorI stripServant5 = new LedStripColorI("strip5", 5);
			devList.add(stripServant5);

			Device[] devices = new Device[devList.size()];
			for (int i = 0; i < devices.length; i++) {
				devices[i] = devList.get(i);
			}

			DeviceListI deviceListServant = new DeviceListI(devices);

			// 4. Wpisy ASM
			adapter.add(deviceListServant, new Identity("list", "devices"));
	        adapter.add(switchServant1, new Identity("1", "switch"));
			adapter.add(switchServant2, new Identity("2", "switch"));
			adapter.add(switchServant3, new Identity("3", "switch"));
			adapter.add(lightServant1, new Identity("1", "light"));
			adapter.add(lightServant2, new Identity("2", "light"));
			adapter.add(stripServant2, new Identity("2", "strip"));
			adapter.add(stripServant5, new Identity("5", "strip"));
			adapter.add(fridgeServant, new Identity("1", "fridge"));

			// 5. Aktywacja adaptera
			adapter.activate();
			System.out.println("Entering event processing loop...");
			communicator.waitForShutdown();
		} catch (Exception e) {
			System.err.println(e);
			status = 1;
		}
		if (communicator != null) {
			// Clean up
			//
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}

	public static void main(String[] args) {
		Server app = new Server();
		app.t1(args);
	}
}
