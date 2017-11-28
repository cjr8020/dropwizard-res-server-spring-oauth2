# dropwizard-hello-world

sample Dropwizard v.0.9.2 based REST service supporting deployment and infrastructure services testing


## hello-world resource

resource URI:

```
https://intportalweb.standard.com/dw-test-app/hello-world
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
   "username": "soapui-test",
   "resourceRequests": 5
}
```

## hello-world-caps resource

resource URI:

```
https://intportalweb.standard.com/dw-test-app/hello-world-caps
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
   "username": "soapui-test",
   "resourceRequests": 4
}
```

