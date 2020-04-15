#ifndef SMART_ICE
#define SMART_ICE

module Smart
{
  enum condition { OFF, ON };
  enum unit { CELSIUS, FAHRENHEIT, KELVIN };
  enum mode { CONST, FLASHING, PULSING };
  enum color { WHITE, YELLOW, ORANGE, PINK, RED, GREEN, BLUE, VIOLET, BROWN, GREY };
  enum type { SWITCH, LIGHT, LIGHTCOLOR, STRIP, FRIDGE };
  sequence <condition> seqOfConditions;
  sequence <byte> photo;
  sequence <string> devices;

  exception BadArgument {};
  exception UnreachableArgument {};



  interface Device {
    string getHelp();
    string getName();
    type getType();
  };


  interface DeviceList {
     devices getList();
  };

  interface Switch extends Device {
    void on();
    void off();
    void setCondition(condition op);
    condition change();
    condition getCondition();
  };

  interface Light extends Switch {
    void setBrightness(int brightness) throws UnreachableArgument;
    int getBrightness();
    void setMode(mode mode);
    mode getMode();
  };

  interface LightColor extends Light {
    void setColor(color color);
    color getColor();
  };

  interface LedStripColor extends LightColor {
    void setSegmentCondition(seqOfConditions conditions) throws BadArgument;
  };

  interface Fridge extends Switch {
    void setTemp(float degrees, unit unit) throws UnreachableArgument;
    float getTemp(unit unit);
    void setHumidity(int humidity) throws UnreachableArgument;
    int getHumidity();
    photo getPhoto();
  };

};

#endif