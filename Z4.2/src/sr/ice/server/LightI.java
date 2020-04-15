package sr.ice.server;

import Smart.Light;
import Smart.UnreachableArgument;
import Smart.mode;
import Smart.type;
import com.zeroc.Ice.Current;

public class LightI extends SwitchI implements Light {

    protected int brightness;
    protected mode mode;

    public LightI(String name, type type) {
        super(name, type);
        this.brightness = 100;
    }

    @Override
    public String getHelp(Current current) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========Light=========\n");
        sb.append("1. on\n");
        sb.append("2. off\n");
        sb.append("3. change\n");
        sb.append("4. setCondition.<on/off>\n");
        sb.append("5. getCondition\n");
        sb.append("6. setBrightness.<value>\n");
        sb.append("7. setMode.<const/flash/puls>\n");
        sb.append("8. getMode\n");
        sb.append("========================");
        return sb.toString();
    }

    @Override
    public void setBrightness(int brightness, Current current) throws UnreachableArgument {
        if (brightness > 100 || brightness < 0) {
            throw new UnreachableArgument();
        }
        System.out.println(this.name + " set brightness " + brightness);
        this.brightness = brightness;
    }

    @Override
    public int getBrightness(Current current) {
        return this.brightness;
    }

    @Override
    public void setMode(mode mode, Current current) {
        System.out.println(this.name + " set mode " + mode.toString());
        this.mode = mode;
    }

    @Override
    public mode getMode(Current current) {
        return this.mode;
    }
}
