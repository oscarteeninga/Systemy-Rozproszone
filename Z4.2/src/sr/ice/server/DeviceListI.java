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
    public String getList(Current current) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < devices.length; i++) {
            sb.append(devices[i].getName(current) + "\n");
        }
        return sb.toString();
    }
}
