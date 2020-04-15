package sr.ice.server;

import Smart.Device;
import com.zeroc.Ice.Current;

public class DeviceI implements Device {

    protected String name;

    public DeviceI(String name) {
        this.name = name;
    }

    @Override
    public String getHelp(Current current) {
        return null;
    }

    @Override
    public String getName(Current current) {
        return this.name;
    }
}
