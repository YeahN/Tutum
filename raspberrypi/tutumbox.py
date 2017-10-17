#RASPBERRY_PI_CODE
import RPi.GPIO as GPIO
import MFRC522
import signal
import requests
import time
import serial
import sys
import requests
import json
import re
from requests.exceptions import ConnectionError

#BARCODE
api_key = ""
def barcode_reader():
    hid = {4: 'a', 5: 'b', 6: 'c', 7: 'd', 8: 'e', 9: 'f', 10: 'g', 11: 'h', 12: 'i', 13: 'j', 14: 'k', 15: 'l', 16: 'm',
    17: 'n', 18: 'o', 19: 'p', 20: 'q', 21: 'r', 22: 's', 23: 't', 24: 'u', 25: 'v', 26: 'w', 27: 'x', 28: 'y',
	29: 'z', 30: '1', 31: '2', 32: '3', 33: '4', 34: '5', 35: '6', 36: '7', 37: '8', 38: '9', 39: '0', 44: ' ',
	45: '-', 46: '=', 47: '[', 48: ']', 49: '\\', 51: ';', 52: '\'', 53: '~', 54: ',', 55: '.', 56: '/'}
	hid2 = {4: 'A', 5: 'B', 6: 'C', 7: 'D', 8: 'E', 9: 'F', 10: 'G', 11: 'H', 12: 'I', 13: 'J', 14: 'K', 15: 'L', 16: 'M',
	17: 'N', 18: 'O', 19: 'P', 20: 'Q', 21: 'R', 22: 'S', 23: 'T', 24: 'U', 25: 'V', 26: 'W', 27: 'X', 28: 'Y',
	29: 'Z', 30: '!', 31: '@', 32: '#', 33: '$', 34: '%', 35: '^', 36: '&', 37: '*', 38: '(', 39: ')', 44: ' ',
	45: '_', 46: '+', 47: '{', 48: '}', 49: '|', 51: ':', 52: '"', 53: '~', 54: '<', 55: '>', 56: '?'}
	fp = open('/dev/hidraw0', 'rb')
	ss = ""
	shift = False
	done = False
	while not done:
        buffer = fp.read(8)
        for c in buffer:
            if ord(c) > 0:
                if int(ord(c)) == 40:
                    done = True
                    break;
                if shift:
                    if int(ord(c)) == 2:
                        shift = True
                    else:
                        ss += hid2[int(ord(c))]
                        shift = False
                    else:
                        if int(ord(c)) == 2:
                            shift = True
                        else:
                            ss += hid[int(ord(c))]
    return ss

def UPC_lookup(api_key, invoiceNo):
    url = "http://13.59.135.92/raspberrypi/invoiceno.php"
	headers = { 'cache-control': "no-cache", }
	response = requests.request("GET", url, headers=headers)
	print(invoiceNo)
	params = {'invoiceNo' : invoiceNo}
	r = requests.get(url = url, params = params)
	return r.content

def end_read(signal,frame):
	global continue_reading
	print "Ctrl+C captured, ending read."
	continue_reading = False
	GPIO.cleanup()

#MAIN
if __name__ == '__main__':
    try:
        while True:
            scan = barcode_reader()
            ser = serial.Serial('/dev/ttyACM0', 9600)
            if scan == "J6I9ALUPOQ":
                ser.write('3')
            else:
                scan_result = UPC_lookup(api_key,scan)
            	print(scan_result)

            	if scan_result == "prepaid":
                    ser.write('3')
            	elif scan_result == "deferred":
                	money_string = ""
                	money_entering = False
                	while True:
                    	charge = ser.readline()
                    	charge = charge[:1]
                    	if money_entering and charge == "#":
                            break
                    	if money_entering:
                            money_string = money_string + charge
                    	if charge == "*":
                            money_entering = True
                	print(money_string)
                	signal.signal(signal.SIGINT, end_read)
                	MIFAREReader = MFRC522.MFRC522()
                	url = "http://13.59.135.92/raspberrypi/billing.php"
                	while True:
                    	(status,TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)
                    	if status == MIFAREReader.MI_OK:
                            print "Card detected"
                    	(status,uid) = MIFAREReader.MFRC522_Anticoll()
                    	if status == MIFAREReader.MI_OK:
                        	print "Card read UID: "+str(uid[0])+","+str(uid[1])+","+str(uid[2])+","+str(uid[3])
                        	uid = str(uid[0]) + "." + str(uid[1]) + "." + str(uid[2]) + "." + str(uid[3])
                        	params = {'rfid' : uid, 'amount' : money_string, 'invoiceNo' : scan}
                        	try:
                                r = requests.get(url = url, params = params)
                            	pattern = re.compile(r'\s+')
                            	open = re.sub(pattern, '', r.content)
                            	print(open)
                            	if open == "success":
                                    ser.write('3')
                                	url = "http://13.59.135.92/firebase/push.php"
                                	params = {'invoice': scan}
                                	r = requests.get(url = url, params = params)
                        	except ConnectionError as e:
                                print e
                        	finally:
                                time.sleep(10)
                        	key = [0xFF,0xFF,0xFF,0xFF,0xFF,0xFF]
                        	MIFAREReader.MFRC522_SelectTag(uid)
                        	status = MIFAREReader.MFRC522_Auth(MIFAREReader.PICC_AUTHENT1A, 8, key, uid)
                        	if status == MIFAREReader.MI_OK:
                                MIFAREReader.MFRC522_Read(8)
                                MIFAREReader.MFRC522_StopCrypto1()
                        	else:
                                print "Authentication error"
    except KeyboardInterrupt:
        pass
