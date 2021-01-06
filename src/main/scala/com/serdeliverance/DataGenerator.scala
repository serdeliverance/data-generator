package com.serdeliverance

import java.time.LocalDateTime

import akka.Done
import akka.actor.ActorSystem
import akka.stream.alpakka.slick.javadsl.SlickSession
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.Source
import com.github.javafaker.Faker
import com.serdeliverance.domain.{Transaction, User}
import com.serdeliverance.infra.TransactionTable.transactionTable
import com.serdeliverance.infra.UserTable.userTable
import com.typesafe.config.ConfigFactory
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

object DataGenerator extends App {

  implicit val system = ActorSystem("DataGenerator")

  implicit val session = SlickSession.forConfig("slick-postgres")

  implicit val executionContext: ExecutionContext = system.dispatcher

  val config             = ConfigFactory.load()
  val userRecords        = config.getInt("generator.number-of-users")
  val transactionRecords = config.getInt("generator.number-of-transactions")

  val userFaker        = new Faker()
  val transactionFaker = new Faker()

  // TODO move this method to a dedicated user data generator object
  private def generateUsers(records: Int): Future[Done] =
    Source(1 to records)
      .map(_ =>
        User(None, userFaker.name().username(), userFaker.internet().password(), userFaker.internet().emailAddress()))
      .runWith(Slick.sink(user => userTable += user))

  private def amount(max: Int) = {
    val min        = 0
    val random     = new Random()
    val nextRandom = min + random.nextInt((max - min) + 1)
    BigDecimal(nextRandom.toString + ".0")
  }

  // TODO refactor to a method with another name to be reused in amount
  def installments(max: Int) = {
    val min    = 0
    val random = new Random()
    min + random.nextInt((max - min) + 1)
  }

  def userId(max: Int) = {
    val min       = 0
    val random    = new Random()
    val generated = min + random.nextInt((max - min) + 1)
    generated.toLong
  }

  def creditCard(): String = {
    val min          = 0
    val random       = new Random()
    val randomNumber = min + random.nextInt((10 - min) + 1)
    if (randomNumber < 5) "VISA" else "MASTERCARD"
  }

  def status(): String = {
    val min          = 0
    val random       = new Random()
    val randomNumber = min + random.nextInt((10 - min) + 1)
    if (randomNumber < 5) "APPROVED" else "REJECTED"
  }

  private def generateTransactions(records: Int): Future[Done] =
    Source(1 to records)
      .map(_ =>
        Transaction(
          id = None,
          amount = amount(5000),
          cardLast4Digits = transactionFaker.finance().creditCard().substring(0, 4),
          dateTime = LocalDateTime.now().toString,
          installments = installments(12),
          cardType = creditCard(),
          userId = userId(200),
          status = status()
      ))
      .runWith(Slick.sink(tx => transactionTable += tx))

  // generating data
  val result = for {
    _ <- generateUsers(userRecords)
    - <- generateTransactions(transactionRecords)
  } yield ()

  result.onComplete { _ =>
    system.log.info("Stream completed. Shutting down system...")
    system.terminate()
  }
}
