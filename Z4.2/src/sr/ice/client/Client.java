package sr.ice.client;

import Smart.*;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.LocalException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
	public static void main(String[] args) {
		int status = 0;
		Communicator communicator = null;

		try {
			// 1. Inicjalizacja ICE
			communicator = Util.initialize(args);
			String line = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while(true) {
				showHelp();
				String[] names = showDevices(communicator);
				System.out.print("==> ");
				line = in.readLine();

				ObjectPrx base = null;
				for (int i = 0; i < names.length; i++) {
					if (names[i].equals(line)) {
						base = communicator.stringToProxy("device/" + line + ":tcp -h localhost -p 10000");
						break;
					}
				}
				if (line.equals("list")) {
					names = showDevices(communicator);
					System.out.print("==> ");
					continue;
				} else if (line.equals("quit")) {
					break;
				} else if (line.equals("help")) {
					showHelp();
				} else if (base == null) {
					System.out.println("Device not found!");
				} else {
					execute(base, in);
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
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}

	static private String[] showDevices(Communicator communicator) {
		System.out.println("=========Devices=========");
		ObjectPrx base = communicator.stringToProxy("devices/list:tcp -h localhost -p 10000");
		DeviceListPrx obj = DeviceListPrx.checkedCast(base);
		String []names = obj.getList();
		for (int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
		}
		System.out.println("=========================");
		return names;
	}

	static private void execute(ObjectPrx base, BufferedReader in) throws Exception {
		switch (DevicePrx.checkedCast(base).getType()) {
			case SWITCH:
				SwitchPrx obj1 = SwitchPrx.checkedCast(base);
				System.out.println(obj1.getHelp());
				while (true) {
					System.out.print("==> ");
					String[] cmd = in.readLine().split("\\.");
					if (cmd[0].equals("quit")) break;
					evaluateSwitch(obj1, cmd);
				}
				break;
			case LIGHT:
				LightPrx obj2 = LightPrx.checkedCast(base);
				System.out.println(obj2.getHelp());
				while (true) {
					System.out.print("==> ");
					String[] cmd = in.readLine().split("\\.");
					if (cmd[0].equals("quit")) break;
					evaluateLight(obj2, cmd);
				}
				break;
			case LIGHTCOLOR:
				LightColorPrx obj3 = LightColorPrx.checkedCast(base);
				System.out.println(obj3.getHelp());
				while (true) {
					System.out.print("==> ");
					String[] cmd = in.readLine().split("\\.");
					if (cmd[0].equals("quit")) break;
					evaluateLightColor(obj3, cmd);
				}
				break;
			case STRIP:
				LedStripColorPrx obj4 = LedStripColorPrx.checkedCast(base);
				System.out.println(obj4.getHelp());
				while (true) {
					System.out.print("==> ");
					String[] cmd = in.readLine().split("\\.");
					if (cmd[0].equals("quit")) break;
					evaluateStrip(obj4, cmd);
				}
				break;
			case FRIDGE:
				FridgePrx obj5 = FridgePrx.checkedCast(base);
				System.out.println(obj5.getHelp());
				while (true) {
					System.out.print("==> ");
					String []cmd = in.readLine().split("\\.");
					if (cmd[0].equals("quit")) break;
					evaluateFridge(obj5, cmd);
				}
				break;
			default:
		}
	}

	static private void showHelp() {
		System.out.println("===========help==========");
		System.out.println("Conditions:");
		showValues(condition.values());
		System.out.println("Modes:");
		showValues(mode.values());
		System.out.println("Colors:");
		showValues(color.values());
		System.out.println("Units:");
		showValues(unit.values());
		System.out.println("=========================");

	}

	static private void showValues(Enum[] values) {
		for (int i = 0; i < values.length; i++) {
			System.out.print(values[i] + "(" + i + ") ");
		}
		System.out.println();
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
				System.out.println("Unknown command");
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
				System.out.println(obj.getColor());
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
				System.out.println(obj.getColor());
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

	private static void evaluateFridge(FridgePrx obj, String[] cmd) {
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
					obj.setTemp(Float.valueOf(cmd[1]), unit.valueOf(Integer.valueOf(cmd[2])));
				} catch (UnreachableArgument e) {
					e.printStackTrace();
				} finally {
					break;
				}
			case 7:
				System.out.println(obj.getTemp(unit.valueOf(Integer.valueOf(cmd[1]))) + " " + unit.valueOf(Integer.valueOf(cmd[1])));
			case 8:
				try {
					obj.setHumidity(Integer.valueOf(cmd[1]));
				} catch (UnreachableArgument e) {
					e.printStackTrace();
				} finally {
					break;
				}
			case 9:
				System.out.println(obj.getHumidity());
				break;
			case 10:
				byte []photo = obj.getPhoto();
				int N = (int) Math.sqrt(photo.length);
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						System.out.print(Integer.toHexString(Math.abs(photo[i*N + j]) % 16) + " ");
					}
					System.out.println();
				}
				break;
			default:
				System.out.println("Unknown command");
		}
	}
}