import Adafruit_DHT # Apenas para rodar na rasp
import RPi.GPIO as GPIO #Apenas para rodar na rasp
import time
import paho.mqtt.client as paho


'''
Funcao que publica uma mensagem em um servidor MQTT e tambem gravar em arquivo o tempo de demora de publicar a mensabem
Parametro: broker = Endereco IP
		   topico = topico do endereco
		   mensagem = mensagem a ser enviado
'''
def publicarMensagemMQTT(broker,topico,mensagem):
	client = paho.Client("client-001") #create client object client1.on_publish = on_publish #assign function to callback clie$
	client.connect(broker)
	start_time = time.time()
	a = client.publish(topico,mensagem,qos = 1)
	aux = time.time() - start_time
	client.disconnect() #disconnect
	aux = round(aux,6)
	#print ("["+str(cont)+"]"+str(aux*1000000))
	arquivo.write(str(cont))
	arquivo.write('\t')
	arquivo.write(str(aux*1000000))
	arquivo.write('\n')
	#print("["+str(cont)+"]"+str(time.time() - start_time))

'''
Funcao que recolhe o dado do sensor e retorna na ordem: umidade e temperatura.
'''
def recolherDadosSensor():
	umid = 50
	temp = 25
	time.sleep(1)
	GPIO.setmode(GPIO.BOARD)#apenas para rodar na rasp
	umid, temp = Adafruit_DHT.read_retry(Adafruit_DHT.AM2302,25);	
	if umid is not None and temp is not None:
		umid = round(umid,2)
		temp = round(temp,2)
		return (umid,temp)
	else:
		print("Falha ao ler sensor")
		return None

'''
Realiza os 3 testes do trabalho de TCC
'''
print 'Comecou'

arquivo = open("mqtt.txt","w+")

cont = 1
for k in range(0,30):
	for j in range(0,50):
		umid, temp = recolherDadosSensor()
		publicarMensagemMQTT("192.168.1.100","test/topic",str(temp))
		cont = cont + 1 

cont = 1
for k in range(0,30):
	for j in range(0,25):
		umid, temp = recolherDadosSensor()
		publicarMensagemMQTT("192.168.1.100","test/topic",str(temp))
		cont = cont + 1

cont = 1
for k in range(0,30):
	for j in range(0,5):
		umid, temp = recolherDadosSensor()
		publicarMensagemMQTT("192.168.1.100","test/topic",str(temp))
		cont = cont + 1

