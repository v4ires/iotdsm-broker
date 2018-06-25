#include "DHT.h"
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#define DHTPIN 2        //GPIO D4 no NODE MCU
#define DHTTYPE DHT21   // DHT 21 (AM2301) sensor fornecido pelo lab.
#define DEBUG 1 //Variavel para incializar o serial(1 com serial, 0 sem serial)
#define DS 1 //Variavel para o tempo de de delay em segundos


DHT dht(DHTPIN, DHTTYPE); //Funcao que inicializa o sensor
char temperatura[10];
char humidade[10];

//WiFi connection info
const char* ssid = "Cluster-Rasp";
const char* password = "senhapadrao";


//Define o IP
const char* mqtt_server = "192.168.1.102";
WiFiClient espClient;
PubSubClient client(espClient);

long miliAtual = 0;
long miliAnterior = 0;
int diferenca = 0;

int i;
int j;
int tam1 = 50;
int tam2 = 25;
int tam3 = 5;
int cont = 1;

void executa(){
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  delay(1000 *DS);
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

  miliAnterior = micros();//Tempo atual em ms
  client.publish("temperatura",temperatura);
  miliAtual = micros();
  Serial.print("\t");
  Serial.print(miliAtual - miliAnterior);
  Serial.println(""); 
}


/*void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

  // Switch on the LED if an 1 was received as first character
  if ((char)payload[0] == '1') {
    digitalWrite(BUILTIN_LED, LOW);   // Turn the LED on (Note that LOW is the voltage level
    // but actually the LED is on; this is because
    // it is acive low on the ESP-01)
  } else {
    digitalWrite(BUILTIN_LED, HIGH);  // Turn the LED off by making the voltage HIGH
  }

}*/

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "ESP8266Client-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      //client.publish("outTopic", "hello world");
      // ... and resubscribe
      //client.subscribe("inTopic");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      // delay(5000);
    }
  }
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

  client.setServer(mqtt_server, 1883);
  //client.setCallback(callback);
  dht.begin();
}

void loop() {
  Serial.println("1 Experimento");
  for(i=0;i<tam1;i++){
    for(j=0;j<30;j++){
      Serial.print(cont);
      executa();
      cont++;
    }
  }
  Serial.println("Fim do 1 Experimento");
  cont = 0;
  Serial.println("2 Experimento");
  for(i=0;i<tam2;i++){
    for(j=0;j<30;j++){
      Serial.print(cont);
      executa();
      cont++;
    }
  }
  Serial.println("Fim do 2 Experimento");
  cont = 0;
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
