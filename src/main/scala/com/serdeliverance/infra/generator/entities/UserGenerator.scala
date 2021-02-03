package com.serdeliverance.infra.generator.entities

import akka.Done
import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.{Slick, SlickSession}
import akka.stream.scaladsl.Source
import com.github.javafaker.Faker
import com.serdeliverance.domain.user.User
import com.serdeliverance.infra.generator.UserTransactionGenerator.userFaker
import com.serdeliverance.infra.tables.UserTable.userTable
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object UserGenerator {

  private val faker = new Faker()

  def generateUsers(records: Int)(implicit system: ActorSystem, slickSession: SlickSession): Future[Done] =
    Source(1 to records)
      .map(_ => User(None, userFaker.name().username(), faker.internet().password(), faker.internet().emailAddress()))
      .runWith(Slick.sink(user => userTable += user))
}
