# Spring Boot Microservices

---------------------------------------------------

## Microservices (Accounts, Cards, Loans)

### Common Steps for all

* create 'accounts' / 'cards' / 'loans' apps on start.spring.io
* add 'openAPI' dependency
* add 'lombok' plugin
* add props in application.properties
    . mysql db props
    . server port
    . jpa props
* create 'schema.sql' file for each microservice
* run schema-creating-queries from 'schema.sql'
* create packages:
    . controller, service, service.impl, repository, dto,
    . entity, audit, exception, constants, mapper
* Implement 'BaseEntity', entities classes
* Implement 'dto' packages' classes and 'validations'
* Implement 'mapper' packages' classes ->> you can use MapStruct
* Implement '...Constants' class
* parallel implementation{
    . Implement repos interfaces
    . Implement service & serviceImpl
    . Implemenmt Exceptions classes
}
* Implement '...Controller' class
* Add annoatations on 'BaseEntity' class: @CreatedDate, @LastModifiedDate
	. tells spring Whenever you see these fields inside a table or an entity, 
    . 	please make sure you are updating the date time based upon 
    . 	when the record is being updated or inserted
* Implement 'AuditAwareImpl' class
* add '@EntityListeners(AuditingEntityListener.class)' on 'BaseEntity' class
* add '@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")' on the Main-Class
* Documentation API by OpenAPI, access it ->> http://localhost:8280/swagger-ui/index.html

---------------------------------------------------

### Testing

#### Testing - Accounts

* Testing (/create):
    . Run App and Access "jdbc:h2:mem:testdb" {sa, ''}
    . find 2 tables created {Accounts, Customer}
    . case 1 - create new account:
        ```request
            curl --location 'http://localhost:8280/api/create' \
                --header 'Content-Type: application/json' \
                --data-raw '{
                    "name": "Mahmoud Hegazy",
                    "email": "hegazy@3ddx.com",
                    "mobileNumber": "1234567890"
                }'
        ```
        ```response
            {
                "statusCode": "201",
                "statusMsg": "Account created successfully"
            }
        ```
    . case 2 - create account with exist number
        ``` resposne
            {
                "apiPath": "uri=/api/create",
                "errorCode": "BAD_REQUEST",
                "errorMessage": "Customer already registered with given mobileNumber 1234567890",
                "errorTime": "2025-08-17T14:56:52.2114719"
            }
        ```
* Testing (/fetch?mobileNumber=):	
	. case 1 - get by exist mobile number
		```request
			curl --location --request GET 'http://localhost:8280/api/fetch?mobileNumber=1234567890' \
				--header 'Content-Type: application/json' \
				--data-raw '{
					"name": "Mahmoud Hegazy",
					"email": "hegazy@3ddx.com",
					"mobileNumber": "1234567890"
				}'
		```
		```response
			{
				"name": "Mahmoud Hegazy",
				"email": "hegazy@3ddx.com",
				"mobileNumber": "1234567890",
				"accountsDto": {
					"accountNumber": 1696759774,
					"accountType": "Savings",
					"branchAddress": "123 Main Street, Cairo"
				}
			}
		```
	. case 2 - mobileNumber not found
		```response
			{
				"apiPath": "uri=/api/fetch",
				"errorCode": "NOT_FOUND",
				"errorMessage": "Customer not found with the given input data mobileNumber : '1234567891'",
				"errorTime": "2025-08-17T19:32:31.6486593"
			}
		```
* Testing (/update)
	. case 1 - update account
		```request
			curl --location --request PUT 'http://localhost:8280/api/update' \
				--header 'Content-Type: application/json' \
				--data-raw '{
					"name": "Mahmoud Hegazy",
					"email": "hegazy@3ddx.com",
					"mobileNumber": "1234567890",
					"accountsDto": {
						"accountNumber": 1696759774,
						"accountType": "Savings",
						"branchAddress": "125 Main Street, Cairo"
					}
				}'
		```
		```response
			{
				"statusCode": "200",
				"statusMsg": "Request processed successfully"
			}
		```
	. case 2 - wrong data (ex: accountNumber)
		```response
			{
				"apiPath": "uri=/api/update",
				"errorCode": "NOT_FOUND",
				"errorMessage": "Account not found with the given input data AccountNumber : '1696759775'",
				"errorTime": "2025-08-17T19:38:31.0356387"
			}
		```
* Testing (/delete)
	. case 1 - delete by mobileNumber
		```request
			curl --location --request DELETE 'http://localhost:8280/api/delete?mobileNumber=1234567890'
		```
		```response
			{
				"statusCode": "200",
				"statusMsg": "Request processed successfully"
			}
		```
	. case 2 - mobileNumber not found
		```response
			{
				"apiPath": "uri=/api/delete",
				"errorCode": "NOT_FOUND",
				"errorMessage": "Customer not found with the given input data mobileNumber : '1234567899'",
				"errorTime": "2025-08-17T19:43:05.1653492"
			}
		```
		
---------------------------------------------------
