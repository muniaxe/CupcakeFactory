import requests
url = 'http://localhost:8080/OlskerCupcakes/authentication'
for i in range(200):

	myobj = {'action': 'register',
			 'email' : 'python' + str(i) + '@generated.dk',
			 'password': 'asd',
			 'password_verify': 'asd'}

	requests.post(url, data = myobj)