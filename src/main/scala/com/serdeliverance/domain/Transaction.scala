package com.serdeliverance.domain

case class Transaction(
    id: Long,
    amount: BigDecimal,
    cardLast4Digits: String,
    dateTime: String,
    installments: Int,
    cardType: String,
    userId: Long,
    mail: Option[String])
