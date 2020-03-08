# MoneyTransferApplication

A REST API for money transfers between accounts.

## Built With
* Java
* Dropwizard - Web framework
* Maven - Build tool
### Other libraries used
* Junit4 - testing
* Mockito - mocking in tests
* JoCoCo - code coverage
* Javax validator- request validation

## Running on local

### To start money-transfer application

### Build
To build your application
```console
mvn clean install
```
A fat-jar will be created in location `target/money-transfer-1.0-SNAPSHOT.jar`

### Run
Once you have built the jar using above step, you can start the application by below command:
```console 
java -jar target/money-transfer-1.0-SNAPSHOT.jar server config.yml
```
### Testing and Reports
Run the below command on terminal to test the application and generate a test report and a JaCoCo code coverage report:
```console
mvn clean test
```
Code Coverage reports can be found at `target/site/jacoco/index.html`

## Available Endpoints
This api doesn't have a Swagger/OpenAPI integration yet.
Thus the available endpoints information has been added here.

### Account Endpoints
#### GET localhost:8090/accounts
Returns all the accounts available. 2 accounts have been created already.
```console
curl localhost:8090/accounts
```

#### GET localhost:8090/accounts/{accountUuid}
Returns information of one account if present otherwise throws exception.
```console
curl localhost:8090/accounts/eced4dbb-e8ef-480e-83f9-185b4c0cdfc6
```

#### POST localhost:8090/accounts
Creates an account with given parameters only balance is a mandatory parameter. accountHolder and currency can be null
```console 
curl -H "Content-type: application/json" -X POST -d '{"balance":"100.00", "accountHolder": "testaccountholder", "currency":"EUR"}' localhost:8090/accounts
```

### Money Transfer Endpoints
#### POST localhost:8090/money-transfer
Used to transfer money from sender account to receiver account. Accepts receiver account uuid, sender account uuid and amount to be transferred

```console 
curl -H "Content-type: application/json" -X POST -d '{"receiverAccountUuid":"eced4dbb-e8ef-480e-83f9-185b4c0cdfc6","senderAccountUuid":"b2310083-9e35-4e88-aa3e-ef8ae6a4ad4c","amount":"10.00"}' localhost:8090/money-transfer
```
