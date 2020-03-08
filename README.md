# MoneyTransfer

How to start the MoneyTransfer application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/money-transfer-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8090`
Commands
---

Account Resource: 

1. Insert : 
curl -H "Content-type: application/json" -X POST -d '{"balance":"10.00", "accountHolder": "test", "currency":"EUR"}' localhost:8090/accounts

2. Get all: 
curl localhost:8090/accounts

3. Get one 
curl localhost:8090/accounts/eced4dbb-e8ef-480e-83f9-185b4c0cdfc6

3. Transfer 
curl -H "Content-type: application/json" -X POST -d '{"receiverAccountUuid":"eced4dbb-e8ef-480e-83f9-185b4c0cdfc6","senderAccountUuid":"b2310083-9e35-4e88-aa3e-ef8ae6a4ad4c","amount":"1.02"}' localhost:8090/money-transfer
