import Adafruit_DHT # Apenas para rodar na rasp
import RPi.GPIO as GPIO #Apenas para rodar na rasp

import getopt
import socket
import sys
import time

from coapthon.client.helperclient import HelperClient
from coapthon.utils import parse_uri

'''
Funcao que publica uma mensagem em um servidor MQTT e tambem gravar em arquivo o tempo de demora de publicar a mensabem
Parametro: broker = Endereco IP
		   local = topico do endereco
		   mensagem = mensagem a ser enviado
'''
def publicarMensagemCOAP(endereco,mensagem,local):
	#start_time = time.time()
	client = HelperClient(server=(endereco, 5683))
	start_time = time.time()
	response = client.post(local,mensagem)
	aux = time.time() - start_time
	aux = round(aux,6)
	#print(cont)
	arquivo.write(str(cont))
	arquivo.write('\t')
	arquivo.write(str(aux*1000000))
	arquivo.write('\n')
	#print response.pretty_print()
	#response = client.post(local,mensagem)
	client.stop()


'''
Funcao que recolhe o dado do sensor e retorna na ordem: umidade e temperatura.
'''
def recolherDadosSensor():
	umid = 50
	temp = 25
	time.sleep(0.5)
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

arquivo = open("coap5.txt","w+")

cont = 1
for k in range(0,30):
	for j in range(0,50):
		umid, temp = recolherDadosSensor()
		publicarMensagemCOAP("192.168.1.100",str(temp),"temperatura")
		cont = cont + 1
cont = 1
for k in range(0,30):
	for j in range(0,25):
		umid, temp = recolherDadosSensor()
		publicarMensagemCOAP("192.168.1.100",str(temp),"temperatura")
		cont = cont + 1

cont = 1
for k in range(0,30):
	for j in range(0,5):
		umid, temp = recolherDadosSensor()
		publicarMensagemCOAP("192.168.1.100",str(temp),"temperatura")
		cont = cont + 1

print ("Terminou")
