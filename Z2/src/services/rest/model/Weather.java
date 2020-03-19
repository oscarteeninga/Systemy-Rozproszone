package services.rest.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Weather {
    @XmlElement
    String city;
    @XmlElement
    Date begin;
    @XmlElement
    Date end;
    @XmlElement
    float averageTemp;
    @XmlElement
    float maxTemp;
    @XmlElement
    float minTemp;

    public Weather() {

    }

    public Weather(String city, Date begin, Date end, float averageTemp, float maxTemp, float minTemp) {
        this.city = city;
        this.begin = begin;
        this.end = end;
        this.averageTemp = averageTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String toHTML() {
        return "<html>" +
                    "<head>" +
                        "<title>Weather for " + city + "</title>" +
                    "</head>" +
                    "<body>" +
                        "<h1>Forecast for " + city + "</h1>" +
                        "<h3>Begin date: " + begin + "</h3>" +
                        "<h3>End date: " + end + "</h3>" +
                        "<h4>Average temperature: " + averageTemp + "</h4>" +
                        "<h4>Max temperature: " + maxTemp + "</h4>" +
                        "<h4>Min temperature: " + minTemp + "</h3>" +
                "</body>";
    }
}
