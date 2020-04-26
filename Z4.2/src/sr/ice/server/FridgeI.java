package sr.ice.server;

import Smart.Fridge;
import Smart.UnreachableArgument;
import Smart.type;
import Smart.unit;
import com.zeroc.Ice.Current;

import java.util.Random;

public class FridgeI extends SwitchI implements Fridge {

    private float kelvinDegrees;
    private int humidity;

    public FridgeI(String name, type type) {
        super(name, type);
        this.kelvinDegrees = 280.0f;
        this.humidity = 50;
    }

    @Override
    public String getHelp(Current current) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========Fridge=========\n");
        sb.append("1. on\n");
        sb.append("2. off\n");
        sb.append("3. change\n");
        sb.append("4. setCondition.<on/off>\n");
        sb.append("5. getCondition\n");
        sb.append("6. setTemp.<value>.<unit>\n");
        sb.append("7. getTemp\n");
        sb.append("8. setHumidity.<value>\n");
        sb.append("9. getHumidity\n");
        sb.append("10. getPhoto\n");
        sb.append("========================");
        return sb.toString();
    }

    @Override
    public void setTemp(float degrees, unit unit, Current current) throws UnreachableArgument {
        float newTemp;
        switch (unit) {
            case CELSIUS:
                newTemp = 273.0f + degrees;
            case FAHRENHEIT:
                newTemp = (degrees + 459.0f) * 5/9;
            case KELVIN:
                newTemp = degrees;
            default:
                newTemp = this.kelvinDegrees;
        }
        newTemp = round(newTemp);
        if (newTemp < 273 || newTemp > 285) {
            throw new UnreachableArgument();
        }
        this.kelvinDegrees = newTemp;
    }

    @Override
    public float getTemp(unit unit, Current current) {
        switch (unit) {
            case CELSIUS:
                return round(this.kelvinDegrees-273.0f);
            case FAHRENHEIT:
                return round(this.kelvinDegrees*9/5 - 459);
            default:
                return round(this.kelvinDegrees);
        }
    }

    @Override
    public void setHumidity(int humidity, Current current) throws UnreachableArgument {
        if (humidity < 10 || humidity > 90) {
            throw new UnreachableArgument();
        }
        this.humidity = humidity;
    }

    @Override
    public int getHumidity(Current current) {
        return this.humidity;
    }

    @Override
    public byte[] getPhoto(Current current) {
        return makePhoto();
    }

    private float round(float value) {
        value *= 10;
        Math.round(value);
        return value/10;
    }

    private byte[] makePhoto() {
        byte []photo = new byte[1024];
        Random camera = new Random();
        camera.nextBytes(photo);
        return photo;
    }
}
