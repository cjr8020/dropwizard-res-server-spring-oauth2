# dropwizard-hello-world

sample Dropwizard v.0.9.2 based REST service 


## hello-world resource

resource URI:

```
https://localhost/hello-world
```

The service will respond with `Hello World` as well as the authenticated userid and the number of times this accessed the resources at this endpoint. 

sample response

```
HTTP/1.1 200 OK
Date: Thu, 15 Jun 2017 22:49:16 GMT
Content-Type: application/json
Vary: Accept-Encoding
Content-Length: 71

{
   "message": "Hello World",
   "username": "blah-one",
   "resourceRequests": 5
}
```

## hello-world-caps resource

resource URI:

```
https://localhost/hello-world-caps
```

The service will respond with `HELLO WORLD` as well as the authenticated userid and the number of times this accessed the resources at this endpoint. 

sample response

```
HTTP/1.1 200 OK
Date: Thu, 15 Jun 2017 22:48:46 GMT
Content-Type: application/json
Vary: Accept-Encoding
Content-Length: 71

{
   "message": "HELLO WORLD",
   "username": "blah-two",
   "resourceRequests": 4
}
```

