# Clevertec test task

Folder consist of 2 modules: **`console`** and **`RESTful`** applications.

# Console application
## Technology stack:
**`Java only`**

## How to build
1. Build project: gradle clean build
2. To run: java -jar ./build/libs/ClevertecTask-1.0-SNAPSHOT.jar

## Example commands:
**`Console mode`**:

*Command*: 1-1 2-1 3-1 or 1-1 2-1 3-1 card-1234

*Response:*
```
1. Adidas Originals___400 RUB
2. Vodar___100 RUB
3. IPhone 11 Pro Max___10000 RUB
------------------
Total Sum - 7350,000000
```

**`File mode`**:

*Command in file*: 'absolutePath' or 'absolutePath' card-1234

*Response in file*:
```
1. Adidas Originals___400 RUB
2. Vodar___100 RUB
3. IPhone 11 Pro Max___10000 RUB
------------------
Total Sum - 7350,000000
```

# RESTful application



##  Technology stack:
**`Spring Boot (Web, Data JPA, Validation)`**, **`PostgreSQL`**, **`Liquibase`**, **`Docker`**, **`Gradle`**.


## How to build
1. Build project: gradle clean build
2. To run environment: docker compose up

##  Endpoints:

|   HTTP Method   | URL                                                          | Description                       |
|:---------------:|--------------------------------------------------------------|-----------------------------------|
|      `GET`      | localhost/api/v1/products                                    | Get all products                  |
|      `PUT`      | localhost/api/v1/products?itemId=3&itemId=3&salecard-1234    | Get receipt.                      |
|      `GET`      | localhost/api/v1/salecards                                   | Get all salecards                 |


## Get All products

*Request:*

`localhost/api/v1/products?size=3`

*Response:*
```json
{
    "number": 0,
    "size": 3,
    "totalPages": 4,
    "totalElements": 10,
    "first": true,
    "numberOfElements": 3,
    "last": false,
    "content": [
        {
            "id": 1,
            "name": "IPhone 11 Pro",
            "manufacturer": "Apple",
            "expirationDate": "2022-12-21T18:56:02",
            "weight": 500,
            "cost": 8000,
            "count": 8
        },
        {
            "id": 2,
            "name": "IPhone 12 Pro",
            "manufacturer": "Apple",
            "expirationDate": "2022-12-21T18:56:02",
            "weight": 500,
            "cost": 9000,
            "count": 7
        },
        {
            "id": 4,
            "name": "IPhone Xs",
            "manufacturer": "Apple",
            "expirationDate": "2022-12-21T18:56:02",
            "weight": 500,
            "cost": 7000,
            "count": 9
        }
    ]
}
```
>202 Accepted
## Get receipt

*Request:*

`localhost/api/v1/products?itemId=3&itemId=3`

*Response:*
```

1. IPhone 13 Pro___10000 RUB
2. IPhone 13 Pro___10000 RUB
------------------
Total Sum - 20000.000000

```
> 200 OK
## Get all salecards

*Request:*

`localhost/api/v1/salecards?size=2`

*Response:*
```json
{
    "number": 0,
    "size": 2,
    "totalPages": 5,
    "totalElements": 9,
    "first": true,
    "numberOfElements": 2,
    "last": false,
    "content": [
        {
            "id": 9,
            "number": 2004,
            "salePercentage": 10
        },
        {
            "id": 12,
            "number": 2004,
            "salePercentage": 14
        }
    ]
}
```
> 200 OK
