package com.serdeliverance.infra.generator

import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.SlickSession
import com.github.javafaker.Faker
import com.serdeliverance.infra.generator.entities.TransactionGenerator.generateTransactions
import com.serdeliverance.infra.generator.entities.UserGenerator.generateUsers
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext

object UserTransactionGenerator extends App {

  implicit val system = ActorSystem("UserTransactionGenerator")

  implicit val session = SlickSession.forConfig("slick-ut")

  implicit val executionContext: ExecutionContext = system.dispatcher

  val config             = ConfigFactory.load()
  val userRecords        = config.getInt("ut.generator.number-of-users")
  val transactionRecords = config.getInt("ut.generator.number-of-transactions")

  val userFaker        = new Faker()
  val transactionFaker = new Faker()

  // generating data
  val result = for {
    _ <- generateUsers(userRecords)
    - <- generateTransactions(transactionRecords)
  } yield ()

  result.onComplete { _ =>
    system.log.info("User Transaction data generation stream completed. Shutting down system...")
    system.terminate()
  }
}
