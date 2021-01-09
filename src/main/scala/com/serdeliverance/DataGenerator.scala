package com.serdeliverance

import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.SlickSession
import com.github.javafaker.Faker
import com.serdeliverance.infra.generator.TransactionGenerator.generateTransactions
import com.serdeliverance.infra.generator.UserGenerator.generateUsers
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext

object DataGenerator extends App {

  implicit val system = ActorSystem("DataGenerator")

  implicit val session = SlickSession.forConfig("slick-postgres")

  implicit val executionContext: ExecutionContext = system.dispatcher

  val config             = ConfigFactory.load()
  val userRecords        = config.getInt("generator.number-of-users")
  val transactionRecords = config.getInt("generator.number-of-transactions")

  val userFaker        = new Faker()
  val transactionFaker = new Faker()

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
