# data-generator

Project for generating dummy data. It uses Akka Streams + Alpakka + Faker + Postgres + Docker.

This project contains different generators for different data models:
1. [User Transaction](#User Transaction data model)
2. [Cryptocurrencies](#Cryptocurrencies data model)

## User Transaction data model

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

The data is generated using [Faker](https://github.com/DiUS/java-faker) and stored on `utdb` (see [docker-compose.yml](docker-compose.yml)).

## Cryptocurrencies data model

This data model corresponds to a cryptocurrency portfolio app. It contains the following data:

``` scala
case class User(id: Option[Long], username: String, password: String, email: String) // the same as User Transaction data model
```

``` scala
case class Cryptocurrency(id: Option[Int], name: String, description: String)
```

``` scala
case class Portfolio(
    id: Option[Long],
    userId: Int,
    cryptocurrencyId: Int,
    amount: BigDecimal,
    transactionDate: LocalDateTime)
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