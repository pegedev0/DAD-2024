#include <HTTPClient.h>
#include "ArduinoJson.h"
#include <WiFiUdp.h>
#include <PubSubClient.h>

// Replace 0 by ID of this current device
const int DEVICE_ID = 124;

int test_delay = 1000; // so we don't spam the API
boolean describe_tests = true;

// Replace 0.0.0.0 by your server local IP (ipconfig [windows] or ifconfig [Linux o MacOS] gets IP assigned to your PC)
String serverName = "http://192.168.1.70:8080/";
HTTPClient http;

// Replace WifiName and WifiPassword by your WiFi credentials
#define STASSID "OnePlus7Pro"    //"Your_Wifi_SSID"
#define STAPSK "juan112345" //"Your_Wifi_PASSWORD"

// MQTT configuration
WiFiClient espClient;
PubSubClient client(espClient);

// Server IP, where de MQTT broker is deployed
const char *MQTT_BROKER_ADRESS = "192.168.1.70";
const uint16_t MQTT_PORT = 1883;

// Name for this MQTT client
const char *MQTT_CLIENT_NAME = "ArduinoClient_1";
//ULTRASONIDO


int pinEco=12;
int pinGatillo=13;

//LED
int pinledveerde=26;
int pinledazul=25;
int pinledrojo=27;
//HUMIDITY
int pinHumedad = 35; 
int seco=3500;
int mojado=1072;

long readUltrasonicDistance(int triggerPin, int echoPin)
{
  //Iniciamos el pin del emisor de reuido en salida
  pinMode(triggerPin, OUTPUT);
  //Apagamos el emisor de sonido
  digitalWrite(triggerPin, LOW);
  //Retrasamos la emision de sonido por 2 milesismas de segundo
  delayMicroseconds(2);
  // Comenzamos a emitir sonido
  digitalWrite(triggerPin, HIGH);
  //Retrasamos la emision de sonido por 2 milesismas de segundo
  delayMicroseconds(10);
  //Apagamos el emisor de sonido
  digitalWrite(triggerPin, LOW);
  //Comenzamos a escuchar el sonido
  pinMode(echoPin, INPUT);
  // Calculamos el tiempo que tardo en regresar el sonido
  return pulseIn(echoPin, HIGH);
}


// callback a ejecutar cuando se recibe un mensaje
// en este ejemplo, muestra por serial el mensaje recibido
void OnMqttReceived(char *topic, byte *payload, unsigned int length)
{
  Serial.print("Received on ");
  Serial.print(topic);
  Serial.print(": ");

  String content = "";
  for (size_t i = 0; i < length; i++)
  {
    content.concat((char)payload[i]);
  }
  Serial.print(content);
  Serial.println();

    if (content == "ON LED ROJO") {
    digitalWrite(pinledrojo, HIGH);
    delay(2000);
    digitalWrite(pinledrojo, LOW);
  } else if (content == "ON LED VERDE") {
    digitalWrite(pinledveerde, HIGH);
    delay(2000);
    digitalWrite(pinledveerde, LOW);
  } else if (content == "ON LED AZUl") {
    digitalWrite(pinledazul, HIGH);
    delay(2000);
    digitalWrite(pinledazul, LOW);
  }
}

// inicia la comunicacion MQTT
// inicia establece el servidor y el callback al recibir un mensaje
void InitMqtt()
{
  client.setServer(MQTT_BROKER_ADRESS, MQTT_PORT);
  client.setCallback(OnMqttReceived);
}




// Setup
void setup()
{
  pinMode(pinledveerde, OUTPUT);
  pinMode(pinledrojo, OUTPUT);
  pinMode(pinledazul, OUTPUT);
  Serial.begin(9600);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(STASSID);

  /* Explicitly set the ESP32 to be a WiFi-client, otherwise, it by default,
     would try to act as both a client and an access-point and could cause
     network-issues with your other WiFi-devices on your WiFi-network. */
  WiFi.mode(WIFI_STA);
  WiFi.begin(STASSID, STAPSK);

  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }

  InitMqtt();

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  Serial.println("Setup!");
}

// conecta o reconecta al MQTT
// consigue conectar -> suscribe a topic y publica un mensaje
// no -> espera 5 segundos
void ConnectMqtt()
{
  Serial.print("Starting MQTT connection...");
  if (client.connect(MQTT_CLIENT_NAME))
  {
    client.subscribe("Grupo1");
    //client.publish("Grupo1", "connected");
  }
  else
  {
    Serial.print("Failed MQTT connection, rc=");
    Serial.print(client.state());
    Serial.println(" try again in 5 seconds");

    delay(5000);
  }
}

// gestiona la comunicación MQTT
// comprueba que el cliente está conectado
// no -> intenta reconectar
// si -> llama al MQTT loop
void HandleMqtt()
{
  if (!client.connected())
  {
    ConnectMqtt();
  }
  client.loop();
}

String response;

