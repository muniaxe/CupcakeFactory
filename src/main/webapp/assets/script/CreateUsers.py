import time
import threading
import requests

start = time.perf_counter()

threads = []
url = 'http://localhost:8080/OlskerCupcakes/authentication'

def createUser(i):
    myobj = {'action': 'register',
             'email': 'python' + str(i) + '@generated.dk',
             'password': 'asd',
             'password_verify': 'asd'}

    requests.post(url, data=myobj)

for _ in range(200,400):
    t = threading.Thread(target=createUser, args=[_])
    t.start()
    threads.append(t)


for thread in threads:
    thread.join()

finish = time.perf_counter()

print(f'Tid: {round(finish - start, 2)} seconds')