## Gateway Performances Test between ServiceComb Edge and Netflix Zuul
This project is used for make a performances test between ServiceComb Edge and Netflix Zuul.

Modules:
1. baffle-service: used for accept the request routed by edge or zuul
2. edge-service: gateway micro-service with ServiceComb edge
3. zuul-service: gateway micro-service with Netflix Zuul

### Package
In project root, run:
```bash
mvn package
```

output:
1. baffle-service: `lib` folder and `baffle-service-{version}.jar`
2. edge-service: `lib` folder and `edge-service-{version}.jar`
3. zuul-service: `edge-service-{version}-exec.jar` in `bin` folder

### Delopy
It is better use three machine to run this test :
1. Pressure machine : run jmeter script generate client request
2. Gateway machine：delopy edge-service or zuul-service
3. Baffle machine : delopy baffle-service

Do not forget deploy [ServiceCenter](https://github.com/apache/incubator-servicecomb-service-center), run on Baffle machine as recommend.

### Run
1. Change all configuration of ServiceCenter address :

```yaml
servicecomb:
  service:
    registry:
      address: http://192.168.88.59:30100
```

2. Open `Client.jmx` with [JMeter](http://jmeter.apache.org/) UI, change `Number of Threads`, `Server Name or IP` etc... Save
3. Startup `edge-service` or `zuul-service`
4. Startup `baffle-service`
5. Copy `Client.jmx` to Pressure machine and run:

```bash
jmeter -n -t Client.jmx -l log.jtl
```

6. After test, you can analysis log file to get result:

```bash
jmeter -g log.jtl -o <report folder>
```