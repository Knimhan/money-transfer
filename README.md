# MoneyTransfer

How to start the MoneyTransfer application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/money-transfer-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


Commands
---

Account Resource: 

1. Insert : 
curl -H "Content-type: application/json" -X POST -d '{"balance":"10.02"}' localhost:8090/accounts

2. Get all: 
curl localhost:8090/accounts

3. Transfer 
curl -H "Content-type: application/json" -X POST -d '{"receiverAccountUuid":"259edead-4ba0-4012-a6cc-66e2fb53e7e0","senderAccountUuid":"fe729e28-1857-4896-9b47-a586a64375b3","amount":"1.02"}' localhost:8090/money-transfer
