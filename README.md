# data-generator

Project for generating dummy data. It uses Akka Streams + Alpakka + Faker + Postgres + Docker

## Data model

The data to be generated corresponds with the following domain model:

``` scala
case class User(id: Option[Long], username: String, password: String, email: String)
```

``` scala
case class Transaction(
    id: Option[Long],
    amount: BigDecimal,
    cardLast4Digits: String,
    dateTime: String,
    installments: Int,
    cardType: String,
    userId: Long,
    status: String)
```
    
## Requirements

* JDK
* SBT
* Docker and Docker-compose

## Docker-compose

A [docker-compose](docker-compose.yml) is provided. It contains the following components:

* Postgres
* Adminer (an adminer UI to see the database content)

## Instructions

1. Run the docker-compose provided:

```
docker-compose up
```

2. Run the app to generate data:

```
sbt run
```

3. Go to [adminer](http://localhost:8083) to check that has been generated (credentials are on [docker-compose.yml](docker-compose.yml))