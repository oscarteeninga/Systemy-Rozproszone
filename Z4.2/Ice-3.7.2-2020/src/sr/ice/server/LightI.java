package sr.ice.server;

import Smart.Light;
import Smart.UnreachableArgument;
import Smart.mode;
import com.zeroc.Ice.Current;

public class LightI extends SwitchI implements Light {

    private int brightness;
    private mode mode;

    public LightI() {
        super();
        this.brightness = 100;
    }
    @Override
    public void setBrightness(int brightness, Current current) throws UnreachableArgument {
        if (brightness > 100 || brightness < 0) {
            throw new UnreachableArgument();
        }
        this.brightness = brightness;
    }

    @Override
    public void setMode(mode mode, Current current) {
        this.mode = mode;
    }

    @Override
    public mode getMode(Current current) {
        return this.mode;
    }
}
