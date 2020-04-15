package sr.ice.server;

import Smart.Device;
import Smart.type;
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

			SwitchI switchServant1 = new SwitchI("switch1", type.SWITCH);
			devList.add(switchServant1);
			SwitchI switchServant2 = new SwitchI("switch2", type.SWITCH);
			devList.add(switchServant2);
			SwitchI switchServant3 = new SwitchI("switch3", type.SWITCH);
			devList.add(switchServant3);
			LightI lightServant1 = new LightI("light1", type.LIGHT);
			devList.add(lightServant1);
			LightI lightServant2 = new LightI("light2", type.LIGHT);
			devList.add(lightServant2);
			LightColorI lightColorServant1 = new LightColorI("lightcolor1", type.LIGHTCOLOR);
			devList.add(lightColorServant1);
			LightColorI lightColorServant2 = new LightColorI("lightcolor2", type.LIGHTCOLOR);
			devList.add(lightColorServant2);
			LightColorI lightColorServant3 = new LightColorI("lightcolor3", type.LIGHTCOLOR);
			devList.add(lightColorServant3);
			FridgeI fridgeServant = new FridgeI("fridge1", type.FRIDGE);
			devList.add(fridgeServant);
			LedStripColorI stripServant2 = new LedStripColorI("strip2", type.STRIP, 2);
			devList.add(stripServant2);
			LedStripColorI stripServant5 = new LedStripColorI("strip5", type.STRIP, 5);
			devList.add(stripServant5);

			Device[] devices = new Device[devList.size()];

			// 4. Wpisy do ASM
			for (int i = 0; i < devices.length; i++) {
				devices[i] = devList.get(i);
				adapter.add(devices[i], new Identity(devices[i].getName(null), "device"));
			}
			DeviceListI deviceListServant = new DeviceListI(devices);
			adapter.add(deviceListServant, new Identity("list", "devices"));

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
