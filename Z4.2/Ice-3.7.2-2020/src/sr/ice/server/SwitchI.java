package sr.ice.server;

import Smart.Switch;
import Smart.condition;
import com.zeroc.Ice.Current;

public class SwitchI implements Switch {

    private condition condition;

    public SwitchI() {
        this.condition = condition.OFF;
    }

    @Override
    public void on(Current current) {
        this.condition = condition.ON;
    }

    @Override
    public void off(Current current) {
        this.condition = condition.OFF;
    }

    @Override
    public void setCondition(condition condition, Current current) {
        this.condition = condition;
    }

    @Override
    public condition change(Current current) {
        if (this.condition.equals(condition.OFF)) {
            this.condition = condition.ON;
        } else {
            this.condition = condition.OFF;
        }
        return this.condition;
    }

    @Override
    public condition getCondition(Current current) {
        return this.condition;
    }
}
