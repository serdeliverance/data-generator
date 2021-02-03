package com.serdeliverance.domain.transaction

case class Transaction(
    id: Option[Long],
    amount: BigDecimal,
    cardLast4Digits: String,
    dateTime: String,
    installments: Int,
    cardType: String,
    userId: Long,
    status: String)
