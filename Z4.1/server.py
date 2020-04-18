from concurrent import futures
import grpc
import proto.weather_pb2 as Weather
import proto.weather_pb2_grpc as Weather_grpc
import time
import datetime
import threading

class Listener(Weather_grpc.WeatherServiceServicer):

    def subscribe(self, request, context):
        while True:
            yield Weather.Forecast(
                datetime=str(datetime.datetime.now()), 
                city=request.city, 
                weather=[Weather.Weather(
                    temperature=10,
                    humindity=45,
                    cloudity=Weather.SUNNY,
                    wind=Weather.Wind(
                        speed=21,
                        direction=Weather.NORTH
                    )
                )])
            time.sleep(1)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=1))
    Weather_grpc.add_WeatherServiceServicer_to_server(Listener(), server)
    Weather_grpc.add_WeatherServiceServicer_to_server(Listener(), server)
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
    timedata = datetime.datetime.now()
    return Weather.Forecast(datatime=timedata, city=city, weather=getWeather())

def getWeather():
    return Weather.Weather()

if __name__ == "__main__":
    serve()