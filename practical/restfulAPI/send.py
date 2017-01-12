import requests

def posting():
    #sending dict as payload syntax: {'key1': 'value1', 'key2': ['value2', 'value3']}
    r = requests.post('http://127.0.0.1:8081/setIdentityAndBinding/sensor', data={'key1':'value1'})
    #r = requests.post('http://127.0.0.1:8081/setIdentityAndBinding/sensor', data={'key2':'value2'})
    if r.status_code == 200:
        print("Data sent - Ack recieved!")

def putting():
    r = requests.put('http://127.0.0.1:8081/setIdentityAndBinding/light', data={'key1':'value1'})
    if r.status_code == 200:
        print("Data sent - Ack recieved!")

def coappost():
    #http://localhost:8080/api/clients/pws/6/0/
    r = requests.post('http://127.0.0.1:8080/api/clients/pws/3/0/15', data={"value":"Europe/snorri"})
    print(r.status_code)

def leshanpost():
    #http://localhost:8080/api/clients/pws/6/0/
    r = requests.post('http://127.0.0.1:8080/api/clients/pws/objId/instanceId/resourceId/write/newValue', data={"value":"test"})
    #r = requests.post('http://127.0.0.1:8080/api/clients/pws/objId/instanceId/resourceId/write/hellohi)
    print(r.status_code)

def main():
    #posting()
    #coappost()
    leshanpost()
if __name__ == "__main__":
    main()
