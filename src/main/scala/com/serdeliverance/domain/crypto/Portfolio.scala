package com.serdeliverance.domain.crypto

import java.time.LocalDateTime

case class Portfolio(
    id: Option[Long],
    userId: Int,
    cryptocurrencyId: Int,
    amount: BigDecimal,
    transactionDate: LocalDateTime)
