package com.serdeliverance.infra.generator.entities

import java.time.LocalDateTime

import akka.Done
import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.{Slick, SlickSession}
import akka.stream.scaladsl.Source
import com.github.javafaker.Faker
import com.serdeliverance.domain.transaction.Transaction
import com.serdeliverance.infra.generator.utils.GeneratorUtils
import com.serdeliverance.infra.tables.TransactionTable.transactionTable
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object TransactionGenerator extends GeneratorUtils {

  private val faker = new Faker()

  def generateTransactions(records: Int)(implicit system: ActorSystem, slickSession: SlickSession): Future[Done] =
    Source(1 to records)
      .map(_ =>
        Transaction(
          id = None,
          amount = amount(5000),
          cardLast4Digits = faker.finance().creditCard().substring(0, 4),
          dateTime = LocalDateTime.now().toString,
          installments = installments(12),
          cardType = creditCard(),
          userId = userId(200),
          status = status()
      ))
      .runWith(Slick.sink(tx => transactionTable += tx))
}
