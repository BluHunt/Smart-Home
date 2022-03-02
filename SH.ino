#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_HOST "********************"
#define FIREBASE_AUTH "************************************"
#define WIFI_SSID "********"
#define WIFI_PASSWORD "**********"

#define Relay1 5 //D1 LED
#define Relay2 4 //D2 FAN

int rel1,rel2,relauto,sen1,sen2,sen3;

void setup() {
  Serial.begin(115200);
  pinMode(Relay1,OUTPUT);
  pinMode(Relay2,OUTPUT);
  digitalWrite(Relay1,HIGH);
  digitalWrite(Relay2,HIGH);
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status()!=WL_CONNECTED){
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Connected");
  Serial.println("IP Address:");
  Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST,FIREBASE_AUTH);
  delay(100);
}

void firebaseconnect(){
  Serial.println("Trying To Reconnect");
  Firebase.begin(FIREBASE_HOST,FIREBASE_AUTH);
}

void loop() {
  if(Firebase.failed()){
    Serial.println("setting up failed!!!");
    Serial.println(Firebase.error());
  }
  rel1 = Firebase.getString("REL1").toInt();
  rel2 = Firebase.getString("REL2").toInt();
 
  if(rel1 == 0){
    digitalWrite(Relay1,LOW);
    Serial.println("RELAY1 IS ON");
  }else{
    digitalWrite(Relay1,HIGH);
    Serial.println("RELAY1 IS OFF");
  }
  if(rel2 == 0){
    digitalWrite(Relay2,LOW);
    Serial.println("RELAY2 IS ON");
  }else{
     digitalWrite(Relay2,HIGH);
    Serial.println("RELAy2 IS OFF");
  }
}
