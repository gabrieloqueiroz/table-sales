## API - CATALOG
this application aims to serve as a catalog system, which contains 3 micro services to perform tasks of descriptive and financial records 
> MS-FINANCIAL -- micro service responsible for the financial part of the products

> MS-DETAIL -- micro service responsible for the description part of the products

> MS-CONSUMER -- micro service responsible for the interaction of the two micro services (interface with user)

*DOCUMENTATION IN SWAGGER*
> MS-DETAIL, [https://localhost:8080/swagger-ui.html](https://localhost:8080/swagger-ui.html) \
> MS-FINANCIAL, [https://localhost:8081/swagger-ui.html](https://localhost:8081/swagger-ui.html) \
> MS-CONSUMER, [https://localhost:8082/swagger-ui.html](https://localhost:8082/swagger-ui.html)


## TECHNOLOGIES 

Project created with:
* Java 17
* SpringBoot
* JUnit Jupiter test
* WebTestClient
* Reactive Programming (non-blocking)
* Swagger
* H2 Repository