String serializeSensorValueBody(int idSensor, int idPlaca, double record, long time, int tipoSensor,int idGrupo)
{
  // StaticJsonObject allocates memory on the stack, it can be
  // replaced by DynamicJsonDocument which allocates in the heap.
  //
  DynamicJsonDocument doc(2048);

  // Add values in the document
  //
  doc["IdSensor"] = idSensor;
  doc["IdPlaca"] = idPlaca;
  doc["Record"] = record;
  doc["Time"] = time;
  doc["TipoSensor"] = tipoSensor;
  doc["idGrupo"] = idGrupo;

  // Generate the minified JSON and send it to the Serial port.
  //
  String output;
  serializeJson(doc, output);
  Serial.println(output);

  return output;
}

String serializeActuatorStatusBody(int idActuator, int idPlaca, int state, int intensity, int idGrupo, long time)
{
  DynamicJsonDocument doc(2048);

  doc["LEDid"] = idActuator;
  doc["IdGroup"] = idGrupo;
  doc["LEDstate"] = state;
  doc["LEDintensity"] = intensity;
  doc["idPlaca"] = idPlaca;
  doc["Time"] = time;

  String output;
  serializeJson(doc, output);
  return output;
}


void deserializeActuatorStatusBody(String responseJson)
{
  if (responseJson != "")
  {
    DynamicJsonDocument doc(2048);

    // Deserialize the JSON document
    DeserializationError error = deserializeJson(doc, responseJson);

    // Test if parsing succeeds.
    if (error)
    {
      Serial.print(F("deserializeJson() failed: "));
      Serial.println(error.f_str());
      return;
    }

    // Fetch values.
    int idActuatorState = doc["idActuatorState"];
    float status = doc["status"];
    bool statusBinary = doc["statusBinary"];
    int idActuator = doc["idActuator"];
    long timestamp = doc["timestamp"];

    Serial.println(("Actuator status deserialized: [idActuatorState: " + String(idActuatorState) + ", status: " + String(status) + ", statusBinary: " + String(statusBinary) + ", idActuator" + String(idActuator) + ", timestamp: " + String(timestamp) + "]").c_str());
  }
}



void test_response(int httpResponseCode)
{
  delay(test_delay);
  if (httpResponseCode > 0)
  {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    String payload = http.getString();
    Serial.println(payload);
  }
  else
  {
    Serial.print("Error code: ");
    Serial.println(httpResponseCode);
  }
}

void describe(char *description)
{
  if (describe_tests)
    Serial.println(description);
}

void GET_tests()
{

}

void POST_tests()
{
  String actuator_states_body = serializeActuatorStatusBody(999,999,1,1,1,1L);
  describe("Test POST with actuator state");
  String serverPath = serverName + "api/actuadores";
  http.begin(serverPath.c_str());
  test_response(http.POST(actuator_states_body));

  String sensor_value_body = serializeSensorValueBody(99,999,999.0,9L,1,7);
  describe("Test POST with sensor value");
  serverPath = serverName + "/api/sensores";
  http.begin(serverPath.c_str());
  test_response(http.POST(sensor_value_body));

  // String device_body = serializeDeviceBody(String(DEVICE_ID), ("Name_" + String(DEVICE_ID)).c_str(), ("mqtt_" + String(DEVICE_ID)).c_str(), 12);
  // describe("Test POST with path and body and response");
  // serverPath = serverName + "api/device";
  // http.begin(serverPath.c_str());
  // test_response(http.POST(actuator_states_body));
}

void POST_ULTRASONIDO(double DISTANCIA)
{
  String Sensor_states_body = serializeSensorValueBody(1,1,DISTANCIA,1L,1,1L);
  describe("Post Ultrasonido");
  String serverPath = serverName + "api/sensores";
  http.begin(serverPath.c_str());
  test_response(http.POST(Sensor_states_body));
}

void POSTLED(int state,int intensidad,int ID){
  
  String actuator_states_body = serializeActuatorStatusBody(ID,1,state,intensidad,1,1L);
  describe("Test POST with actuator state");
  String serverPath = serverName + "api/actuadores";
  http.begin(serverPath.c_str());
  test_response(http.POST(actuator_states_body));

}


void POST_HUMIDITY(double HUMEDAD)
{
  String Sensor_states_body = serializeSensorValueBody(2,1,HUMEDAD,1L,0,1L);
  describe("Post Humedad");
  String serverPath = serverName + "api/sensores";
  http.begin(serverPath.c_str());
  test_response(http.POST(Sensor_states_body));
}

// Run the tests!
void loop()
{
  
  HandleMqtt();
  
  int DISTANCIA = 0.01723 * readUltrasonicDistance(pinGatillo, pinEco);
  
  while(DISTANCIA>=20){
    DISTANCIA = 0.01723 * readUltrasonicDistance(pinGatillo, pinEco);
  }
  POST_ULTRASONIDO(DISTANCIA);
  int sensorValue = analogRead(pinHumedad);
  int Humedad= map(sensorValue,mojado,seco,100,0);
  POST_HUMIDITY(Humedad);

  HandleMqtt();

  if(Humedad<=50){ 
    POSTLED(1,1,1);
  }
  else if(Humedad>50 && Humedad<90){
    POSTLED(1,1,2);
  }
  else{

    POSTLED(1,1,3);
  }
  
}
