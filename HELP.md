# wm-monitor-system

The system is aimed to process humidity and temperature levels in a ware house premises and alert in case
those levels are over a defined threshold.
The application is monolithic for running convenience sake.
Modules:
* [common](common) - common for the other parts of application (models)
* [alert-service](alert-service) - sensor data processing and alerting if required (just logging)
* [messaging](messaging) - layer where data-collector publishes sensors data and what it pushes to alert-service from.
* [data-collector](data-collector) - UDP server configuration and running;
* [udp-client](udp-client) - not a part of the app, however is attached for a tester convenience.

The application is Spring Boot based. It also requires a maven to be installed on ones system.
After pulling the source code from the repo it is enough with issuing the following command
from the project root folder:
`mvn spring-boot:run`
So, now the app up and running. It listens on ports 3344 and 3355 for sensors data to arrive.
Also, _akka_ basic configuration is implemented as a messaging tool. Since akka free keys are not obtained, it stops to function after some time.

### Why monolithic
It was not defined in the task, so considered as non-essential here, however dividing application into 
services is logical and natural here.   

### Flow
* sensor data read from UDP socket and converted into simple model containing sensor value. Sensor type is presented by the model class type.
* data object sent to akka (messaging module)
* obtained from akka, data sent to monitoring in asynchronous way (alert-service)
* there, it is processed and in case a sensor value is over a configured threshold an alert printed to console.

### Sending packages to UDP server

According to the task two UDP servers opened on 3344 and 3355 ports.
There is a simple UDP client attached in a separate module [udp-client/src](udp-client/src). 
This module is not a part of the application and doesn't get compiled when the app does.
The client has already been compiled and can be run from the client residing dir as follows:
* `> java -cp . UDPClient 3355 "sensor_id=h1;value=50.01"` for humidity;
* `> java -cp . UDPClient 3344 "sensor_id=t1;value=50.01"` for temperature.
In case port and sensor id are misused an exception will be thrown.

### Ways to improve

* division of the app into microservices
* integration tests introducing
* some additional JUnit tests should be added (on akka)
* containerization
