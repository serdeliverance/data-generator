package com.serdeliverance.infra.generator.utils

import scala.util.Random

trait GeneratorUtils {

  def amount(max: Int): BigDecimal = {
    val randomNumber = intGenerator(max)
    BigDecimal(randomNumber.toString + ".0")
  }

  def installments(max: Int): Int = intGenerator(max)

  def userId(max: Int): Long = intGenerator(max).toLong

  def creditCard(): String = conditionalTwoOptionGenerator[String]("VISA", "MASTERCARD")

  def status(): String = conditionalTwoOptionGenerator[String]("APPROVED", "REJECTED")

  private def conditionalTwoOptionGenerator[T](option1: T, option2: T) = {
    val min          = 0
    val random       = new Random()
    val randomNumber = min + random.nextInt((10 - min) + 1)
    if (randomNumber < 5) option1 else option2
  }

  private def intGenerator(maxValue: Int): Int = {
    val min    = 0
    val random = new Random()
    min + random.nextInt((maxValue - min) + 1)
  }
}
