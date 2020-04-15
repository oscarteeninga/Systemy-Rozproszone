package sr.ice.server;

import Smart.BadArgument;
import Smart.LedStripColor;
import Smart.condition;
import Smart.type;
import com.zeroc.Ice.Current;

public class LedStripColorI extends LightColorI implements LedStripColor {

    protected condition[] segments;

    public LedStripColorI(String name, type type, int length) {
        super(name, type);
        this.segments = new condition[length];
        for (int i = 0; i < length; i++) {
            this.segments[i] = condition.ON;
        }
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
        sb.append("11. setSegmentCondition.0/1.0/1.(...).0/1\n");
        sb.append("========================");
        return sb.toString();
    }

    @Override
    public void setSegmentCondition(condition[] conditions, Current current) throws BadArgument {
        if (segments.length != conditions.length) {
            throw new BadArgument();
        }
        System.out.print(this.name + " set conditions: ");
        for (int i = 0; i < conditions.length; i++) {
            System.out.print(conditions[i].toString() + "/");
        }
        System.out.println();
        this.segments = conditions;
    }
}
