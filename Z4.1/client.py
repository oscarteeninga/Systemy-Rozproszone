import weather_pb2 as Weather
import weather_pb2_grpc as Weather_grpc
import time
import grpc
import threading
from sys import argv

def showForecast(forecast):
    print("=== " + forecast.city + " " + forecast.datetime + " ===")
    for weather in forecast.weather:
        print("Temperature: " + str(weather.temperature))
        print("Cloudity: " + str(weather.cloudity))
        print("Humindity: " + str(weather.humindity))
        print("Wind speed: " + str(weather.wind.speed))
        print("Wind direction: " + str(weather.wind.direction))
    print((len(forecast.city) + len(forecast.datetime)+9)*"=")
    print("")

def grpc_server_on(channel) -> bool:
    try:
        grpc.channel_ready_future(channel).result(timeout=1)
        return True
    except grpc.FutureTimeoutError:
        return False

def run(channel, city, frequency):
    stub = Weather_grpc.WeatherServiceStub(channel)
    while True:
        try:
            response = stub.subscribe(Weather.SubscribeRequest(city=city, frequency=frequency))
            for forecast in response:
                if forecast.city == "Nocity":
                    print(city + " not able :(")
                    return
                showForecast(forecast)
        except KeyboardInterrupt:
            print("KeyboardInterrupt")
            channel.unsubscribe(close)
            exit()
        except Exception:
            print("Server unreachable...")
            time.sleep(1)

def close(channel):
    channel.close()

if __name__ == "__main__":
    channel = grpc.insecure_channel("localhost:9999")
    for arg in argv[1:]:
        city = arg.split(".")[0]
        frequency = int(arg.split(".")[1])
        print("Connecting to city: " + city + " frequency " + str(frequency))
        threading.Thread(target=run, args=(channel, city, frequency, )).start()
        time.sleep(0.1)