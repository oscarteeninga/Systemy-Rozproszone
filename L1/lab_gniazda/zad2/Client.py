import socket;

serverIP = "127.0.0.1"
serverPort = 9008

print('ZAD2 - PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
snd_msg = "żółta gęś"
print("SEND:", snd_msg)
client.sendto(bytes(snd_msg, 'utf-8'), (serverIP, serverPort))
rcv_msg = client.recvfrom(1024)[0]
print("RECEIVED:", rcv_msg.decode('utf-8'))




