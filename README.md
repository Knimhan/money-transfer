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
curl -H "Content-type: application/json" -X POST -d '{"balance":"10.00", "currency":"EUR"}' localhost:8090/accounts

2. Get all: 
curl localhost:8090/accounts

3. Get one 
curl localhost:8090/accounts/65fcd0c6-143f-4823-b940-094b23f301a9

3. Transfer 
curl -H "Content-type: application/json" -X POST -d '{"receiverAccountUuid":"afaa6633-b5c1-4272-9f0f-86f173cd4964","senderAccountUuid":"195df4a7-5f0b-4180-b916-5662ce7a7f11","amount":"10.02"}' localhost:8090/money-transfer
