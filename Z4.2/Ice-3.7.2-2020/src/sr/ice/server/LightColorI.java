package sr.ice.server;

import Smart.LightColor;
import Smart.color;
import com.zeroc.Ice.Current;

public class LightColorI extends LightI implements LightColor {

    private color color;

    public LightColorI() {
        super();
        this.color = Smart.color.WHITE;
    }

    @Override
    public void setColor(color color, Current current) {
        this.color = color;
    }

    @Override
    public color getColor(Current current) {
        return this.color;
    }
}
