package services.rest;

import services.rest.model.Weather;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

@Path("/weather")
public class WeatherResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getWeather() {
        return WeatherData.getWeather().toHTML();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newWeather(@FormParam("city") String city, @FormParam("begin") String begin, @FormParam("end") String end, @Context HttpServletResponse servletResponse) throws IOException {
        Weather weather = WeatherGetter.getWeather(city, begin, end);
        WeatherData.setWeather(weather);
        servletResponse.sendRedirect("../index.html");
    }
}
