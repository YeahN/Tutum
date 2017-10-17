//ARDUINO_CODE
#include <Servo.h>
#include <LiquidCrystal.h>
#include <Keypad.h>

//DOOR_GLOBAL
Servo servo;
int value= 0, sensor = digitalRead(36), sensorPrev = 1;

//LCD_KEYPAD_GLOBAL
LiquidCrystal lcd(35,34,33,32,31,30);
char input[16];
int i = 0;
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

//LCD_FUNCTION
void input_key(){
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Enter Price");
  char inputkey = keypad.getKey();

  if(inputkey == '*'){
	Serial.write(inputkey);
	Serial.println();
	for (i = 0; i < 16; i++){
		input[i] = keypad.waitForKey();
		Serial.write(input[i]);
		Serial.println();
		if(input[i] == '#'){
			input[i]= 0;
			break;
		}
		lcd.setCursor(i, 1);
  		lcd.print(input[i]);
	}
  }
}

void setup(){
  //DOOR_SETUP
  servo.attach(29);
  //SERIAL_SETUP
  Serial.begin(9600);
  //DOOR_SETUP
  pinMode(36,INPUT);
  pinMode(38,OUTPUT);
  //LCD_SETUP
  lcd.begin(16, 2);
}

void door_check(){

  if(Serial.available()){
	char in_data;
	in_data = Serial.read();
	if(in_data == '2')
		value += 90;
	else if(in_data == '3')
		value = 0;
	servo.write(value);
  }

  if(sensor == HIGH && sensorPrev == LOW){
	value = 90;
	servo.write(value);
  }

  //Serial.println(sensor);

  if(sensor == HIGH)
	digitalWrite(38,HIGH);
  else
	digitalWrite(38,LOW);

  delay(100);
  sensorPrev = sensor;
}

void loop(){
  door_check();
  input_key();
}
