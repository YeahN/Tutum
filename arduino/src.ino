//include module
#include <SPI.h>
#include <MFRC522.h>
#include <Servo.h>
#include <LiquidCrystal.h>
#include <Keypad.h>

//RFID_GLOBAL
constexpr uint8_t RST_PIN = 48;
constexpr uint8_t SS_PIN = 53;

MFRC522 rfid(SS_PIN, RST_PIN);
MFRC522::MIFARE_Key key;
 
byte nuidPICC[4];

//DOOR_GLOBAL
Servo servo;
int value= 0, sensor = digitalRead(36), sensorPrev = 1;

//LCD_GLOBAL
LiquidCrystal lcd(35,34,33,32,31,30);
char input[16];

int a = 0, i = 0;
const byte rows = 4;
const byte cols = 3;
byte rowPins[rows] = {22,23,24,25}, colPins[cols] = {26,27,28};

char keys[rows][cols] = {
  {'1', '2', '3'},
  {'4', '5', '6'},
  {'7', '8', '9'},
  {'*', '0', '#'}
};

Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, rows, cols);

//GODJAKOO
bool is_rfid= false, is_door= false, is_keyinput= false;

//RFID_FUNCTION
void printDec(byte *buffer, byte bufferSize) {
  for (byte i = 0; i < bufferSize; i++) {
    Serial.print(buffer[i]< 0x10 ? " 0" : " ");
    Serial.print(buffer[i], DEC);
  }
}

//LCD_FUNCTION
void input_key() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Enter Price");
    char inputkey= keypad.getKey();
  
if(inputkey == '*'){    
  for (i = 0; i < 16; i++) {
    input[i] = keypad.waitForKey();
    if(input[i] == '#') {
      input[i]= 0;
      break;
    }

    lcd.setCursor(i, 1);
    lcd.print(input[i]);
//    Serial.write(input[i]);
  }

   Serial.write(input);
   Serial.println();
}
}

void setup() {
  //DOOR_SEUP
  servo.attach(29);
  //SERIAL_SETUP
  Serial.begin(9600);
  //RFID_SETUP
  SPI.begin();
  rfid.PCD_Init();
  for (byte i = 0; i < 6; i++) {
    key.keyByte[i] = 0xFF;
  }
  //DOOR_SETUP
  pinMode(36,INPUT);
  pinMode(38,OUTPUT);
  //LCD_SETUP
  lcd.begin(16, 2);
}

void rfid_check() {
  if (!rfid.PICC_IsNewCardPresent()) {

    return;
  }
  if (!rfid.PICC_ReadCardSerial()) {

    return;
  }

  MFRC522::PICC_Type piccType = rfid.PICC_GetType(rfid.uid.sak);
  if (piccType != MFRC522::PICC_TYPE_MIFARE_MINI &&
      piccType != MFRC522::PICC_TYPE_MIFARE_1K &&
      piccType != MFRC522::PICC_TYPE_MIFARE_4K)  {
    Serial.println(F("Your tag is not of type MIFARE Classic."));
    return;
  }

  if (rfid.uid.uidByte[0] != nuidPICC[0] ||
      rfid.uid.uidByte[1] != nuidPICC[1] ||
      rfid.uid.uidByte[2] != nuidPICC[2] ||
      rfid.uid.uidByte[3] != nuidPICC[3] )  {
    Serial.println(F("A new card has been detected."));

    for (byte i = 0; i< 4; i++) nuidPICC[i] = rfid.uid.uidByte[i];
    printDec(rfid.uid.uidByte, rfid.uid.size);
    Serial.println();
  } else Serial.println(F("Card read previously."));

  rfid.PICC_HaltA();
  rfid.PCD_StopCrypto1();
}

void door_check() {
   if(Serial.available())   //�ø��� ����Ϳ� �����Ͱ� �ԷµǸ�
 {
  char in_data;           // �Էµ� �����͸� ���� ���� in_data
  in_data = Serial.read(); //�ø������ͷ� �Էµ� ������ in_data�� ����
  if(in_data == '2')      //�Էµ� �����Ͱ� 1�̶��
  {
    value += 90;          //������ 0�� ������Ų��.
  }
  else if(in_data=='3')                   //�׿��� �����Ͱ� �ԷµǸ�
  value = 0;            //������ 0���� �ʱ�ȭ
  servo.write(value); //value���� ������ ȸ��. ex) value�� 90�̶�� 90�� ȸ��
 }

  int sensor = digitalRead(36);
 
if(sensor == HIGH && sensorPrev == LOW){  //���� 1�̰� �����°� 0�϶� (���� ���� ��)
 
  value = 0;
  servo.write(value);
  }
 
  Serial.println(sensor);
 
  if(sensor == HIGH)
  {
  digitalWrite(38,HIGH);
  }
  else{digitalWrite(38,LOW);}
  
  
  delay(100);
  sensorPrev = sensor;
}


void loop() {
  rfid_check();
  door_check();
  input_key();
}
