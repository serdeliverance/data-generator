package com.serdeliverance.infra

import com.serdeliverance.domain.Transaction
import slick.jdbc.PostgresProfile.api._

class TransactionTable(tag: Tag) extends Table[Transaction](tag, "transaction") {

  def id: Rep[Long]                = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def amount: Rep[BigDecimal]      = column[BigDecimal]("amount")
  def cardLast4Digits: Rep[String] = column[String]("card_last_digits")
  def dateTime: Rep[String]        = column[String]("date_time")
  def installments: Rep[Int]       = column[Int]("installments")
  def cardType: Rep[String]        = column[String]("card_type")
  def userId: Rep[Long]            = column[Long]("user_id")
  def email: Rep[Option[String]]   = column[Option[String]]("email")

  def * = (id, amount, cardLast4Digits, dateTime, installments, cardType, userId, email).mapTo[Transaction]
}

object TransactionTable {
  val transactionTable = TableQuery[TransactionTable]
}
