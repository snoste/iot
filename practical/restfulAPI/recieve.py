import requests

#sending dict as payload syntax: {'key1': 'value1', 'key2': ['value2', 'value3']}
#r = requests.get('http://127.0.0.1:8080/api/v1/getrecord/prufa')
#r = requests.get('http://127.0.0.1:8080/api/clients/pws/3/0/15')

#recieving data from the coap server and sending it to the cloud team
r = requests.get('http://127.0.0.1:8081/executeSystem/light_registration')
print(r.status_code)
# if r.status_code == 200:
#     print("Data recieved - Ack recieved!")
#     print("payload: {}".format(r.text))
