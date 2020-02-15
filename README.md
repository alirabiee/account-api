![Java CI](https://github.com/alirabiee/account-api/workflows/Java%20CI/badge.svg?branch=master)
# Account API
This bundle provides an API to create accounts with a fiat currency and perform real-time transactions between accounts with hard no-overdraft limits.

## Start server
(Java 11 required)
```
./gradlew run
``` 

## Documentation
Run
```
./gradlew build
```
and the Swagger documentation is generated in ```build/classes/java/main/META-INF/swagger/accounts-api-0.1.yml```

## API
### GET /account
```
curl --location --request GET 'http://localhost:8080/account'
```

### GET /account/{id}/balance
```
curl --location --request GET 'http://localhost:8080/account/0605951b-6c00-44c1-afe0-cb19ec2a0fd3/balance'
```

### GET /transfer
```
curl --location --request GET 'http://localhost:8080/transfer'
```

### PUT /account
```
curl --location --request PUT 'http://localhost:8080/account' \
--header 'Content-Type: application/json' \
--header 'x-idempotency-key: 4b201147-8dfa-48ec-954b-341715620e5b' \
--data-raw '{
	"initialBalance": 100,
	"currency": "EUR"
}'
```

### PUT /transfer
```
curl --location --request PUT 'http://localhost:8080/transfer' \
--header 'Content-Type: application/json' \
--header 'x-idempotency-key: 4b201147-8dfa-48ec-954b-341715620e5b' \
--data-raw '{
	"fromAccountId": "e48a4176-8a47-4cb4-a62c-6e5ee600e8b1X",
	"toAccountId": "c0cdc521-6fbd-4d75-b885-e5eaeb2117f3",
	"amount": 7
}'
```
