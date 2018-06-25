#include "DHT.h"
#include <ESP8266WiFi.h>
#include "coap_client.h"


#define DHTPIN 2        //GPIO D4 no NODE MCU
#define DHTTYPE DHT21   // DHT 21 (AM2301) sensor fornecido pelo lab.
#define DEBUG 1 //Variavel para incializar o serial(1 com serial, 0 sem serial)
#define DS 2 //Variavel para o tempo de de delay em segundos

DHT dht(DHTPIN, DHTTYPE); //Funcao que inicializa o sensor

//instance for coapclient
coapClient coap; // Intancia pro coapClient


long tempoAnterior = 0;
long tempoAtual = 0 ;

//WiFi connection info
const char* ssid = "Cluster-Rasp";
const char* password = "senhapadrao";

//Define o IP e porta da conexao
IPAddress ip(192,168,1,102);
int port = 5683;

int i;
int j;
int tam1 = 50;
int tam2 = 25;
int tam3 = 5;
int cont = 1;
  
char temperatura[10];
char humidade[10];
bool state;
int msgid0;

void executa(){
  delay(1000);
  // Leitura do sensor
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  // Indice de calor
  float hic = dht.computeHeatIndex(t, h, false);
  // chega se o valor recebido foi valido
  if (isnan(h) || isnan(t)) {
    if(DEBUG){
          Serial.println("Failed to read from DHT sensor!");
    }
    return;
  }
  /*
  //Exibe na serial os valores da humidade e temperatura
  if(DEBUG){
      Serial.print("Humidity: ");
  Serial.print(h);
  Serial.print(" %\t");
  Serial.print("Temperature: ");
  Serial.print(t);
  Serial.print(" *C ");
  Serial.print("Heat index: ");
  Serial.print(hic);
  Serial.print(" *C \n");
  }*/

  dtostrf(hic, 6, 4, temperatura);
  dtostrf(h, 6, 4, humidade);
  //arguments server ip address,default port,resource name, payload,payloadlength
  tempoAnterior = micros();//Tempo atual em ms
  msgid0 = coap.post(ip,port,"temperatura",temperatura,strlen(temperatura));
  tempoAtual = micros();
  Serial.print(" ");
  Serial.print(tempoAtual - tempoAnterior);
  Serial.println("");
  //Serial.println("CoAP");  
  //int msgid1 = coap.post(ip,port,"humidade",humidade,strlen(humidade));
  state = coap.loop();
}

void callback_response(coapPacket &packet, IPAddress ip, int port) {
    char p[packet.payloadlen + 1];
    memcpy(p, packet.payload, packet.payloadlen);
    p[packet.payloadlen] = NULL;

    //response from coap server
 if(packet.type==3 && packet.code==0){
      Serial.println("ping ok");
    }

    Serial.println(p);
}

void setup() {

  if(DEBUG){
    Serial.begin(9600);
    Serial.println("Inicio Debug:");
    Serial.print("Conectando a rede:");
    Serial.print(ssid);
    Serial.print(" com a senha:");;
    Serial.print(password);
  }
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    yield();
  }
  if(DEBUG){
    Serial.println("");
    Serial.println("WiFi connected");
    Serial.println(WiFi.localIP());
  }

  //coap.response(callback_response);
  coap.start();
  dht.begin();
}

void loop() {
  /*Serial.println("1 Experimento");
  for(i=0;i<tam1;i++){
    for(j=0;j<30;j++){
      Serial.print(cont);
      executa();
      cont++;
    }
  }
  Serial.println("Fim do 1 Experimento");
  cont = 1 ;*/
  Serial.println("2 Experimento");
  for(i=0;i<tam2;i++){
    for(j=0;j<30;j++){
      Serial.print(cont);
      executa();
      cont++;
    }
  }
  Serial.println("Fim do 2 Experimento");
  cont = 1;
  Serial.println("3 Experimento");
  for(i=0;i<tam3;i++){
    for(j=0;j<30;j++){
      Serial.print(cont);
      executa();
      cont++;
    }
  }
  Serial.println("Fim do 3 Experimento");
  while(1){
   Serial.println("Acabou"); 
    delay(10000);
  }

}
