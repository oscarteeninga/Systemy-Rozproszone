package sr.ice.server;

import Smart.BadArgument;
import Smart.LedStripColor;
import Smart.condition;
import com.zeroc.Ice.Current;

public class LedStripColorI extends LightColorI implements LedStripColor {

    private condition[] segments;

    public LedStripColorI(int length) {
        super();
        this.segments = new condition[length];
        for (int i = 0; i < length; i++) {
            this.segments[i] = condition.ON;
        }
    }

    @Override
    public void setSegmentCondition(Smart.condition[] conditions, Current current) throws BadArgument {
        if (segments.length != conditions.length) {
            throw new BadArgument();
        }
        this.segments = conditions;
    }
}
