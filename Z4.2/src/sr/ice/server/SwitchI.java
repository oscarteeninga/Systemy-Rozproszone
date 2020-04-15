package sr.ice.server;

import Smart.Switch;
import Smart.condition;
import com.zeroc.Ice.Current;

public class SwitchI extends DeviceI implements Switch {

    protected condition condition;

    public SwitchI(String name) {
        super(name);
        this.condition = condition.OFF;
    }

    @Override
    public String getHelp(Current current) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========Switch=========\n");
        sb.append("1. on\n");
        sb.append("2. off\n");
        sb.append("3. change\n");
        sb.append("4. setCondition.on/off\n");
        sb.append("5. getCondition\n");
        sb.append("========================");
        return sb.toString();
    }

    @Override
    public void on(Current current) {
        System.out.println("ON");
        this.condition = condition.ON;
    }

    @Override
    public void off(Current current) {
        System.out.println("OFF");
        this.condition = condition.OFF;
    }

    @Override
    public void setCondition(condition condition, Current current) {
        this.condition = condition;
    }

    @Override
    public condition change(Current current) {
        if (this.condition.equals(condition.OFF)) {
            on(current);
        } else {
            off(current);
        }
        return this.condition;
    }

    @Override
    public condition getCondition(Current current) {
        return this.condition;
    }
}
