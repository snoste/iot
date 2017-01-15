import requests
import ast
import time

from sense_hat import SenseHat

def changeMyLightColor(settings):
	print("Now I'm gonna set this as the light settings on this pi")
	print(settings[0],settings[1],settings[2])
	#sense.clear(settings)
	sense.clear(settings[0],settings[1],settings[2])
	#
	#


while(True):
	r = requests.get('http://192.168.1.15:8080/api/clients/NIKOS-LENOVO/10250/0/5')
	print(r.status_code)
	print(r.text)
	mylist = r.text

	print(type(mylist))

	mydict = ast.literal_eval(mylist)
	settings = mydict['content']['value']


	settings = [x for x in map(str.strip, settings.split(',')) if x]
	settings = map(int, settings)
	print(settings)
	changeMyLightColor(settings)

	time.sleep(2)
