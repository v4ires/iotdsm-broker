import time
import paho.mqtt.client as paho
import json


def on_message(client, userdata, message):
	time.sleep(1)
	print("received message =",str(message.payload.decode("utf-8")))



def publicarMensagem(broker,topico,mensagem):
	client= paho.edu.usp.icmc.lasdpc.client.CoAPClient("client-001") #create client object client1.on_publish = on_publish #assign function to callback client1.connect(broker,port) #establish connection client1.publish("house/bulb1","on")
	#client.on_message=on_message
	print("Conectado ",broker)
	client.connect(broker)
	print("publicando ")
	a=client.publish(topico,mensagem) 
	print (a)
	client.disconnect() #disconnect


print ("INICIO!!!");
while(1):
	umid, temp = "30", "30";
	if umid is not None and temp is not None:
		tp =  str("Temperatura {} Umidade = {}n").format(temp,umid)
		#js = json.dumps([1,2,3,{'Temperatura C': temp, 'Umidade n': umid}], separators=(',',':'))
		js = json.dumps({'Temperatura (C)':temp,'Umidade (n)':umid})
		print(("Temperatura = {}  Umidade = {}n").format(temp, umid))
		publicarMensagem("localhost","test/topic",js)
		time.sleep(1)
	else:
		print("Falha ao ler sensor")

