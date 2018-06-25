import Adafruit_DHT # Apenas para rodar na rasp
import RPi.GPIO as GPIO #Apenas para rodar na rasp
import time
import requests
import sys

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
Funcao que publica uma mensagem em um servidor HTTP e tambem gravar em arquivo o tempo de demora de publicar a mensabem
Parametro: url = Endereco
		   payload = mensagem a ser enviado
'''

def publicarHTTP(url,payload):
	start_time = time.time()
	r = requests.post(url, data=(payload),timeout=1)
	aux = time.time() - start_time
	aux = round(aux,6)
	#print("["+str(cont)+"]"+str(aux*1000000))
	arquivo.write(str(aux*1000000))
	arquivo.write('\n')


'''
Realiza os 3 testes do trabalho de TCC
'''
print 'Comecou'
cont = 1

arquivo = open("saida.txt","w+")


umid, temp = recolherDadosSensor()
for k in range(0,30):
	for j in range(0,50):
		umid, temp = recolherDadosSensor()
		publicarHTTP("http://192.168.1.100:8081/sensor",str(temp))
		cont = cont + 1
	cont = 1
	for k in range(0,30):
		for j in range(0,25):
			umid, temp = recolherDadosSensor()
			publicarHTTP("http://192.168.1.100:8081/sensor",str(temp))
			cont = cont + 1
	cont = 1
	for k in range(0,30):
		for j in range(0,5):
			umid, temp = recolherDadosSensor()
			publicarHTTP("http://192.168.1.100:8081/sensor",str(temp))
			cont = cont + 1
