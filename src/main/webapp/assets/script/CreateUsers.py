import requests
import time

url = 'http://localhost:8080/OlskerCupcakes/authentication'
start = time.time()
for i in range(200):
    myobj = {'action': 'register',
             'email': 'python' + str(i) + '@generated.dk',
             'password': 'asd',
             'password_verify': 'asd'}

    requests.post(url, data=myobj)
    if (i % 10 == 0) and (i != 0):
        print(str(i) + ' brugere oprettet')
slut = time.time()
print('Oprettelsen af ' + str(i + 1) + ' brugere tog ' + '{:.2f}'.format(slut - start))
