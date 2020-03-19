import socket;

serverIP = "127.0.0.1"
serverPort = 9009

print('ZAD3 - PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
snd_msg = (2).to_bytes(4, byteorder='little')
print("SEND:", int.from_bytes(snd_msg, byteorder='little'))
client.sendto(snd_msg, (serverIP, serverPort))
rcv_msg = client.recvfrom(1024)[0]
print("RECEIVED:", int.from_bytes(rcv_msg, byteorder='big'))




