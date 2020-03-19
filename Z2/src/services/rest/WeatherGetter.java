package services.rest;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import services.rest.model.Weather;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class WeatherGetter {
    @GET
    @Produces(MediaType.TEXT_XML)
    public static String getWeatherString(URL url) {
        final StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code: " + conn.getResponseCode());
            } else {
                //System.out.println("Request GET send!");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output).append("\n");
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static NodeList getForecast(URL url, String tag) {
        Document XMLWeather = convertStringToXMLDocument(getWeatherString(url));
        XMLWeather.getDocumentElement().normalize();
        return XMLWeather.getElementsByTagName(tag);
    }

    public static Weather getWeather(String city, String begin, String end) {
        URL url = null;
        Date startDate = null, endDate = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&mode=xml&appid=4526d487f12ef78b82b7a7d113faea64");
            if (url == null) {
                return null;
            }
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(begin.substring(0, 10));
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end.substring(0, 10));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------");
        System.out.println("City: " + city);

        float tempSum = 0, maxTemp = 0, minTemp = 400;
        int count = 0;
        for (int i = 0; i < getForecast(url,"time").getLength(); i+=2) {
            Node times = getForecast(url,"time").item(i);
            if (times.getNodeType() == Node.ELEMENT_NODE) {
                Element timeWeather = (Element) times;
                String strDate = timeWeather.getAttribute("from");
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate.substring(0, 10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (date.after(startDate) && date.before(endDate)) {
                    count++;
                    NodeList node = timeWeather.getElementsByTagName("temperature");
                    Element tempInfo = (Element) node.item(0);
                    float temperature = new Float(tempInfo.getAttribute("value"));
                    if (temperature > maxTemp) {
                        maxTemp = temperature;
                    }
                    if (temperature < minTemp) {
                        minTemp = temperature;
                    }
                    System.out.println("Date: " + strDate + "\tTemp: " + (temperature-273));
                    tempSum += (temperature-273);
                }
            }
        }
        maxTemp -= 273;
        minTemp -= 273;
        float averageTemp = tempSum/count;
        System.out.println("AVG: " + averageTemp + "\tMAX: " + maxTemp + "\tMIN: " + minTemp);
        System.out.println("----------------------------");

        return new Weather(city, startDate, endDate, averageTemp, maxTemp, minTemp);
    }

    private static Document convertStringToXMLDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
