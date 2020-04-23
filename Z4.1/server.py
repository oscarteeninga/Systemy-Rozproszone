from concurrent import futures
import grpc
import weather_pb2 as Weather
import weather_pb2_grpc as Weather_grpc
import time
import datetime
import threading

class Listener(Weather_grpc.WeatherServiceServicer):
    def __init__(self, server):
        self.server = server

    def subscribe(self, request, context):
        addListener(self.server)
        while True:
            yield getForecast(request.city)
            time.sleep(request.frequency)

def addListener(server):
    print("New subscrible!")
    Weather_grpc.add_WeatherServiceServicer_to_server(Listener(server), server)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=1000))
    Weather_grpc.add_WeatherServiceServicer_to_server(Listener(server), server)
    server.add_insecure_port("[::]:9999")
    server.start()
    try:
        while True:
            print("Server on: thread", threading.active_count())
            time.sleep(60)
    except KeyboardInterrupt:
        print("KeyboardInterrupt")
        server.stop(0)

def getForecast(city):
    return Weather.Forecast(
        datetime=str(datetime.datetime.now()).split('.')[0], 
        city=city, 
        weather=[getWeather()]
        )

def getWeather():
    return Weather.Weather(
        temperature=10,
        humindity=45,
        cloudity=Weather.CLOUDY,
        wind=Weather.Wind(
            speed=21,
            direction=Weather.NORTH
        ))

if __name__ == "__main__":
    serve()