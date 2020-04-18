import os
from proto import weather_pb2 as Weather
from proto import weather_pb2_grpc as Weather_grpc
import time
import grpc

def run():
    with grpc.insecure_channel("localhost:9999") as channel:
        stub = Weather_grpc.WeatherServiceStub(channel)
        try:
            response = stub.subscribe(Weather.SubscribeRequest(city="Amsterdam"))
            for i in response:
                print(i)
        except KeyboardInterrupt:
            print("KeyboardInterrupt")
            channel.unsubscribe(close)
            exit()

def close(channel):
    channel.close()

if __name__ == "__main__":
    run()    
