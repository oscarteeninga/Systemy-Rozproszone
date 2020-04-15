package sr.ice.server;

import Smart.Device;
import Smart.type;
import com.zeroc.Ice.Current;

public class DeviceI implements Device {

    protected String name;
    protected type type;

    public DeviceI(String name, type type) {
        this.type = type;
        this.name = name;
    }

    @Override
    public type getType(Current current) {
        return this.type;
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
