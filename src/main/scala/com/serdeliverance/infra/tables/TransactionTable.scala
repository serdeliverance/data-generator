package com.serdeliverance.infra.tables

import com.serdeliverance.domain.Transaction
import slick.jdbc.PostgresProfile.api._

class TransactionTable(tag: Tag) extends Table[Transaction](tag, "transaction") {

  def id: Rep[Option[Long]]        = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def amount: Rep[BigDecimal]      = column[BigDecimal]("amount")
  def cardLast4Digits: Rep[String] = column[String]("card_last_digits")
  def dateTime: Rep[String]        = column[String]("date_time")
  def installments: Rep[Int]       = column[Int]("installments")
  def cardType: Rep[String]        = column[String]("card_type")
  def userId: Rep[Long]            = column[Long]("user_id")
  def state: Rep[String]           = column[String]("status")

  def * = (id, amount, cardLast4Digits, dateTime, installments, cardType, userId, state).mapTo[Transaction]
}

object TransactionTable {
  val transactionTable = TableQuery[TransactionTable]
}
