package com.serdeliverance

import akka.Done
import akka.actor.ActorSystem
import akka.stream.alpakka.slick.javadsl.SlickSession
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.Source
import com.github.javafaker.Faker
import com.serdeliverance.domain.User
import com.serdeliverance.infra.UserTable.userTable
import com.typesafe.config.ConfigFactory
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

object DataGenerator extends App {

  implicit val system = ActorSystem("DataGenerator")

  implicit val session = SlickSession.forConfig("slick-postgres")

  implicit val executionContext: ExecutionContext = system.dispatcher

  val config             = ConfigFactory.load()
  val userRecords        = config.getInt("generator.number-of-users")
  val transactionRecords = config.getInt("generator.number-of-transactions")

  val faker = new Faker()

  private def generateUsers(records: Int): Future[Done] =
    Source(1 to records)
      .map(_ => User(None, faker.name().username(), faker.internet().password(), faker.internet().emailAddress()))
      .runWith(Slick.sink(user => userTable += user))

  // generating data
  val result = for {
    _ <- generateUsers(userRecords)
  } yield ()

  result.onComplete { _ =>
    system.log.info("Stream completed. Shutting down system...")
    system.terminate()
  }
}
