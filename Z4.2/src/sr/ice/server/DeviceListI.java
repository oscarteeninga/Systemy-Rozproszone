package sr.ice.server;

import Smart.Device;
import Smart.DevicePrx;
import Smart.DeviceList;
import com.zeroc.Ice.Current;

public class DeviceListI implements DeviceList {
    private Device[] devices;

    public DeviceListI(Device[]devices) {
        this.devices = devices;
    }

    @Override
    public String[] getList(Current current) {
        String []names = new String[devices.length];
        for (int i = 0; i < devices.length; i++) {
            names[i] = devices[i].getName(current);
        }
        return names;
    }
}
