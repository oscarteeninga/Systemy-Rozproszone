package sr.ice.client;

import Smart.*;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.LocalException;

public class Client {
	public static void main(String[] args) {
		int status = 0;
		Communicator communicator = null;

		try {
			// 1. Inicjalizacja ICE
			communicator = Util.initialize(args);
			String line = null;
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

			while(true) {
				showDevices(communicator);
				System.out.print("==> ");
				line = in.readLine();
				if (line.equals("switch1")) {
					ObjectPrx base = communicator.stringToProxy("switch/1:tcp -h localhost -p 10000");
					SwitchPrx obj = SwitchPrx.checkedCast(base);
					if (obj == null) throw new Error("Invalid proxy");
					System.out.println(obj.getHelp());
					while (true) {
						System.out.print("==> ");
						String[] cmd = in.readLine().split("\\.");
						if (cmd[0].equals("quit")) break;
						evaluateSwitch(obj, cmd);
					}
				} else if (line.equals("switch2")) {
					ObjectPrx base = communicator.stringToProxy("switch/2:tcp -h localhost -p 10000");
					SwitchPrx obj = SwitchPrx.checkedCast(base);
					if (obj == null) throw new Error("Invalid proxy");
					System.out.println(obj.getHelp());
					while (true) {
						System.out.print("==> ");
						String[] cmd = in.readLine().split("\\.");
						if (cmd[0].equals("quit")) break;
						evaluateSwitch(obj, cmd);
					}
				} else if (line.equals("switch3")) {
					ObjectPrx base = communicator.stringToProxy("switch/3:tcp -h localhost -p 10000");
					SwitchPrx obj = SwitchPrx.checkedCast(base);
					if (obj == null) throw new Error("Invalid proxy");
					System.out.println(obj.getHelp());
					while (true) {
						System.out.print("==> ");
						String[] cmd = in.readLine().split("\\.");
						if (cmd[0].equals("quit")) break;
						evaluateSwitch(obj, cmd);
					}
				} else if (line.equals("light1")) {
					ObjectPrx base = communicator.stringToProxy("light/1:tcp -h localhost -p 10000");
					LightPrx obj = LightPrx.checkedCast(base);
					if (obj == null) throw new Error("Invalid proxy");
					System.out.println(obj.getHelp());
					while (true) {
						System.out.print("==> ");
						String[] cmd = in.readLine().split("\\.");
						if (cmd[0].equals("quit")) break;
						evaluateLight(obj, cmd);
					}
				} else if (line.equals("light2")) {
					ObjectPrx base = communicator.stringToProxy("light/2:tcp -h localhost -p 10000");
					LightPrx obj = LightPrx.checkedCast(base);
					if (obj == null) throw new Error("Invalid proxy");
					System.out.println(obj.getHelp());
					while (true) {
						System.out.print("==> ");
						String[] cmd = in.readLine().split("\\.");
						if (cmd[0].equals("quit")) break;
						evaluateLight(obj, cmd);
					}
				} else if (line.equals("strip2")) {
					ObjectPrx base = communicator.stringToProxy("strip/2:tcp -h localhost -p 10000");
					LedStripColorPrx obj = LedStripColorPrx.checkedCast(base);
					if (obj == null) throw new Error("Invalid proxy");
					System.out.println(obj.getHelp());
					while (true) {
						System.out.print("==> ");
						String[] cmd = in.readLine().split("\\.");
						if (cmd[0].equals("quit")) break;
						evaluateStrip(obj, cmd);
					}
				} else if (line.equals("strip5")) {
					ObjectPrx base = communicator.stringToProxy("strip/5:tcp -h localhost -p 10000");
					LedStripColorPrx obj = LedStripColorPrx.checkedCast(base);
					if (obj == null) throw new Error("Invalid proxy");
					System.out.println(obj.getHelp());
					while (true) {
						System.out.print("==> ");
						String[] cmd = in.readLine().split("\\.");
						if (cmd[0].equals("quit")) break;
						evaluateStrip(obj, cmd);
					}
				} else if (line.equals("quit")) {
					System.out.println("Quiting...");
					break;
				} else {
					System.out.println("Device not found!");
				}
			}
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

	static private void showDevices(Communicator communicator) {
		System.out.println("=========Devices=========");
		ObjectPrx base = communicator.stringToProxy("devices/list:tcp -h localhost -p 10000");
		DeviceListPrx obj = DeviceListPrx.checkedCast(base);
		System.out.print(obj.getList());
		System.out.println("=========================");
	}

	static private void evaluateSwitch(SwitchPrx obj, String []cmd) {
		switch (Integer.valueOf(cmd[0])) {
			case 1:
				obj.on();
				break;
			case 2:
				obj.off();
				break;
			case 3:
				obj.change();
				break;
			case 4:
				obj.setCondition(condition.valueOf(Integer.valueOf(cmd[1])));
				break;
			case 5:
				System.out.println(obj.getCondition());
				break;
			default:
				System.out.println("Unkown command");
		}
	}

	static private void evaluateLight(LightPrx obj, String []cmd) {
		switch (Integer.valueOf(cmd[0])) {
			case 1:
				obj.on();
				break;
			case 2:
				obj.off();
				break;
			case 3:
				obj.change();
				break;
			case 4:
				obj.setCondition(condition.valueOf(Integer.valueOf(cmd[1])));
				break;
			case 5:
				obj.getCondition();
				break;
			case 6:
				try {
					obj.setBrightness(Integer.valueOf(cmd[1]));
				} catch (UnreachableArgument e) {
					e.printStackTrace();
				}
				break;
			case 7:
				mode mode = Smart.mode.valueOf(Integer.valueOf(cmd[1]));
				obj.setMode(mode);
				break;
			case 8:
				System.out.println(obj.getMode());
				break;
			default:
				System.out.println("Unknown command");
		}
	}

	static private void evaluateLightColor(LightColorPrx obj, String []cmd) {
		switch (Integer.valueOf(cmd[0])) {
			case 1:
				obj.on();
				break;
			case 2:
				obj.off();
				break;
			case 3:
				obj.change();
				break;
			case 4:
				obj.setCondition(condition.valueOf(Integer.valueOf(cmd[1])));
				break;
			case 5:
				obj.getCondition();
				break;
			case 6:
				try {
					obj.setBrightness(Integer.valueOf(cmd[1]));
				} catch (UnreachableArgument e) {
					e.printStackTrace();
				}
				break;
			case 7:
				mode mode = Smart.mode.valueOf(Integer.valueOf(cmd[1]));
				obj.setMode(mode);
				break;
			case 8:
				System.out.println(obj.getMode());
				break;
			case 9:
				color color = Smart.color.valueOf(Integer.valueOf(cmd[1]));
				obj.setColor(color);
				break;
			case 10:
				System.out.println("Color: " + obj.getColor());
				break;
			default:
				System.out.println("Unknown command");
		}
	}

	static private void evaluateStrip(LedStripColorPrx obj, String []cmd) {
		switch (Integer.valueOf(cmd[0])) {
			case 1:
				obj.on();
				break;
			case 2:
				obj.off();
				break;
			case 3:
				obj.change();
				break;
			case 4:
				obj.setCondition(condition.valueOf(Integer.valueOf(cmd[1])));
				break;
			case 5:
				obj.getCondition();
				break;
			case 6:
				try {
					obj.setBrightness(Integer.valueOf(cmd[1]));
				} catch (UnreachableArgument e) {
					e.printStackTrace();
				}
				break;
			case 7:
				mode mode = Smart.mode.valueOf(Integer.valueOf(cmd[1]));
				obj.setMode(mode);
				break;
			case 8:
				System.out.println(obj.getMode());
				break;
			case 9:
				color color = Smart.color.valueOf(Integer.valueOf(cmd[1]));
				obj.setColor(color);
				break;
			case 10:
				System.out.println("Color: " + obj.getColor());
				break;
			case 11:
				condition []conditions = new condition[cmd.length-1];
				for (int i = 1; i < cmd.length; i++) {
					conditions[i-1] = Smart.condition.valueOf(Integer.valueOf(cmd[i]));
				}
				try {
					obj.setSegmentCondition(conditions);
				} catch (BadArgument e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Unkown command");
		}
	}
}