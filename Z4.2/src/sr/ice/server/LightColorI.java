package sr.ice.server;

import Smart.LightColor;
import Smart.color;
import com.zeroc.Ice.Current;

public class LightColorI extends LightI implements LightColor {

    protected color color;

    public LightColorI(String name) {
        super(name);
        this.color = Smart.color.WHITE;
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
        sb.append("9. setColor.<color>\n");
        sb.append("10. getColor\n");
        sb.append("========================");
        return sb.toString();
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
