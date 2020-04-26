from concurrent import futures
import grpc
import weather_pb2 as Weather
import weather_pb2_grpc as Weather_grpc
import time
import datetime
import threading
import random


class Forecaster():
    def __init__(self, city, weather):
        self.city = city
        self.weather = weather

    def getForecast(self):
        return Weather.Forecast(
            datetime=str(datetime.datetime.now()).split('.')[0], 
            city=self.city, 
            weather=[self.weather]
            )

    def updateWeather(self, weather):
        self.weather = weather


def getWeather():
    return Weather.Weather(
        temperature=int((random.random()-0.3) * 30),
        humindity=int(random.random() * 50) + 20,
        cloudity=Weather.CLOUDY,
        wind=Weather.Wind(
            speed=int(random.random()*30),
            direction=Weather.NORTH
        ))


def genForecasters():
    global cities
    forecasters = {}
    for city in cities:
        forecasters[city] = Forecaster(city, getWeather())
    return forecasters


class Listener(Weather_grpc.WeatherServiceServicer):
    def __init__(self, server, forecasters):
        self.server = server
        self.forecasters = forecasters


    def subscribe(self, request, context):
        if not (request.city in forecasters) or request.city == "Nocity":
            print("No city: " + request.city)
            yield self.forecasters["Nocity"].getForecast()
            return
        
        addListener(self.server, self.forecasters, request.city)
        while True:
            yield self.forecasters[request.city].getForecast()
            time.sleep(request.frequency)


def addListener(server, forecasters, city):
    print("New subscrible on " + city + "!")
    Weather_grpc.add_WeatherServiceServicer_to_server(Listener(server, forecasters), server)


def serve(forecasters):
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=1000))
    Weather_grpc.add_WeatherServiceServicer_to_server(Listener(server, forecasters), server)
    server.add_insecure_port("[::]:9999")
    server.start()
    try:
        while True:
            print("Server on: thread", threading.active_count())
            time.sleep(5)
    except KeyboardInterrupt:
        print("KeyboardInterrupt")
        server.stop(0)


def updateForecasts(forecasters):
    while True:
        for forecaster in forecasters:
            forecasters[forecaster].updateWeather(getWeather())
        time.sleep(5)


cities = ["Amsterdam", "London", "Warsaw", "Nocity"]
forecasters = genForecasters()

if __name__ == "__main__":
    threading.Thread(target=serve, args=(forecasters, )).start()
    threading.Thread(target=updateForecasts, args=(forecasters, )).start()