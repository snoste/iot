from BaseHTTPServer import BaseHTTPRequestHandler,HTTPServer
from SocketServer import ThreadingMixIn
import threading
import argparse
import re
import cgi
import requests

def fetchLights():
    r = requests.get('http://127.0.0.1:8080/api/clients/pws/3/0/15')
    print(r.status_code)
    if r.status_code == 200:
        print("Data recieved - Ack recieved!")
        print("payload: {}".format(r.text))
        #Then from here send via HTTP post to the cloud team


class LocalData(object):
  records = {}

class HTTPRequestHandler(BaseHTTPRequestHandler):
  def do_POST(self):
    if None != re.search('/setIdentityAndBinding/*', self.path):
      ctype, pdict = cgi.parse_header(self.headers.getheader('content-type'))
      #print(ctype)

      if ctype == 'application/json' or ctype=='application/x-www-form-urlencoded':
        length = int(self.headers.getheader('content-length'))
        data = cgi.parse_qs(self.rfile.read(length), keep_blank_values=1)
        recordID = self.path.split('/')[-1]
        if recordID=='light':
            LocalData.records[recordID] = data
            print "%s was added successfully" % recordID
        elif recordID=='sensor':
            LocalData.records[recordID] = data
            print "%s was added successfully" % recordID
      else:
        data = {}
      self.send_response(200)
      self.end_headers()
    else:
      self.send_response(403)
      self.send_header('Content-Type', 'application/json')
      self.end_headers()
    return

  def do_GET(self):
    if None != re.search('/executeSystem/*', self.path):
      recordID = self.path.split('/')[-1]
      if recordID=='light_registration':
        #Call a special function for light reg
        fetchLights()
        self.send_response(200)
      elif recordID=='sensor_registration':
        #Call a special function for sensor reg
        fetchLights()
        self.send_response(200)
      else:
        self.send_response(400, 'Bad Request: request does not exist')

      #For retrieving local files: self.wfile.write(LocalData.records[recordID])
      self.send_header('Content-Type', 'application/json')
      self.end_headers()
    else:
      self.send_response(403)
      self.send_header('Content-Type', 'application/json')
      self.end_headers()
    return

class ThreadedHTTPServer(ThreadingMixIn, HTTPServer):
  allow_reuse_address = True

  def shutdown(self):
    self.socket.close()
    HTTPServer.shutdown(self)

class SimpleHttpServer():
  def __init__(self, ip, port):
    self.server = ThreadedHTTPServer((ip,port), HTTPRequestHandler)

  def start(self):
    self.server_thread = threading.Thread(target=self.server.serve_forever)
    self.server_thread.daemon = True
    self.server_thread.start()

  def waitForThread(self):
    self.server_thread.join()

  def addRecord(self, recordID, jsonEncodedRecord):
    LocalData.records[recordID] = jsonEncodedRecord

  def stop(self):
    self.server.shutdown()
    self.waitForThread()

if __name__=='__main__':
  parser = argparse.ArgumentParser(description='HTTP Server')
  parser.add_argument('port', type=int, help='Listening port for HTTP Server')
  parser.add_argument('ip', help='HTTP Server IP')
  args = parser.parse_args()

  server = SimpleHttpServer(args.ip, args.port)
  print 'HTTP Server Running...........'
  server.start()
  server.waitForThread()
